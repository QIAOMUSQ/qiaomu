package io.renren.modules.android.bussiness;

import io.renren.common.utils.CommonUtils;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.android.entity.AppUpdateEntity;
import io.renren.modules.android.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import static io.renren.common.utils.Constant.BASE_URL;

/**
 * Created by wenglei on 2018/11/12.
 */
@Service
public class UpdateAppBussinessImpl implements UpdateAppBussiness {
    @Autowired
    private BaseService baseService;

    @Override
    public R uploadFile(HttpServletRequest request) {

        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = "d:\\appUpload\\download";    //需要放到spring容器中，待修改;()

        //消息提示
        String message = "";
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;

            Map<String,String[]> params = req.getParameterMap();
            String appVersion = CommonUtils.getMapValue("appVersion",params).toString();
            String updateType = CommonUtils.getMapValue("updateType",params).toString();



            MultipartFile multipartFile = req.getFile("file");
            String fileName =mkFileName(multipartFile.getOriginalFilename()) ;
            //String savePathStr = mkFilePath(savePath, fileName);
            File saveFile = new File(savePath+"/"+fileName);
            multipartFile.transferTo(saveFile);

            AppUpdateEntity appUpdateEntity = new AppUpdateEntity();
            appUpdateEntity.setAppVersion(appVersion);
            appUpdateEntity.setCreatedAs(DateUtils.format(new Date(),"yyyy-MM-dd"));
            appUpdateEntity.setUpdateType(updateType);

            String url = BASE_URL+"/update/"+fileName;
            appUpdateEntity.setAppUrl(url);
            baseService.addAppUpdateInfo(appUpdateEntity);


        }catch (Exception e){
            e.printStackTrace();
            return R.error(1001,"文件上传失败[UL-1001]");
        }

        return R.ok("文件上传成功");
    }

    @Override
    public R downloadFile(HttpServletRequest request) {
        return null;
    }

    @Override
    public R isNeedUpdate(String version) {
        AppUpdateEntity appUpdateEntity = baseService.getMaxVersion();
        String maxVersion = appUpdateEntity.getAppVersion();
        if(version==null||!version.matches("\\d+\\.\\d+\\.\\d+")){
            return R.ok(1001,"版本格式错误！");
        }

        R r = R.ok(1000,"成功");
        if(version.compareTo(maxVersion)>0){
            r.put("isNeedUpdate","1");
            r.put("appUrl",appUpdateEntity.getAppUrl());

        }else{
            r.put("isNeedUpdate","0");
            r.put("appUrl","");
        }
        return r;
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


    //生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
    public String mkFileName(String fileName){
        return UUID.randomUUID().toString()+"_"+fileName;
    }
    public String mkFilePath(String savePath,String fileName){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = fileName.hashCode();
        int dir1 = hashcode&0xf;
        int dir2 = (hashcode&0xf0)>>4;
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }
}
