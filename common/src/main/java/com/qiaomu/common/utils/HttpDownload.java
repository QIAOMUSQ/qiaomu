package com.qiaomu.common.utils;



import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * http 文件下载工具类
 * @author Xiao Yingjun
 */
public class HttpDownload {

    /**
     * 自定义缓存区大小，一次读取磁盘文件到内存，根据实际情况优化调整
     */
    private static final int BUFFER = 1024*8;

    /**
     * RandomAccessFile工作模式,此处设置只读即可
     */
    private static final String MODE = "r";

    /**
     * 文件下载，支持普通下载，断点继续下载，断点区间下载
     * @param request 请求
     * @param response 响应
     * @param sourceFile 文件路径
     * @param fileName 文件名称
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String sourceFile, String fileName){
        response.reset();
        File file = new File(sourceFile);
        long fileLength = file.length();
        try {
            RandomAccessFile raf = new RandomAccessFile(file,MODE);
            ServletOutputStream sos = response.getOutputStream();
            String rangeBytes = null;
            RangeSwitchEnum rangeSwitch = RangeSwitchEnum.ORDINARY;
            long pastLength = 0;
            long toLength;
            long contentLength;
            if (null != request.getHeader("Range")) {
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
                if(rangeBytes.indexOf('-') == rangeBytes.length()-1){
                    rangeSwitch = RangeSwitchEnum.BEGIN;
                    rangeBytes = rangeBytes.substring(0,rangeBytes.indexOf("-")).trim();
                    pastLength = Long.parseLong(rangeBytes);
                    contentLength = fileLength - pastLength;
                }else{
                    rangeSwitch = RangeSwitchEnum.BLOCK;
                    pastLength = Long.parseLong(rangeBytes.substring(0,rangeBytes.indexOf("-")).trim());
                    toLength = Long.parseLong(rangeBytes.substring(rangeBytes.indexOf("-")+1).trim());
                    contentLength = toLength - pastLength + 1;
                }
            }else{
                contentLength = fileLength;
            }
            response = setResponse(request,response,contentLength,fileName);
            switch (rangeSwitch){
                case ORDINARY:{
                    break;
                }
                case BEGIN:{
                    String contentRange = "bytes "+pastLength+"-"+(fileLength-1)+"/" + fileLength;
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                case BLOCK:{
                    String contentRange = "bytes "+rangeBytes +"/" + fileLength;
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                default:{
                    break;
                }
            }
            inputStreamToOutputStream(raf,sos,pastLength,contentLength);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置响应对象信息
     * @param response 响应对象
     * @param contentLength 需要下载的文件长度
     * @param fileName 文件名称
     * @return 响应对象
     * @throws Exception 抛出异常
     */
    private static HttpServletResponse setResponse(HttpServletRequest request, HttpServletResponse response, Long contentLength, String fileName) throws UnsupportedEncodingException {
        /*String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeURIComponent(fileName) +"\";";
        headerValue += " filename*=utf-8''" + encodeURIComponent(fileName);*/
        String userAgent = request.getHeader("User-Agent");
        /*if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {*/
            /*fileName = java.net.URLEncoder.encode(fileName, "UTF-8");*/
        /*} else {
            // 非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }*/

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" +new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(contentLength));
        return response;
    }

    public static String encodeURIComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从输入流中读取内容写入到输出流中
     * @param raf 随机读取流
     * @param sos 输出流
     * @param pastLength 开始读取位置
     * @param contentLength 读取的长度
     */
    private static void inputStreamToOutputStream(RandomAccessFile raf, ServletOutputStream sos, long pastLength, long contentLength){
        try {
            if(pastLength != 0){
                raf.seek(pastLength);
            }
            byte[] buffer = new byte[BUFFER];
            int length;
            int readLength = 0;
            while (readLength <= contentLength - BUFFER) {
                length = raf.read(buffer, 0, BUFFER);
                sos.write(buffer, 0, length);
                readLength += length;
            }
            if(readLength < contentLength){
                length = raf.read(buffer, 0, (int)(contentLength - readLength));
                sos.write(buffer, 0, length);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(sos != null){
                try {
                    sos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(raf != null){
                try {
                    raf.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
