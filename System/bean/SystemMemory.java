package com.faithworth.utils.System.bean;

/**
 * @program: LogMonster *
 * @description:
 * @title: SystemMemory
 * @author: Harris.hou
 * @date: 2018-12-20 17:57
 * @version: v1.0
 **/
public class SystemMemory {
    //物理总内存
    private long totalPhysicalMemory;
    //已用物理内存
    private long usedPhysicalMemorySize;
    //剩余物理内存
    private long freePhysicalMemory;
    //总交换空间
    private long totalSwapSpaceSize;
    //已用交换空间
    private long usedSwapSpaceSize;
    //剩余交换空间
    private long freeSwapSpaceSize;

    public long getTotalPhysicalMemory() {
        return totalPhysicalMemory;
    }

    public void setTotalPhysicalMemory(long totalPhysicalMemory) {
        this.totalPhysicalMemory = totalPhysicalMemory;
    }

    public long getUsedPhysicalMemorySize() {
        return usedPhysicalMemorySize;
    }

    public void setUsedPhysicalMemorySize(long usedPhysicalMemorySize) {
        this.usedPhysicalMemorySize = usedPhysicalMemorySize;
    }

    public long getFreePhysicalMemory() {
        return freePhysicalMemory;
    }

    public void setFreePhysicalMemory(long freePhysicalMemory) {
        this.freePhysicalMemory = freePhysicalMemory;
    }

    public long getTotalSwapSpaceSize() {
        return totalSwapSpaceSize;
    }

    public void setTotalSwapSpaceSize(long totalSwapSpaceSize) {
        this.totalSwapSpaceSize = totalSwapSpaceSize;
    }

    public long getUsedSwapSpaceSize() {
        return usedSwapSpaceSize;
    }

    public void setUsedSwapSpaceSize(long usedSwapSpaceSize) {
        this.usedSwapSpaceSize = usedSwapSpaceSize;
    }

    public long getFreeSwapSpaceSize() {
        return freeSwapSpaceSize;
    }

    public void setFreeSwapSpaceSize(long freeSwapSpaceSize) {
        this.freeSwapSpaceSize = freeSwapSpaceSize;
    }
}
