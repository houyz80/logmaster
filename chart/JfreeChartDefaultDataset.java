package com.faithworth.utils.chart;

/**
 * @program: LogMaster *
 * @description:
 * @title: JfreeChartDefaultDataset
 * @author: Harris.hou
 * @date: 2018-11-25 21:33
 * @version: v1.0
 **/
public class JfreeChartDefaultDataset {

    private double value;
    private String rowKey;
    private String columnKey;

    public JfreeChartDefaultDataset() {
    }

    public JfreeChartDefaultDataset(double value, String rowKey, String columnKey) {
        this.value = value;
        this.rowKey = rowKey;
        this.columnKey = columnKey;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }
}
