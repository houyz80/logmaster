package com.faithworth.utils.zip;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: LogMaster *
 * @description:
 * @title: ZipUtils
 * @author: Harris.hou
 * @date: 2019-03-20 14:55
 * @version: v1.0
 **/
public class ZipUtils {

    private static final int BUFFER_SIZE = 1024;
    /**
     * 区分目录
     */
    private static final String PATH = "/";

    /**
     * zip压缩文件
     */
    public static void zip(String dir, String zipPath) {
        List<String> paths = getFiles(dir);
        compressFilesZip(paths.toArray(new String[paths.size()]), zipPath, dir);
    }

    /**
     * 递归取到当前目录所有文件
     */
    private static List<String> getFiles(String dir) {
        List<String> listFiles = new ArrayList<>();
        File file = new File(dir);
        File[] files = file.listFiles();
        String str = null;
        for (File f : files) {
            str = f.getAbsolutePath();
            listFiles.add(str);
            if (f.isDirectory()) {
                listFiles.addAll(getFiles(str));
            }
        }
        return listFiles;
    }

    /**
     * 文件名处理
     */
    private static String getFilePathName(String dir, String path) {
        String p = path.replace(dir + File.separator, "");
        p = p.replace("\\", "/");
        return p;
    }

    /**
     * 把文件压缩成zip格式
     *
     * @param files       需要压缩的文件
     * @param zipFilePath 压缩后的zip文件路径   ,如"D:/test/aa.zip";
     */
    private static void compressFilesZip(String[] files, String zipFilePath, String dir) {
        if (files == null || files.length <= 0) {
            return;
        }
        File zipFile = new File(zipFilePath);
        try (ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(zipFile)) {
            zaos.setUseZip64(Zip64Mode.AsNeeded);
            //将每个文件用ZipArchiveEntry封装
            //再用ZipArchiveOutputStream写到压缩文件中
            for (String strFile : files) {
                File file = new File(strFile);
                if (file != null) {
                    String name = getFilePathName(dir, strFile);
                    ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, name);
                    zaos.putArchiveEntry(zipArchiveEntry);
                    if (file.isDirectory()) {
                        continue;
                    }
                    try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            //把缓冲区的字节写入到ZipArchiveEntry
                            zaos.write(buffer, 0, len);
                        }
                        zaos.closeArchiveEntry();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            zaos.finish();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 解压 zip 文件
     *
     * @param zipFile zip 压缩文件
     * @param destDir zip 压缩文件解压后保存的目录
     * @return 返回 zip 压缩文件里的文件名的 list
     */
    public static List<String> unZip(File zipFile, String destDir) {
        // 如果 destDir 为 null, 空字符串, 或者全是空格, 则解压到压缩文件所在目录
        if (StringUtils.isBlank(destDir)) {
            destDir = zipFile.getParent();
        }

        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        List<String> fileNames = new ArrayList<>();

        try (ZipArchiveInputStream is = new ZipArchiveInputStream(new BufferedInputStream(
                new FileInputStream(zipFile), BUFFER_SIZE));) {
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());

                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                    try (OutputStream os = new BufferedOutputStream(new FileOutputStream(new File
                            (destDir,
                                    entry.getName())), BUFFER_SIZE);) {
                        IOUtils.copy(is, os);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileNames;
    }



    /**
     * 解压 zip 文件
     *
     * @param zipFilePath zip 压缩文件的路径
     * @param destDir     zip 压缩文件解压后保存的目录
     * @return 返回 zip 压缩文件里的文件名的 list
     */
    public static List<String> unZip(String zipFilePath, String destDir) throws Exception {
        File zipFile = new File(zipFilePath);
        return unZip(zipFile, destDir);
    }
}
