package com.faithworth.utils.System.bean;

import java.util.List;

/**
 * @program: LogMonster *
 * @description:
 * @title: SystemStorage
 * @author: Harris.hou
 * @date: 2018-12-20 17:59
 * @version: v1.0
 **/
public class SystemStorage {
    private long totalSpace;
    private List<SystemStoragePartition> systemStoragePartitions;

    public SystemStorage(List<SystemStoragePartition> systemStoragePartitions) {
        this.systemStoragePartitions = systemStoragePartitions;
    }

    public List<SystemStoragePartition> getSystemStoragePartitions() {
        return systemStoragePartitions;
    }

    public void setSystemStoragePartitions(List<SystemStoragePartition> systemStoragePartitions) {
        this.systemStoragePartitions = systemStoragePartitions;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(long totalSpace) {
        this.totalSpace = totalSpace;
    }
}
