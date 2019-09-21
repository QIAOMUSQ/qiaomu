package com.qiaomu.modules.infopublish.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.infopublish.entity.CarportEntity;
import com.qiaomu.modules.infopublish.service.CarportService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.UserExtendService;
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
    @Autowired
    private UserExtendService userExtendService;
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

        UserExtend userExtendQ = new UserExtend();
        userExtendQ.setUserId(Long.valueOf(sysUserEntity.getUserId()));
        userExtendQ.setCommunityId(carport.getCommunityId());
        UserExtend userExtend = userExtendService.queryUserExtend(userExtendQ);
        if(userExtend==null||userExtend.getCommunityId()==null){
            throw new CommentException("请认证该小区！");
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


}
