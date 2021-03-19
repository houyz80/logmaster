package com.faithworth.utils.System.bean;

/**
 * @program: LogMaster *
 * @description:
 * @title: SystemEthernet
 * @author: Harris.hou
 * @date: 2019-09-05 16:56
 * @version: v1.0
 **/
public class SystemEthernet {
    private String name;
    private String address;
    private String netMask;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetMask() {
        return netMask;
    }

    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }
}
