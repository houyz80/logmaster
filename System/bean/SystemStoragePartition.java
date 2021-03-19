package com.faithworth.utils.System.bean;

/**
 * @program: LogMonster *
 * @description:
 * @title: SystemStorage
 * @author: Harris.hou
 * @date: 2018-12-20 17:59
 * @version: v1.0
 **/
public class SystemStoragePartition {

    private String name;
    //总空间
    private long totalSpaceSize;
    //已用空间
    private long usedSpaceSize;
    //剩余空间
    private long freeSpaceSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalSpaceSize() {
        return totalSpaceSize;
    }

    public void setTotalSpaceSize(long totalSpaceSize) {
        this.totalSpaceSize = totalSpaceSize;
    }

    public long getUsedSpaceSize() {
        return usedSpaceSize;
    }

    public void setUsedSpaceSize(long usedSpaceSize) {
        this.usedSpaceSize = usedSpaceSize;
    }

    public long getFreeSpaceSize() {
        return freeSpaceSize;
    }

    public void setFreeSpaceSize(long freeSpaceSize) {
        this.freeSpaceSize = freeSpaceSize;
    }
}
