package com.qiaomu.modules.workflow.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.annotation.SysLog;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.UserExtendService;
import com.qiaomu.modules.workflow.entity.InvitationEntity;
import com.qiaomu.modules.workflow.service.InvitationService;
import com.qiaomu.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author 李品先
 * @description:公告管理
 * @Date 2018-12-07 0:02
 */
@Controller
@RequestMapping(value = "mobile/invitation")
public class InvitationManageController extends AbstractController {
    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserExtendService userExtendService;
    /**
     * 根据类型获取信息
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pageList",method = RequestMethod.POST)
    public R getInfoPageByType(@RequestParam Map<String, Object> params){
       /* String CommunityId = getCommunityId();
        if (StringUtils.isNotBlank(CommunityId)){
            params.put("communityId",CommunityId);
        }*/

        Long companyId = getCompanyId();
        if (companyId!=null && companyId>0){
            params.put("companyId",getCompanyId());
        }else {
            SysUserEntity sysUserEntity = getUser();
            if(sysUserEntity==null||sysUserEntity.getRealName()==null||sysUserEntity.getRealName().isEmpty()){
                throw new CommentException("请先实名认证！");
            }

            UserExtend userExtendQ = new UserExtend();
            userExtendQ.setUserId(Long.valueOf(sysUserEntity.getUserId()));
            userExtendQ.setCommunityId(Long.valueOf((String) params.get("communityId")));
            UserExtend userExtend = userExtendService.queryUserExtend(userExtendQ);

            if(userExtend==null
                    || !"1".equals(userExtend.getCheck())
                    || Integer.valueOf(userExtend.getCompanyRoleType()) >=4){
                throw new CommentException("请认证该小区！");
            }
        }
        PageUtils page = invitationService.queryPage(params);
        return R.ok().put("page", page);

    }

    @RequestMapping("getSavePage")
    public String getSavePage(String type, ModelMap map,Long id){
        //
        map.put("type",type);
        map.put("companyId",getCompanyId());
        map.put("id",id);
        return "modules/invitation/add";
    }

    @ResponseBody
    @SysLog(value = "保存公告")
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Object save(InvitationEntity invitation, ServletRequest request){
        try {
            invitation.setContent((String) WebUtils.toHttp(request).getAttribute("content"));
            invitation.setUserId(getUserId());
            invitationService.save(invitation);
            return BuildResponse.success("保存成功");
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.success("保存失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public Object delete(@RequestBody Long[] ids){
        invitationService.deleteBatchIds(Arrays.asList(ids));
        return BuildResponse.success("删除成功");
    }

    @ResponseBody
    @RequestMapping(value = "getById")
    public Object getById(Long id){
        return BuildResponse.success(JSON.toJSON(invitationService.selectById(id)));
    }
}
