package com.qiaomu.modules.infopublish_publish.controller;

import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.infopublish_publish.entity.InvitationEntity;
import com.qiaomu.modules.infopublish_publish.service.InvitationService;
import com.qiaomu.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 李品先
 * @description:发帖子管理
 * @Date 2018-12-07 0:02
 */
@RestController
@RequestMapping(value = "invitation")
public class InvitationManageController extends AbstractController{
    @Autowired
    private InvitationService invitationService;

    /**
     * 根据类型获取信息
     * @param params
     * @return
     */
    @RequestMapping(value = "getInfoPageByType",method = RequestMethod.POST)
    public R getInfoPageByType(@RequestParam Map<String, Object> params){
        params.put("communityId",getCompanyOrCommunityByType("2"));
        PageUtils page = invitationService.queryPage(params);
        return R.ok().put("page", page);

    }

    /**
     *
     * @param invitationEntity
     * @return
     */
    @RequestMapping(value = "insertInvitation",method = RequestMethod.POST)
    public R insertInvitation(InvitationEntity invitationEntity){
     /*   invitationEntity.setCommunityId(getCompanyOrCommunityByType("2"));
        invitationEntity.setCompanyId(getCompanyOrCommunityByType("1"));*/
        invitationService.insert(invitationEntity);
        return R.ok().put("success","ok");
    }

}
