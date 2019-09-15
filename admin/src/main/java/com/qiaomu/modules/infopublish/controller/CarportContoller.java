package com.qiaomu.modules.infopublish.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import com.qiaomu.modules.infopublish.service.CarportService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author 李品先
 * @description: 车辆管理
 * @Date 2019-07-27 0:16
 */
@RestController
@RequestMapping("mobile/carport")
public class CarportContoller extends AbstractController{
    @Autowired
    private CarportService carportService;

    /**
     * 获取车辆出租信息
     * @param carport
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    public Object getPage(CarportEntity carport){
        SysUserEntity sysUserEntity = getUser();
        if(sysUserEntity==null||sysUserEntity.getRealName()==null||sysUserEntity.getRealName().isEmpty()){
            throw new CommentException("请先实名认证！");
        }
         List<CarportEntity> data =  carportService.selectAll(carport);
        return BuildResponse.success(JSON.toJSON(data));
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Object save(Long userId, Long communityId, String type, String title, String content, HttpServletRequest request){
        CarportEntity carport = new CarportEntity();
        carport.setUserId(userId);
        carport.setCommunityId(communityId);
        carport.setType(type);
        carport.setTitle(title);
        carport.setContent(content);
        boolean info = carportService.save(carport,request);
        if (info){
            return BuildResponse.success();
        }else {
            return BuildResponse.fail();
        }
    }


    @RequestMapping(value = "getCarportById",method = RequestMethod.POST)
    public Object getCarportById(Long id){
        return BuildResponse.success(carportService.getCarportById(id));
    }

    @RequestMapping(value = "deleteCarportById",method = RequestMethod.POST)
    public Object deleteCarportById(Long id){
        try {
            if (carportService.deleteCarportById(id)){
                return BuildResponse.success();
            }else {
                return BuildResponse.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail();
        }

    }

    /**
     * 更新车辆位信息
     * @param carportEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "updateCarport",method = RequestMethod.POST)
    public Object updateCarport(CarportEntity carportEntity,HttpServletRequest request){
        try {
            carportService.updateCarport(carportEntity,request);
            return BuildResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail();
        }
    }

}
