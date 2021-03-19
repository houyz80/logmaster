package com.faithworth.utils.System;

import com.faithworth.utils.System.bean.*;
import org.hyperic.sigar.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @program: LogMonster *
 * @description:
 * @title: OperatingSystem
 * @author: Harris.hou
 * @date: 2018-12-20 17:26
 * @version: v1.0
 **/
public class OperatingSystem {

    private SystemInfo systemInfo;
    private Sigar sigar = null;

    public OperatingSystem() {
        systemInfo = new SystemInfo();
    }

    public SystemInfo getSystemInfo() {
        systemInfo.setNme(System.getProperty("os.name"));
        systemInfo.setVersion(System.getProperty("os.version"));
        systemInfo.setTimeZone(TimeZone.getDefault().getID());
        systemInfo.setTimeZone_offset(TimeZone.getDefault().getRawOffset());
        systemInfo.setServerTime(new Date());
        sigar = new Sigar();
        getSystemMemory();
        getSystemStrorage();
        getCpuInfo();
        return systemInfo;
    }

    public CPUInfo getCpuInfo() {
        CPUInfo info = new CPUInfo();
        info.setArch(System.getProperty("os.arch"));
        info.setAvailableProcessors(Runtime.getRuntime().availableProcessors());
        double combined = 0;
        try {
            CpuPerc cpuList[] = null;
            cpuList = sigar.getCpuPercList();
            if (cpuList != null) {
//                for (int i = 0; i < cpuList.length; i++) {
//                    System.out.println("cpu " + i + " 使用率:" + cpuList[i].getCombined());
//                }
                for (CpuPerc cpuPerc : cpuList) {
                    combined += cpuPerc.getCombined();
                }
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        info.setUsage(combined);
        systemInfo.setCpuInfo(info);
        return info;
    }

    /**
     * @Description: 获取物理内存
     * @Author: Harris.hou
     * @Date: 2018/12/20 18:24
     * @Param: @param
     * @return: com.faithworth.utils.OperatingSystem.bean.SystemMemory
     **/
    public SystemMemory getSystemMemory() {
        SystemMemory systemMemory = new SystemMemory();
        try {
            Mem mem = sigar.getMem();
            long totalPhysicalMemory = mem.getTotal();
            long freePhysicalMemory = mem.getFree();
            long usedPhysicalMemorySize = totalPhysicalMemory - freePhysicalMemory;
            Swap swap = sigar.getSwap();
            long totalSwapSpaceSize = swap.getTotal();
            long freeSwapSpaceSize = swap.getFree();
            long usedSwapSpaceSize = totalSwapSpaceSize - freeSwapSpaceSize;

            systemMemory.setTotalPhysicalMemory(totalPhysicalMemory);
            systemMemory.setFreePhysicalMemory(freePhysicalMemory);
            systemMemory.setUsedPhysicalMemorySize(usedPhysicalMemorySize);

            systemMemory.setTotalSwapSpaceSize(totalSwapSpaceSize);
            systemMemory.setFreeSwapSpaceSize(freeSwapSpaceSize);
            systemMemory.setUsedSwapSpaceSize(usedSwapSpaceSize);
        } catch (SigarException e) {
            e.printStackTrace();
        }
        systemInfo.setSystemMemory(systemMemory);
        return systemMemory;
    }

    /**
     * @Description: 获取硬盘空间
     * @Author: Harris.hou
     * @Date: 2019/1/7 23:58
     * @Param: @param
     * @return: com.faithworth.utils.System.bean.SystemStorage
     **/
    public SystemStorage getSystemStrorage() {
        List<SystemStoragePartition> systemStoragePartitions = new ArrayList<>();
        long totalHd = 0L;
        try {
            FileSystem fslist[] = sigar.getFileSystemList();
            SystemStoragePartition systemStoragePartition = null;
            for (FileSystem fileSystem : fslist) {
                systemStoragePartition = new SystemStoragePartition();
                systemStoragePartition.setName(fileSystem.getDevName());
                FileSystemUsage usage = null;
                switch (fileSystem.getType()) {
                    case 0: // TYPE_UNKNOWN ：未知
                        break;
                    case 1: // TYPE_NONE
                        break;
                    case 2://TYPE_LOCAL_DISK 本地磁盘
//                        usage = sigar.getFileSystemUsage(fileSystem.getDirName());
//                        systemStoragePartition.setTotalSpaceSize(usage.getTotal());
//                        systemStoragePartition.setUsedSpaceSize(usage.getUsed());
//                        systemStoragePartition.setFreeSpaceSize(usage.getFree());
//                        totalHd += usage.getTotal();
//                        systemStoragePartitions.add(systemStoragePartition);
                    case 3:// TYPE_NETWORK ：网络(remote)
                        usage = sigar.getFileSystemUsage(fileSystem.getDirName());
                        systemStoragePartition.setTotalSpaceSize(usage.getTotal());
                        systemStoragePartition.setUsedSpaceSize(usage.getUsed());
                        systemStoragePartition.setFreeSpaceSize(usage.getFree());
                        totalHd += usage.getTotal();
                        systemStoragePartitions.add(systemStoragePartition);
                        break;
                    case 4:// TYPE_RAM_DISK ：闪存
                        usage = sigar.getFileSystemUsage(fileSystem.getDirName());
                        systemStoragePartition.setTotalSpaceSize(usage.getTotal());
                        systemStoragePartition.setUsedSpaceSize(usage.getUsed());
                        systemStoragePartition.setFreeSpaceSize(usage.getFree());
                        totalHd += usage.getTotal();
                        systemStoragePartitions.add(systemStoragePartition);
                        break;
                    case 5:// TYPE_CDROM ：光驱
                        break;
                    case 6:// TYPE_SWAP ：页面交换
                        usage = sigar.getFileSystemUsage(fileSystem.getDirName());
                        systemStoragePartition.setTotalSpaceSize(usage.getTotal());
                        systemStoragePartition.setUsedSpaceSize(usage.getUsed());
                        systemStoragePartition.setFreeSpaceSize(usage.getFree());
                        totalHd += usage.getTotal();
                        systemStoragePartitions.add(systemStoragePartition);
                        break;
                }

            }
        } catch (SigarException e) {
            e.printStackTrace();
        }


        SystemStorage systemStorage = new SystemStorage(systemStoragePartitions);
        systemStorage.setTotalSpace(totalHd);
        systemInfo.setSystemStorage(systemStorage);
        return systemStorage;
    }


    public List<SystemEthernet> getSystemEthernet() {
        List<SystemEthernet> list = new ArrayList<>();
        try {
            if (sigar == null) sigar = new Sigar();
            String[] netInterfaceList = sigar.getNetInterfaceList();
            SystemEthernet systemEthernet = null;
            NetInterfaceConfig ifconfig = null;
            for (String name : netInterfaceList) {
                systemEthernet = new SystemEthernet();
                ifconfig = sigar.getNetInterfaceConfig(name);
                //System.out.println(ifconfig.getName()+" "+ifconfig.getType()+" "+ifconfig.getAddress()+" "+ifconfig.getDescription());
                if (NetFlags.LOOPBACK_ADDRESS.equals(ifconfig.getAddress()) ||NetFlags.ANY_ADDR.equals(ifconfig.getAddress()) || (ifconfig.getFlags() & NetFlags.IFF_LOOPBACK) != 0 || NetFlags.NULL_HWADDR.equals(ifconfig.getHwaddr())) {
                    continue;
                }

                systemEthernet.setName(ifconfig.getName());
                systemEthernet.setAddress(ifconfig.getAddress());
                systemEthernet.setNetMask(ifconfig.getNetmask());
                systemEthernet.setDesc(ifconfig.getDescription());
                list.add(systemEthernet);
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return list;
    }

}
