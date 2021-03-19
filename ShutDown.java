package com.faithworth.utils;

import com.faithworth.log.TransferConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @program: LogMaster *
 * @description: 容器关闭时销毁资源
 * @title: ShutDown
 * @author: Harris.hou
 * @date: 2018-10-25 14:16
 * @version: v1.0
 **/
public class ShutDown implements ServletContextListener {

    private Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (logger.isInfoEnabled()) {
                    logger.info("Run shutdown hook now.");
                }
                TransferConfig.destroyAll();//停止所有数据传输动作及进程,循环判断是否完成（最长处理100记录约1分钟）
                try {
                    Thread.sleep(30 * 1000); //数据处理约1分钟
                } catch (InterruptedException e) {
                }
            }
        }, "LogMasterShutdownHook"));

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            defaultScheduler.shutdown(true);
            //TransferConfig.destroyAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
