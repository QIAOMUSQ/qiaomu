package com.qiaomu.modules.app.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.app.entity.City;
import com.qiaomu.modules.app.entity.CommunityCheckEntity;
import com.qiaomu.modules.app.service.CommunityCheckService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.CityService;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 13:28
 */

@Controller
@RequestMapping("mobile/communityMessage")
public class CommunityMessageController  extends AbstractController {

    Logger logger = LoggerFactory.getLogger(CommunityMessageController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private CommunityCheckService communityCheckService;

    @Autowired
    private ProvinceCityDateService provinceCityDateService;


    /**
     * 获取社区列表
     * @param params
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public R getCommunityList(@RequestParam Map<String, Object> params, ServletRequest request) {
        SysUserEntity user = getUser();
        if(user.getCompanyId()!= null ){
            params.put("companyId",user.getCompanyId());
        }
        PageUtils page = this.communityService.queryPage(params);
        return R.ok("success", page);
    }


    @ResponseBody
    @RequestMapping(value = "selectCity", method = RequestMethod.POST)
    public R select() {
        List cityList = this.cityService.queryList(new HashMap());
        City root = new City();
        root.setId(Long.valueOf(0L));
        root.setName("城市");
        return R.ok().put("cityList", cityList);
    }

    /**
     * 新增用户审核信息
     * @param pathId 图片存库的地址id
     * @param phone 号码
     * @param communityId 社区ID
     * @param realName 真实姓名
     * @param address 住址
     * @param identityInfo 身份证号码
     * @param sex 性别
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addCommunityMember", method = RequestMethod.POST)
    public Object addCommunityMember(String pathId, String phone, Long communityId, String realName, String address, String identityInfo, String sex) {
        String data = this.communityService.addCommunityMember(pathId, phone, communityId, realName, address, identityInfo, sex);
        if(data.equals("保存成功")){
            return BuildResponse.success(data);
        }else {
            return BuildResponse.fail(data);
        }

    }


    /**
     * 新增社区审核信息
     * @param params
     *         参数 userPhone:用户手机号码
     *              cityCode: 城市编号
     *              communityName:社区名称
     *              name:申请人姓名
     *              contactPhone：联系人号码
     *              address：小区地址
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addCommunityCheckInfo",method = RequestMethod.POST)
    public R addCommunityCheckInfo(@RequestParam Map<String, Object> params){
        try {
            //对社区信息验证
            String userPhone = (String) params.get("userPhone");
            String cityName = (String) params.get("cityName");
            String communityName = (String) params.get("communityName");
            String name = (String) params.get("name");
            String contactPhone = (String) params.get("contactPhone");
            String address = (String) params.get("address");

            YwCommunity community = new YwCommunity();
            community.setName(communityName);
            community.setCityName(cityName);
            List<YwCommunity> communities =  communityService.findAllByCondition(community);
            if(communities.size()>0){
                return R.ok("error","该社区已注册");
            }
            ProvinceCityDateEntity cityDateEntity = provinceCityDateService.getProvCityByCityName(cityName);
            CommunityCheckEntity communityCheck = new CommunityCheckEntity();
            communityCheck.setUserPhone(userPhone);
            communityCheck.setCommunityName(communityName);
            communityCheck.setCityCode(cityDateEntity.getCityCode());
            communityCheck.setName(name);
            communityCheck.setContactPhone(contactPhone);
            communityCheck.setAddress(address);
            communityCheck.setCreateTime(new Date());
            communityCheck.setIsCheck("0");
            communityCheckService.insert(communityCheck);
            return R.ok("success","保存成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.error(DateTime.now().toString("YYYY-MM-dd HH:mm:ss")+
                    "CommunityMessageController.addCommunityCheckInfo"+ JSON.toJSON(params));
            return R.ok("error","保存失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "queryCommunityCheck",method = RequestMethod.POST)
    @RequiresPermissions("community:check")
    public R queryCommunity(@RequestParam Map<String, Object> params){
        PageUtils page =  communityCheckService.queryPage(params);
        return  R.ok().put("page", page);
    }

    /**
     * 跳转审核页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "getInfoDataById",method = RequestMethod.GET)
    public String getInfoDataById(Long id, ModelMap  model){
        model.addAttribute("model",JSON.toJSONString(communityCheckService.selectById(id)));
        return "modules/propertyCompany/check";
    }

    /**
     * 新增社区
     * @param community
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addCommunity",method = RequestMethod.POST)
    public R addCommunity(CommunityCheckEntity community){
       String info = communityCheckService.save(community);
        if(info.equals("success")){
            return R.ok("success","操作成功");
        }else {
            return R.ok("error","操作失败");
        }
    }



    /**
     * 获取用户所在社区权限
     * @param userId
     * @param communityId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCommunityUserPermission",method = RequestMethod.POST)
    public Object getCommunityUserPermission(Long userId,Long communityId){
        UserExtend user = communityService.getCommunityUserPermission(userId,communityId);
        if(user != null){
            return BuildResponse.success(JSON.toJSON(user));
        }else {
            return BuildResponse.fail("用户未认证");
        }

    }

    /**
     * 设置物业公司
     * @param checkCommunityId
     * @param companyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "changeCompany",method = RequestMethod.POST)
    public Object changeCompany(Long checkCommunityId,Long companyId){
        try {
            String info = communityCheckService.changeCompany(checkCommunityId,companyId);
            return BuildResponse.success(info);
        }catch (Exception e){
            return  BuildResponse.fail("error");
        }


    }

}
