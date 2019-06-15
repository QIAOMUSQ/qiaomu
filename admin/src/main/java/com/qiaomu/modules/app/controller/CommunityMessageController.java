package com.qiaomu.modules.app.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.app.entity.City;
import com.qiaomu.modules.app.entity.CommunityCheckEntity;
import com.qiaomu.modules.app.service.CommunityCheckService;
import com.qiaomu.modules.sys.entity.ProvinceCityDateEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.CityService;
import com.qiaomu.modules.sys.service.ProvinceCityDateService;
import com.qiaomu.modules.sys.service.YwCommunityService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 13:28
 */

@Controller
@RequestMapping({"mobile/communityMessage"})
public class CommunityMessageController {

    Logger logger = LoggerFactory.getLogger(CommunityMessageController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private CommunityCheckService communityCheckService;

    @Autowired
    private ProvinceCityDateService provinceCityDateService;

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public R getCommunityList(@RequestParam Map<String, Object> params, ServletRequest request) {
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
     * 新增用户成员
     * @param pathId 图片存库的地址id
     * @param phone
     * @param communityId
     * @param realName 真实姓名
     * @param address 住址
     * @param identityInfo 身份证号码
     * @param sex 性别
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addCommunityMember", method = RequestMethod.POST)
    public R addCommunityMember(String pathId, String phone, Integer communityId, String realName, String address, String identityInfo, String sex) {
        String data = this.communityService.addCommunityMember(pathId, phone, communityId, realName, address, identityInfo, sex);
        return R.ok("success", data);
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
    @RequestMapping(value = "addCommunityCheckInfo",method = RequestMethod.GET)
    public R addCommunityCheckInfo(@RequestParam Map<String, Object> params){
        String userPhone = (String) params.get("userPhone");
        String cityCode = (String) params.get("cityCode");
        String communityName = (String) params.get("communityName");
        String name = (String) params.get("name");
        String contactPhone = (String) params.get("contactPhone");
        String address = (String) params.get("address");
        try {
            //对社区信息验证
            YwCommunity community = new YwCommunity();
            community.setName(communityName);
            community.setCityCode(cityCode);
            List<YwCommunity> communities =  communityService.findAllByCondition(community);
            if(communities.size()>0){
                return R.ok("error","该社区已注册");
            }
            CommunityCheckEntity communityCheck = new CommunityCheckEntity();
            communityCheck.setUserPhone(userPhone);
            communityCheck.setCommunityName(communityName);
            communityCheck.setCityCode(cityCode);
            communityCheck.setName(name);
            communityCheck.setContactPhone(contactPhone);
            communityCheck.setAddress(address);
            communityCheckService.insert(communityCheck);
            return R.ok("success","保存成功");
        }catch (Exception e){
            logger.error(DateTime.now().toString("YYYY-MM-dd HH:mm:ss")+
                    " CommunityMessageController.addCommunityCheckInfo"+ JSON.toJSON(params));
            return R.ok("error","保存失败");
        }
    }

}
