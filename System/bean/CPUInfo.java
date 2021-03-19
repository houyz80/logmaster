package com.faithworth.utils.System.bean;

/**
 * @program: LogMonster *
 * @description:
 * @title: CPUInfo
 * @author: Harris.hou
 * @date: 2019-01-08 00:03
 * @version: v1.0
 **/
public class CPUInfo {
    //架构
    private String arch;
    private double usage = 0;
    //系统CPU内核数
    private int availableProcessors;

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }
}
