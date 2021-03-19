package com.faithworth.utils.System.bean;

import java.util.Date;
import java.util.List;

/**
 * @program: LogMonster *
 * @description:
 * @title: SystemInfo
 * @author: Harris.hou
 * @date: 2018-12-20 17:25
 * @version: v1.0
 **/
public class SystemInfo {
    //系统名称
    private String Nme;
    //系统版本
    private String version;

    private CPUInfo cpuInfo;
    //内存
    private SystemMemory systemMemory;
    //硬盘
    private SystemStorage systemStorage;

    private Date ServerTime;

    private String timeZone;
    private long timeZone_offset;


    //网卡



    public String getNme() {
        return Nme;
    }

    public void setNme(String nme) {
        Nme = nme;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public SystemMemory getSystemMemory() {
        return systemMemory;
    }

    public void setSystemMemory(SystemMemory systemMemory) {
        this.systemMemory = systemMemory;
    }

    public SystemStorage getSystemStorage() {
        return systemStorage;
    }

    public void setSystemStorage(SystemStorage systemStorage) {
        this.systemStorage = systemStorage;
    }

    public CPUInfo getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(CPUInfo cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public Date getServerTime() {
        return ServerTime;
    }

    public void setServerTime(Date serverTime) {
        ServerTime = serverTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public long getTimeZone_offset() {
        return timeZone_offset;
    }

    public void setTimeZone_offset(long timeZone_offset) {
        this.timeZone_offset = timeZone_offset;
    }
}
