package com.qiaomu.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.modules.sys.entity.SysFileEntity;
import com.qiaomu.modules.sys.service.SysFileService;
import jodd.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author 李品先
 * @description:文件、图片操作控制器
 * @Date 2019-01-15 18:58
 */
@Controller
@RequestMapping("welfare/sysFile")
public class SysFileConcroller {

    @Autowired
    private SysFileService sysFileService;

    @Value("${file.save}")
    private String savePath;

    /**
     * 文件上传
     *
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "upload")
    public void upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");//application/html
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

       // FileInfo fileInfo = new FileInfo();
        SysFileEntity fileEntity = new SysFileEntity();
        if (!file.isEmpty()) {
            try {
                String orgName = file.getOriginalFilename();
                Long fileSize = file.getSize();
                String saveName = UUID.randomUUID().toString() + "." + orgName.substring(orgName.lastIndexOf(".") + 1, orgName.length());
                File dir = new File(savePath);
                if (!dir.exists()) dir.mkdirs();
                File newFile = new File(savePath + saveName);
                fileEntity.setDateTime(new Date());
                FileUtil.writeStream(newFile, file.getInputStream());

                fileEntity.setName(saveName);
                fileEntity.setServicePath(savePath + saveName);
                fileEntity.setPath("http://103.26.76.116:9999/admin/outapp/image/" + saveName);
                fileEntity.setOrgName(orgName);
                fileEntity.setFileSize(fileSize);
                sysFileService.insertInfo(fileEntity);
                System.out.println("file 2= " + JSON.toJSON(fileEntity).toString());
                String result = JSON.toJSONString(fileEntity);
                out.println(result);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                String result = JSON.toJSON(fileEntity).toString();
                out.print(result);
                out.close();
            }
        }

        return;
    }

    /**
     * 展示图片
     *
     * @param request
     * @param response
     * @param id
     * @throws Exception
     */
    //@RequestMapping(value = "showPicForMany")
    public void showPicForMany(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam Long id) throws Exception {
        if (null != id && id != -1) {
            SysFileEntity fileEntity =  sysFileService.selectById(id);
            pictureStyle(fileEntity, request, response);
        }
    }


    private void pictureStyle(SysFileEntity file, HttpServletRequest request, HttpServletResponse response) {
        if (null != file) {
            // 显示的是缩略图
            String relativePath = file.getPath();
            File pf = new File(relativePath);
            if (!pf.exists()) {
                return;
            }
            double rate = 0.2; //rate是压缩比率  1为原图  0.1为最模糊
            int[] results = getImgWidth(pf);
            int widthdist = 0;
            int heightdist = 0;
            try{
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return;
                } else {
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
                Image src = javax.imageio.ImageIO.read(pf);
                BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
                ServletOutputStream fout = response.getOutputStream();
                ImageIO.write(tag, "jpg", fout);
                fout.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽
            result[1] = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "showPicForMany")
    public ResponseEntity<byte[]> downFile (HttpServletRequest request, HttpServletResponse response, @RequestParam Long id){
        try {
            if (null != id && id != -1) {
                SysFileEntity fileEntity =  sysFileService.selectById(id);
                InputStream in=new FileInputStream(new File(fileEntity.getServicePath()));//将该文件加入到输入流之中
                byte[] body=null;
                body=new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
                in.read(body);//读入到输入流里面

                String fileName=new String(fileEntity.getName().getBytes("gbk"),"iso8859-1");//防止中文乱码
                HttpHeaders headers=new HttpHeaders();//设置响应头
                headers.add("Content-Disposition", "attachment;filename="+fileName);
                HttpStatus statusCode = HttpStatus.OK;//设置响应吗
                return new ResponseEntity<byte[]>(body, headers, statusCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpHeaders headers=new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=error");
        HttpStatus statusCode = HttpStatus.NOT_FOUND;//设置响应吗
        byte[] body= JSON.toJSONString(BuildResponse.fail()).getBytes();
        return new ResponseEntity<byte[]>(body,headers, statusCode);
    }
}
