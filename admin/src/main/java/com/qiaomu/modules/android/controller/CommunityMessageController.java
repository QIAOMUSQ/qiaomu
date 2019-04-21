package com.qiaomu.modules.android.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.android.entity.City;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.CityService;
import com.qiaomu.modules.sys.service.SysDeptService;
import com.qiaomu.modules.sys.service.YwCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping({"/App/communityMessage"})
public class CommunityMessageController
{

    @Autowired
    private CityService cityService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private YwCommunityService communityService;

    @ResponseBody
    @RequestMapping(value={"/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R getCommunityList(@RequestParam Map<String, Object> params, ServletRequest request)
    {
        PageUtils page = this.communityService.queryPage(params);

        return R.ok("success", page);
    }
    @ResponseBody
    @RequestMapping(value={"getCity"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R getCity() {
        return R.ok("success", JSON.toJSON(this.cityService.selectList(null)));
    }
    @ResponseBody
    @RequestMapping(value={"selectCity"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R select() { List cityList = this.cityService.queryList(new HashMap());
        City root = new City();
        root.setId(Long.valueOf(0L));
        root.setName("城市");
        return R.ok().put("cityList", cityList);
    }

    @ResponseBody
    @RequestMapping(value={"findCommunityAdministrator"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R findCommunityByQuery(String communityName, Long communityId)
    {
        YwCommunity community = new YwCommunity();

        if (communityId != null) community.setId(communityId);
        community.setName(communityName);
        String info = this.communityService.findAdministratorNum(community);

        return R.ok("success", info);
    }



    @ResponseBody
    @RequestMapping(value={"addCommunityMember"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public R addCommunityMember(String pathId, String phone, Long communityId, String realName, String address, String identityInfo, String sex)
    {
        String data = this.communityService.addCommunityMember(pathId, phone, communityId, realName, address, identityInfo, sex);
        return R.ok("success", data);
    }
}
