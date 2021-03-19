package com.faithworth.utils.zip;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: LogMaster *
 * @description:
 * @title: GZipUtils
 * @author: Harris.hou
 * @date: 2019-03-20 15:36
 * @version: v1.0
 **/
public class GZipUtils {
    /**
     * tar后缀
     */
    private static final String TAR_SUFFIX = ".tar";
    /**
     * gz后缀
     */
    private static final String GZ_SUFFIX = ".gz";
    /**
     * 区分目录
     */
    private static final String PATH = "/";
    /**
     * 字节数组
     */
    private static final int BYTE_SIZE = 1024;


    /**
     * 归档
     *
     * @param srcFile 源文件
     * @return java.lang.String
     * @author
     * @date 2018/1/22 15:38
     * @since 1.0.0
     */
    public static String archive(String srcFile) throws IOException {
        File file = new File(srcFile);

        TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(file
                .getAbsolutePath() + TAR_SUFFIX));
        String basePath = file.getName();
        if (file.isDirectory()) {
            archiveDir(file, tos, basePath);
        } else {
            archiveHandle(tos, file, basePath);
        }
        tos.close();
        return file.getAbsolutePath() + TAR_SUFFIX;
    }

    /**
     * 目录归档
     *
     * @param dir      目录
     * @param taos     流
     * @param basePath 归档包内相对路径
     * @return void
     * @author
     * @date 2018/1/22 15:40
     * @since 1.0.0
     */
    private static void archiveDir(File dir, TarArchiveOutputStream taos, String basePath) throws
            IOException {
        File[] files = dir.listFiles();
        for (File fi : files) {
            if (fi.isDirectory()) {
                archiveDir(fi, taos, basePath + File.separator + fi.getName());
            } else {
                archiveHandle(taos, fi, basePath);
            }
        }
    }

    /**
     * 具体归档处理（文件）
     *
     * @param taos     输出流
     * @param file     文件
     * @param basePath 文件路径
     * @return void
     * @author
     * @date 2018/1/22 20:16
     * @since 1.0.0
     */
    private static void archiveHandle(TarArchiveOutputStream taos, File file, String basePath)
            throws IOException {
        TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + file.getName());
        tEntry.setSize(file.length());

        taos.putArchiveEntry(tEntry);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[BYTE_SIZE];
            int read = -1;
            while ((read = bis.read(buffer)) != -1) {
                taos.write(buffer, 0, read);
            }
        }
        //这里必须写，否则会失败
        taos.closeArchiveEntry();
    }


    /**
     * 把tar包压缩成gz
     *
     * @param path tar文件路径
     * @return java.lang.String
     * @author
     * @date 2018/1/22 20:17
     * @since 1.0.0
     */
    public static String compressArchive(String path) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path))) {
            try (GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(new
                    BufferedOutputStream(new FileOutputStream(path + GZ_SUFFIX)))) {
                byte[] buffer = new byte[BYTE_SIZE];
                int read = -1;
                while ((read = bis.read(buffer)) != -1) {
                    gcos.write(buffer, 0, read);
                }
            }
        }
        return path + GZ_SUFFIX;
    }


    /**
     * 解压gz
     *
     * @return void
     * @author
     * @date 2018/1/22 20:18
     * @since 1.0.0
     */
    public static void unCompressArchiveGz(String archive) throws IOException {
        File file = new File(archive);
        String finalName = null;

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            finalName = file.getParent() + File.separator + fileName;

            try (BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(finalName))) {
                GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
                byte[] buffer = new byte[BYTE_SIZE];
                int read = -1;
                while ((read = gcis.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
            }
        }
        unCompressTar(finalName);
    }

    /**
     * 解压gz
     *
     * @return void
     * @author
     * @date 2018/1/22 20:18
     * @since 1.0.0
     */
    public static List<String> unCompressArchiveGz2(String archive, String path) throws IOException {
        //获取解压后的文件名
        File file = new File(archive);
        String finalName = GzipUtils.getUncompressedFilename(archive);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            finalName = path + File.separator + fileName;
            fileName = new File(finalName).getCanonicalPath();
            try (BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(fileName))) {
                GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
                byte[] buffer = new byte[BYTE_SIZE];
                int read = -1;
                while ((read = gcis.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
            }
        }
        return unCompressTar2(finalName);
    }

    private static List<String> unCompressTar2(String finalName) throws IOException {
        //获取解压后的文件名
        List<String> _file = new ArrayList<>();
        File file = new File(finalName);
        String parentPath = file.getParent();
        try (TarArchiveInputStream tais =
                     new TarArchiveInputStream(new FileInputStream(file))) {
            TarArchiveEntry tarArchiveEntry = null;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                String name = tarArchiveEntry.getName();
                File tarFile = new File(parentPath, name);
                _file.add(tarFile.getAbsolutePath());
                if (!tarFile.getParentFile().exists()) {
                    tarFile.getParentFile().mkdirs();
                }
                try (BufferedOutputStream bos =
                             new BufferedOutputStream(new FileOutputStream(tarFile))) {
                    int read = -1;
                    byte[] buffer = new byte[BYTE_SIZE];
                    while ((read = tais.read(buffer)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                }
            }
        }
        //删除tar文件
        file.delete();
        return _file;
    }

    /**
     * 解压tar
     *
     * @param finalName 文件路径
     * @return void
     * @author
     * @date 2018/1/22 20:18
     * @since 1.0.0
     */
    private static void unCompressTar(String finalName) throws IOException {

        File file = new File(finalName);
        String parentPath = file.getParent();
        try (TarArchiveInputStream tais =
                     new TarArchiveInputStream(new FileInputStream(file))) {
            TarArchiveEntry tarArchiveEntry = null;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                String name = tarArchiveEntry.getName();
                File tarFile = new File(parentPath, name);
                if (!tarFile.getParentFile().exists()) {
                    tarFile.getParentFile().mkdirs();
                }
                try (BufferedOutputStream bos =
                             new BufferedOutputStream(new FileOutputStream(tarFile))) {
                    int read = -1;
                    byte[] buffer = new byte[BYTE_SIZE];
                    while ((read = tais.read(buffer)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                }
            }
        }
        //删除tar文件
        file.delete();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(GZipUtils.unCompressArchiveGz2("D:\\data\\qrcom_test\\2019-03-25\\test2.tar.gz", "D:\\data\\logmaster\\"));
    }

}
