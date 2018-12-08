package io.renren.modules.android.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.R;
import io.renren.modules.android.entity.Community;
import io.renren.modules.android.service.CommunityService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2018-11-28 21:41
 */
@Controller
@RequestMapping(value = "/App/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private SysDeptService sysDeptService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public R getCommunityList(){
       // List<Community> communities = communityService.selectList(null);
        List<SysDeptEntity> deptList = sysDeptService.selectList(null);
        return R.ok("success", JSON.toJSON(deptList));
    }

}
