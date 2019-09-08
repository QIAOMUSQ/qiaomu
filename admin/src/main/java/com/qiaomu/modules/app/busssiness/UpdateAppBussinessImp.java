package com.qiaomu.modules.app.busssiness;

/**
 * Created by wenglei on 2019/7/6.
 */


import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.CommonUtils;
import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.app.entity.AppUpdateEntity;
import com.qiaomu.modules.app.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import static com.qiaomu.common.utils.Constant.OUT_DIR;
import static com.qiaomu.common.utils.Constant.SERVER_URL;


/**
 * Created by wenglei on 2018/11/12.
 */
@Service
public class UpdateAppBussinessImp {
    @Autowired
    private BaseService baseService;

    public Map<String,Object> uploadFile(HttpServletRequest request) {

        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath =OUT_DIR;    //需要放到spring容器中，待修改;()

        //消息提示
        String message = "";
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;

            Map<String,String[]> params = req.getParameterMap();
            String appVersion = CommonUtils.getMapValue("appVersion",params)[0];
            String updateType = CommonUtils.getMapValue("updateType",params)[0];
            String clientType = CommonUtils.getMapValue("clientType",params)[0];



            MultipartFile multipartFile = req.getFile("file");
            String fileName =CommonUtils.mkFileName(multipartFile.getOriginalFilename()) ;
            //String savePathStr = mkFilePath(savePath, fileName);
            File saveFile = new File(savePath+fileName);
            multipartFile.transferTo(saveFile);

            AppUpdateEntity appUpdateEntity = new AppUpdateEntity();
            appUpdateEntity.setAppVersion(appVersion);
            appUpdateEntity.setCreatedAs(DateUtils.formats(new Date()));
            appUpdateEntity.setUpdateType(updateType);
            appUpdateEntity.setClientType(clientType);

            String url = SERVER_URL+"/outapp/"+fileName;
            appUpdateEntity.setAppUrl(url);
            baseService.addAppUpdateInfo(appUpdateEntity);


        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail("文件上传失败[UL-1001]");
        }

        return BuildResponse.success();
    }


    public R downloadFile(HttpServletRequest request) {
        return null;
    }


    public Map<String,Object> isNeedUpdate(String version,String clientType) {
        AppUpdateEntity appUpdateEntity = baseService.getMaxVersion(clientType);
        if(appUpdateEntity==null){
            Map<String,Object> returnMap = BuildResponse.success();
            returnMap.put("isNeedUpdate","0");
            returnMap.put("appUrl","");
            returnMap.put("appVersion",version);
            return returnMap;
        }
        String maxVersion = appUpdateEntity.getAppVersion();
        if(version==null||!version.matches("\\d+\\.\\d+\\.\\d+")){
            return BuildResponse.fail("版本格式错误！");
        }

        if(version.compareTo(maxVersion)<0){
            Map<String,Object> returnMap = BuildResponse.success();
            returnMap.put("isNeedUpdate","1");
            returnMap.put("appUrl",appUpdateEntity.getAppUrl());
            returnMap.put("appVersion",appUpdateEntity.getAppVersion());
            return returnMap;
        }else {
            Map<String, Object> returnMap = BuildResponse.success();
            returnMap.put("isNeedUpdate", "0");
            returnMap.put("appUrl", "");
            returnMap.put("appVersion",version);
            return returnMap;
        }
    }

//    @Override
//    public R downloadFile(HttpServletRequest request) {
//        //得到要下载的文件名
//        String fileName = request.getParameter("filename");
//        fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
//        //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
//        String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/upload");
//        //        处理文件名
//        String realname = fileName.substring(fileName.indexOf("_")+1);
//        //通过文件名找出文件的所在目录
//        String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
//        //得到要下载的文件
//        File file = new File(path+File.separator+fileName);
//        //如果文件不存在
//        if(!file.exists()){
//            request.setAttribute("message", "您要下载的资源已被删除！！");
//           // request.getRequestDispatcher("/message.jsp").forward(request, response);
//            return R.error();
//        }
//
//        //设置响应头，控制浏览器下载该文件
//       // response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
//        //读取要下载的文件，保存到文件输入流
//        FileInputStream in = new FileInputStream(path + File.separator + fileName);
//        //创建输出流
//        OutputStream os = response.getOutputStream();
//        //设置缓存区
//        byte[] bytes = new byte[1024];
//        int len = 0;
//        while((len = in.read(bytes))>0){
//            os.write(bytes);
//        }
//        //关闭输入流
//        in.close();
//        //关闭输出流
//        os.close();
//        return null;
//    }

    //通过文件名和存储上传文件根目录找出要下载的文件的所在路径
    public String findFileSavePathByFileName(String fileName,String fileSaveRootPath){
        int hashcode = fileName.hashCode();
        int dir1 = hashcode&0xf;
        int dir2 = (hashcode&0xf0)>>4;
        String dir = fileSaveRootPath + "\\" + dir1 + "\\" + dir2;
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }




    public ResponseEntity<byte[]> fileDownLoad(HttpServletRequest request) throws Exception{
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;

        Map<String,String[]> params = req.getParameterMap();
        String url = CommonUtils.getMapValue("url",params)[0];
        ServletContext servletContext = request.getServletContext();
        String fileName=url.split("outapp")[1];
        String realPath = "/www/app/admin/staticFile/outapp/"+fileName;//得到文件所在位置
        InputStream in=new FileInputStream(new File(realPath));//将该文件加入到输入流之中
        byte[] body=null;
        body=new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
        in.read(body);//读入到输入流里面

        fileName=new String(fileName.getBytes("gbk"),"iso8859-1");//防止中文乱码
        HttpHeaders headers=new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename="+fileName);
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;


    }


}

