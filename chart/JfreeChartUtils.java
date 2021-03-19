package com.faithworth.utils.chart;

import com.faithworth.utils.chart.jfreechart.ChartUtils;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: LogMaster *
 * @description:
 * @title: JfreeChartUtils
 * @author: Harris.hou
 * @date: 2018-11-25 21:18
 * @version: v1.0
 **/
public class JfreeChartUtils {

    /**
     * @Description: 创建柱状图
     * @Author: Harris.hou
     * @Date: 2018/11/25 21:18
     * @Param: [dataset]
     * @return: void
     **/
    public static String createBar(String title, String xLable, String yLable, List<JfreeChartDefaultDataset> datasetList,  int width, int height) {

        try {
            ChartUtils.setChartTheme();

            DefaultCategoryDataset dataset = generateDefaultCategoryDataset(datasetList);
            //创建柱状图,柱状图分水平显示和垂直显示两种
            JFreeChart chart = ChartFactory.createBarChart(title, xLable, yLable, dataset, PlotOrientation.VERTICAL, true, true, true);


            //得到绘图区
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            ChartUtils.setBarRenderer(plot, true);
            ChartUtils.setAntiAlias(chart);
            ChartUtils.setLegendEmptyBorder(chart);

            //将chart转为base64编码
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(byteArrayOutputStream, chart, width, height);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            return getImageBase64Str(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createBar3D(String title, String xLable, String yLable, List<JfreeChartDefaultDataset> datasetList,  int width, int height) {

        try {
            ChartUtils.setChartTheme();

            DefaultCategoryDataset dataset = generateDefaultCategoryDataset(datasetList);
            //创建柱状图,柱状图分水平显示和垂直显示两种
            JFreeChart chart = ChartFactory.createBarChart3D(title, xLable, yLable, dataset, PlotOrientation.VERTICAL, true, true, true);
            //得到绘图区
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            ChartUtils.setBarRenderer(plot, true);
            ChartUtils.setAntiAlias(chart);
            ChartUtils.setLegendEmptyBorder(chart);

            //图例
            LegendTitle legend = chart.getLegend();
            legend.setBorder(0, 0, 0, 0);
//            legend.setPosition(org.jfree.ui.RectangleEdge.RIGHT);
            legend.setBackgroundPaint(ChartColor.WHITE);
            //将chart转为base64编码
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(byteArrayOutputStream, chart, width, height);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            return getImageBase64Str(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createLine(String title, String xLable, String yLable, List<JfreeChartDefaultDataset> datasetList,  int width, int height) {

        try {
            ChartUtils.setChartTheme();

            DefaultCategoryDataset dataset = generateDefaultCategoryDataset(datasetList);
            JFreeChart chart = ChartFactory.createLineChart(title, xLable, yLable, dataset, PlotOrientation.VERTICAL, true, true, true);


            //得到绘图区
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            ChartUtils.setLineRender(plot, false, true);

            ChartUtils.setAntiAlias(chart);
            ChartUtils.setLegendEmptyBorder(chart);

            //将chart转为base64编码
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(byteArrayOutputStream, chart, width, height);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            return getImageBase64Str(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createPie(String title, Map<String, Long> dataMap,int width, int height) {

        try {
            ChartUtils.setChartTheme();

            PieDataset dataset = generatePieDataset(dataMap);
            //创建柱状图,柱状图分水平显示和垂直显示两种
            JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, true);


            //得到绘图区
            PiePlot plot = (PiePlot) chart.getPlot();
            ChartUtils.setPieRender(plot);
            ChartUtils.setAntiAlias(chart);
            ChartUtils.setLegendEmptyBorder(chart);
//            chart.getLegend().setLegendItemGraphicLocation(RectangleAnchor.TOP_RIGHT);
////            chart.getLegend().setLegendItemGraphicAnchor(RectangleAnchor.RIGHT);


            //将chart转为base64编码
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(byteArrayOutputStream, chart, width, height);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            return getImageBase64Str(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PieDataset generatePieDataset(Map<String, Long> dataMap) {
        PieDataset ds = new DefaultPieDataset();
        Iterator entries = dataMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            ((DefaultPieDataset) ds).setValue(entry.getKey().toString(), (Long) entry.getValue());
        }
        return ds;
    }

    public static DefaultCategoryDataset generateDefaultCategoryDataset(List<JfreeChartDefaultDataset> datasetList) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        for (JfreeChartDefaultDataset jfreeChartDefaultDataset : datasetList) {
            ds.setValue(jfreeChartDefaultDataset.getValue(), jfreeChartDefaultDataset.getRowKey(), jfreeChartDefaultDataset.getColumnKey());
        }
        return ds;
    }

    private static String getImageBase64Str(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
