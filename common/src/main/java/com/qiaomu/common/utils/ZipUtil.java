package com.qiaomu.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 李品先
 * @description:
 * @Date 2020-04-26 15:21
 */
public class ZipUtil {
    static final Integer BUFFER_SIZE = 4096;
    /**
     * 把文件集合打成zip压缩包
     * @param srcFiles 压缩文件集合
     * @param zipFile  zip文件名
     * @throws RuntimeException 异常
     */
    public static void toZip(List<File> srcFiles, File zipFile) throws RuntimeException {
        //long start = System.currentTimeMillis();

        if(zipFile == null){
            throw new RuntimeException("压缩包文件名为空");
        }
        if(!zipFile.getName().endsWith(".zip")){
            throw new RuntimeException("压缩包文件名异常，zipFile={}");
        }
        ZipOutputStream zos = null;//1
        FileOutputStream out = null;//2
        try {
            out = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);//3
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            zos.finish();
        } catch (Exception e) {
            throw new RuntimeException("zipFile error from ZipUtils", e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
                if (zos != null) {
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("ZipUtil toZip close exception", e);
            }

        }
    }
}
