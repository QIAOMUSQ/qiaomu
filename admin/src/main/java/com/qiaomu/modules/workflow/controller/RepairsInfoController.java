package com.qiaomu.modules.workflow.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.Enum.CommunityRoleType;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.sys.service.UserExtendService;
import com.qiaomu.modules.workflow.entity.RepairsInfo;
import com.qiaomu.modules.workflow.service.RepairsInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-10-06 14:59
 */
@Controller
@RequestMapping("mobile/repairs")
public class RepairsInfoController extends AbstractController {

    @Autowired
    private RepairsInfoService repairsInfoService;

    @Autowired
    private SysFileService fileService;

    @Autowired
    private UserExtendService userExtendService;

    /**
     * 保存维修信息
     * @param repairs
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveRepairs",method = RequestMethod.POST)
    public Object saveRepairs(RepairsInfo repairs,HttpServletRequest request){
        try {
            UserExtend condition = new UserExtend();
            condition.setUserId(repairs.getUserId());
            condition.setCommunityId(repairs.getCommunityId());
            UserExtend user = userExtendService.queryUserExtend(condition);
            if (user!=null && !user.getCompanyRoleType().equals(CommunityRoleType.TOURIST.getValue())){
                repairs.setPicture(JSON.toJSONString(fileService.imageUrls(request)));
                repairsInfoService.insert(repairs);
                return BuildResponse.success();
            }else if(user!=null && user.getCompanyRoleType().equals("0")){
                return BuildResponse.fail("请等待个人信息验证");
            }else {
                return BuildResponse.fail("请先进行信息认证");
            }

        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail();
        }

    }

    /**
     * 分页查询信息
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findRepairs",method = RequestMethod.POST)
    public Object findRepairs(@RequestParam Map<String, Object> params){
       /* String CommunityId = getCommunityId();
        if (StringUtils.isNotBlank(CommunityId)){
            params.put("communityId",CommunityId);
        }*/
        PageUtils repairsInfo = repairsInfoService.findRepairsPage(params);
        if(StringUtils.isBlank((String) params.get("companyId"))){
            return BuildResponse.success(JSON.toJSON(repairsInfo));
        }else {
            return R.ok().put("page",repairsInfo);
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryAllRepairsByUserPhone",method = RequestMethod.POST)
    public Object queryAllRepairsByUserPhone(RepairsInfo repairsInfo){
        Map<String,List<RepairsInfo>> repairsInfoMap = repairsInfoService.queryAllRepairsByUserPhone(repairsInfo);
        return BuildResponse.success(repairsInfoMap);
    }


    /**
     * 通过id获取信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findRepairsById",method = RequestMethod.POST)
    public Object findRepairsById(Long id,HttpServletRequest request){
        RepairsInfo repairsInfo = repairsInfoService.findRepairsById(id);
        return BuildResponse.success(JSON.toJSON(repairsInfo));
    }


    /**
     * 维修完成
     * @param id id
     * @param starType 星级
     * @param userOpinion 用户评价
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "finishRepairs",method = RequestMethod.POST)
    public Object finishRepairs(Long id,String starType,String userOpinion){
        try {
            if(StringUtils.isNotBlank(starType)){
                repairsInfoService.finishRepairs(id,starType,userOpinion);
                return BuildResponse.success("感谢对服务进行点评星级");
            }else{
                repairsInfoService.finishRepairs(id,starType,userOpinion);
                return BuildResponse.success("感谢对服务进行评价");
            }
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail("维修点评失败，请联系物业");
        }

    }

    @ResponseBody
    @RequestMapping(value = "invalidRepairs",method = RequestMethod.POST)
    public Object invalidRepairs(Long id){
        try {
            repairsInfoService.invalidRepairs(id);
            return BuildResponse.success("该条记录已作废");
        }catch (Exception e){
            return BuildResponse.fail("维修作废失败，请联系物业");
        }

    }

    /**
     * 分配维修人员
     * @param userId 用户id
     * @param id 维修信息id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "apportionRepairsPerson",method = RequestMethod.POST)
    public Object apportionRepairsPerson(Long userId,Long id){
        try {
           repairsInfoService.apportionRepairsPerson(userId,id);
            return BuildResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail(e.getMessage());
        }
    }

    /**
     * 撤销报修
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "revocationRepairs",method = RequestMethod.POST)
    public Object revocationRepairs(Long id){
        try {
            repairsInfoService.revocationRepairs(id);
            return BuildResponse.success("该条信息已经撤销");
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail(e.getMessage());
        }
    }

    /**
     * 更新维修人员维修反馈信息
     * @param id
     * @param workerOpinion
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateWorkerOpinion",method = RequestMethod.POST)
    public Object updateWorkerOpinion(Long id,String workerOpinion,String type){
        try {
            repairsInfoService.updateWorkerOpinion(id,workerOpinion,type);
            return BuildResponse.success("信息已提交");
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail(e.getMessage());
        }
    }

    /**
     * 修改维修信息
     * @param repairs
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyRepair",method = RequestMethod.POST)
    public Object modifyRepair(RepairsInfo repairs,HttpServletRequest request){
        Map<String,String> imgMap = fileService.imageUrls(request);
        if (imgMap.size()>0){
            repairs.setPicture(JSON.toJSONString(imgMap));
        }
        String info = repairsInfoService.modifyRepair(repairs);
        return BuildResponse.success(info);
    }


}
