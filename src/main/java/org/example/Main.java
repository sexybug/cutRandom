package org.example;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        //log.info("Hello,world!");
        //\u000d System.out.println("Hello world!");
    }

    /**
     * 切割单个文件
     *
     * @param inputPath  源文件路径
     * @param outputPath 输出文件路径
     * @param outLength  输出长度
     * @return 0成功，1失败
     * @author hello.bug
     */
    public static int cutOne(String inputPath, String outputPath, int outLength) {
        Path path = Paths.get(inputPath);
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(path);
            if (inChannel.size() < outLength) {
                System.err.println("源文件长度小于输出长度");
                return 1;
            }
            outChannel = FileChannel.open(Paths.get(outputPath), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            inChannel.transferTo(0, outLength, outChannel);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        } finally {
            try {
                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前时间的格式化字符串
     * @author hello.bug
     */
    public static String getZonedDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return dateTimeFormatter.format(ZonedDateTime.now());
    }

    /**
     * 获取文件夹下所有文件名
     *
     * @param dirPath 文件夹路径
     * @return 文件名列表
     * @author hello.bug
     */
    public static List<String> getAllFileName(String dirPath) {
        List<String> fileNameList = new ArrayList<>();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                fileNameList.add(f.getName());
            }
        }
        return fileNameList;
    }

    /**
     * 截取文件夹下所有文件,结果保存在同一文件夹下。
     * 仅当源文件大小大于所需长度时才截取，截取后删除原文件。
     * @param dirPath   文件夹路径,以/结尾
     * @param outLength 截取长度(单位：字节)
     * @return 0成功，1失败
     * @author hello.bug
     */
    public static int cutAll(String dirPath, int outLength) {
        String dateTime = getZonedDateTime();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if(files[i].length()>outLength){
                    if (cutOne(files[i].getAbsolutePath(), dirPath + i + "_" + dateTime, outLength) != 0) {
                        System.err.println("截取文件失败：" + files[i].getAbsolutePath());
                        return 1;
                    }
                    if (!files[i].delete()) {
                        System.err.println("删除原文件失败：" + files[i].getAbsolutePath());
                    }
                }
            }
        }
        return 0;
    }

}