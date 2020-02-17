package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.auth.service.KafkaTemplateService;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.entity.SysFileEntity;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
import com.qiaomu.modules.workflow.VO.PushMessageVO;
import com.qiaomu.modules.workflow.VO.TransmissionContentVO;
import com.qiaomu.modules.workflow.dao.RepairsInfoDao;
import com.qiaomu.modules.workflow.dao.UserRepairsDao;
import com.qiaomu.modules.workflow.entity.RepairsInfo;
import com.qiaomu.modules.workflow.entity.UserRepairs;
import com.qiaomu.modules.workflow.enums.RepairsStar;
import com.qiaomu.modules.workflow.enums.RepairsStatus;
import com.qiaomu.modules.workflow.enums.RepairsTypeEnum;
import com.qiaomu.modules.workflow.service.RepairsInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 李品先
 * @description:
 * @Date 2019-10-06 15:03
 */
@Service
public class RepairsInfoServiceImpl extends ServiceImpl<RepairsInfoDao,RepairsInfo> implements RepairsInfoService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private YwCommunityService communityService;

    @Autowired
    private UserExtendService userExtendService;
    @Resource
    private UserRepairsDao userRepairsDao;

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private KafkaTemplateService kafkaTemplateService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(RepairsInfo entity) {
        //查询物业分配的工作人员
        UserExtend repairWorkers = new UserExtend();
        repairWorkers.setCommunityId(entity.getCommunityId());
        repairWorkers.setRepairsType(entity.getRepairsType());
        List<UserExtend> workersList =  userExtendService.findAll(repairWorkers);

        entity.setCreateTime(new Date());
        //如果已经分配人员
        if(workersList.size()>0){
            entity.setApportionTime(new Date());
            entity.setStatus(RepairsStatus.assign.getStatus());
        }else {
            //如果没有分配人员
            entity.setStatus(RepairsStatus.commit.getStatus());
        }
        baseMapper.insert(entity);
        //插入关系表
        for(UserExtend workers : workersList){
            UserRepairs userRepairs = new UserRepairs(workers.getUserId(),entity.getId());
            userRepairsDao.insert(userRepairs);
            //消息推动到工作人员app
            kafkaTemplateService.pushRepairsInfo(
                    new PushMessageVO(
                            workers.getUserPhone(),
                            "社区新的维修信息",
                            entity.getCommunityId(),
                            entity.getDetail(),
                            JSON.toJSONString(new TransmissionContentVO("社区维修",entity.getCommunityId(),entity.getId()))
                     ));
        }


        return true;
    }

    /**
     * 获取维修信息
     * @param params
     *      userId 用户Id
     *      communityId 社区id
     *      repairsType 维修类型 0：电力 1：供水 2：煤气 3房屋
     *      status  状态  0：已提交 1：物业已分派人员 2：处理完成
     *      注：当前申请为3
     *      repairsId 维修人员ID
     * @return
     */
    @Override
    public PageUtils findRepairsPage(Map<String, Object> params) {
        RepairsInfo repairs = new RepairsInfo();
        if (StringUtils.isBlank((String) params.get("companyId"))){
            if (StringUtils.isNotBlank((String) params.get("userId"))){
                repairs.setUserId(Long.valueOf((String) params.get("userId")));
            }
            if (StringUtils.isNotBlank((String) params.get("communityId"))){
                repairs.setCommunityId(Long.valueOf((String) params.get("communityId")));
            }

        }else {
            YwCommunity community = new YwCommunity();
            String companyId = (String) params.get("companyId");
            community.setCompanyId(Long.valueOf(companyId));
            if (StringUtils.isNotBlank((String) params.get("communityName"))){
                community.setName((String) params.get("communityName"));
            }
            List<YwCommunity> list = communityService.findAllByCondition(community);
            List<Long> communityIds = new ArrayList<>();
            list.forEach(a->communityIds.add(a.getId()));
            repairs.setCommunityIds(communityIds);
        }
        if (params.get("repairsType")!=null){
            repairs.setRepairsType((String)params.get("repairsType"));
        }
        if (params.get("status")!=null){
            if (params.get("status").equals("3")){
                repairs.setQueryType("3");
                repairs.setStatus(null);
            }else {
                repairs.setStatus((String)params.get("status"));
            }

        }
        Page<RepairsInfo> page = new Query(params).getPage();
        if (params.get("repairsId")!=null){
            //根据维修人员id查询数据
            repairs.setRepairsId(Long.valueOf((String)params.get("repairsId")));
           /* //查询关系表
            UserRepairs userRepairs = new UserRepairs(Long.valueOf((String)params.get("repairsId")));
            List<UserRepairs> UserRepairsList = userRepairsDao.selectAll(userRepairs);*/
            page.setRecords(this.baseMapper.selectPagesByRepairs(page, repairs));
        }else {
            page.setRecords(this.baseMapper.selectPages(page, repairs));
        }
        for (RepairsInfo info: page.getRecords()){
            info.setStatus(RepairsStatus.status(info.getStatus()).getStatusInfo());
            if (StringUtils.isNotBlank(info.getStarType())){
                info.setStarType(RepairsStar.star(info.getStarType()).getStartInfo());
            }
            if (info.getLingerTime() != null){
                info.setLingerTime(DateUtils.longTimeToDay(Long.valueOf(info.getLingerTime())));
            }else {
                info.setLingerTime(DateUtils.longTimeToDay(new Date().getTime()-info.getCreateTime().getTime()));
            }

            info.setUserRealName(AESUtil.decrypt(info.getUserRealName()));
            info.setRepairsType(RepairsTypeEnum.repairs(info.getRepairsType()).getRepairsInfo());
            if (StringUtils.isNotBlank((String) params.get("companyId"))){
                info.setCommunityName(communityService.selectById(info.getCommunityId()).getName());
            }
            if(info.getImgId() !=null ){
                SysFileEntity file = sysFileService.selectById(info.getImgId());
                if (file !=  null){
                    info.setUserHeadImg(file.getPath());
                }

            }
        }
        return new PageUtils(page);
    }


    @Override
    public RepairsInfo findRepairsById(Long id) {
        RepairsInfo info = baseMapper.findRepairsById(id);
        info.setUserRealName(AESUtil.decrypt(info.getUserRealName()));
        info.setStatus(RepairsStatus.status(info.getStatus()).getStatusInfo());
        if (StringUtils.isNotBlank(info.getStarType())){
            info.setStarType(RepairsStar.star(info.getStarType()).getStartInfo());
        }
        info.setRepairsType(RepairsTypeEnum.repairs(info.getRepairsType()).getRepairsInfo());
        info.setCommunityName(communityService.selectById(info.getCommunityId()).getName());
        if(info.getStatus().equals("已提交") || info.getStatus().equals("已分派")){
            info.setLingerTime(DateUtils.longTimeToDay(new Date().getTime()-info.getCreateTime().getTime()));
        }else {
            info.setLingerTime(DateUtils.longTimeToDay(Long.valueOf(info.getLingerTime())));
        }
        List<UserRepairs> userRepairsList = userRepairsDao.selectByRepairsId(info.getId());
        String repairsName="",repairsPhone = "";
        for (UserRepairs repairs: userRepairsList){
            repairsName += AESUtil.decrypt(repairs.getRepairsName())+" ";
            repairsPhone += repairs.getRepairsPhone() +" ";
        }
        info.setRepairsPhone(repairsPhone);
        info.setRepairsName(repairsName);
        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object apportionRepairsPerson(Long userId, Long id) {
        RepairsInfo info = baseMapper.findRepairsById(id);
        //如果状态是已经完成则不准分配
        if(info.getStatus().equals("2") || info.getStatus().equals("4")){
             return new Exception("该条记录不可再次分配");
        }
        //查询以前是否存在维修信息
        List<UserRepairs> userRepairsList =  userRepairsDao.selectByRepairsId(info.getRepairsId());
        for (UserRepairs repairs : userRepairsList){
            userRepairsDao.deleteById(repairs.getId());
        }
        userRepairsDao.insert(new UserRepairs(userId,id));
        //info.setRepairsId(userId);
        info.setApportionTime(new Date());
        info.setStatus(RepairsStatus.assign.getStatus());
        baseMapper.updateById(info);
        SysUserEntity repairUser = sysUserService.queryById(userId);
        //推送信息
        kafkaTemplateService.pushRepairsInfo(
                new PushMessageVO(
                        repairUser.getUsername(),
                        "社区分配维修信息",
                        info.getCommunityId(),
                        info.getDetail(),
                        JSON.toJSONString(new TransmissionContentVO("社区维修",info.getCommunityId(),info.getId()))
                ));
        return "分配成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishRepairs(Long id, String starType, String userOpinion) {
        RepairsInfo info = baseMapper.findRepairsById(id);
        if (StringUtils.isNotBlank(starType)){
            info.setStarType(starType);
        }
        if (StringUtils.isNotBlank(userOpinion)){
            info.setUserOpinion(userOpinion);
        }
        info.setRepairsTime(new Date());
        info.setLingerTime(String.valueOf(new Date().getTime()- info.getCreateTime().getTime()));
        info.setStatus(RepairsStatus.finish.getStatus());
        baseMapper.updateById(info);
        //查询以前是否存在维修信息
        List<UserRepairs> userRepairsList =  userRepairsDao.selectByRepairsId(info.getRepairsId());
        for (UserRepairs repairs : userRepairsList){
            SysUserEntity repairUser = sysUserService.queryById(repairs.getUserId());
            kafkaTemplateService.pushRepairsInfo(
                    new PushMessageVO(
                            repairUser.getUsername(),
                            "社区维修结束信息",
                            info.getCommunityId(),
                            "物业客户已经评价您的维修任务，请及时查看",
                            JSON.toJSONString(new TransmissionContentVO("社区维修",info.getCommunityId(),info.getId()))
                    ));
        }


    }

    @Override
    public void invalidRepairs(Long id) {
        RepairsInfo info = baseMapper.findRepairsById(id);
        info.setStatus(RepairsStatus.invalid.getStatus());
        baseMapper.updateById(info);
    }

    @Override
    public List<HashMap<String,String>> staticRepairsByStatus(String communityId){
        List<HashMap<String,String>> statusData = baseMapper.staticRepairsByStatus(communityId);
        return statusData;
    }

    @Override
    public List<HashMap<String,String>> staticRepairsByrepairsType(String communityId){
        List<HashMap<String,String>> repairData = baseMapper.staticRepairsByrepairsType(communityId);
        return repairData;
    }
    @Override
    public List<RepairsInfo> staticRepairsByAssign(String communityId){
        List<RepairsInfo> repairsInfos = baseMapper.staticRepairsByAssign(communityId);
        return repairsInfos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revocationRepairs(Long id) {
        RepairsInfo info = baseMapper.selectById(id);
        if (info.getStatus().equals(RepairsStatus.finish.getStatus())){
            try {
                throw new Exception("该处理已经结束，不能撤销");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            /*info.setStatus(RepairsStatus.invalid.getStatus());
            info.setLingerTime(String.valueOf(new Date().getTime()- info.getCreateTime().getTime()));
            baseMapper.updateById(info);
            //查询以前是否存在维修信息
            List<UserRepairs> userRepairsList =  userRepairsDao.selectByRepairsId(info.getRepairsId());
            for (UserRepairs repairs : userRepairsList){
                SysUserEntity repairUser = sysUserService.queryById(repairs.getUserId());
                kafkaTemplateService.pushRepairsInfo(
                        new PushMessageVO(
                                repairUser.getUsername(),
                                "社区维修撤销信息",
                                info.getCommunityId(),
                                "物业客户已经撤销您的维修任务，请及时查看",
                                JSON.toJSONString(new TransmissionContentVO("社区维修",info.getCommunityId(),info.getId()))
                        ));
            }*/
            baseMapper.deleteById(id);
            //需要删除图片
            //sysFileService.deleteFileByHttpUrl(info.getPicture());
            List<UserRepairs> userRepairsList = userRepairsDao.selectByRepairsId(info.getRepairsId());
            for (UserRepairs repairs : userRepairsList) {
                userRepairsDao.deleteById(repairs.getId());
            }

        }
    }

    @Override
    public void updateWorkerOpinion(Long id, String workerOpinion, String type) {
        RepairsInfo info = baseMapper.findRepairsById(id);
        if (info != null) {
            info.setRepairsWorkerOpinion(workerOpinion);
            info.setStatus(type);
            info.setRepairsTime(new Date());
            info.setLingerTime(String.valueOf(new Date().getTime() - info.getCreateTime().getTime()));
            baseMapper.updateById(info);
        }
    }

    @Override
    public String modifyRepair(RepairsInfo repairs) {
        RepairsInfo info = baseMapper.findRepairsById(repairs.getId());
        if (info == null) {
            return "请选择需要修改信";
        }
        info.setServiceDate(repairs.getServiceDate());
        info.setLocation(repairs.getLocation());
        info.setDetail(repairs.getDetail());
        if (repairs.getPicture() != null) {
            info.setPicture(repairs.getPicture());
        }
        baseMapper.updateById(info);
        return "修改成功";
    }

    /**
     * userId
     * repairsType
     * status
     * repairsId
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<RepairsInfo>> queryAllRepairsByUserPhone(RepairsInfo params) {
        if ("3".equals(params.getStatus())) {
            params.setQueryType("3");
            params.setStatus(null);
        }
        List<RepairsInfo> repairsInfoList = new ArrayList<>();
        if (params.getRepairsId() != null) {
            //根据维修人员id查询数据
            repairsInfoList = this.baseMapper.selectPagesByRepairs(params);
        } else {
            repairsInfoList = this.baseMapper.selectPages(params);
        }
        Map<String, List<RepairsInfo>> resultMap = new HashMap<>();
        for (RepairsInfo info : repairsInfoList) {
            info.setStatus(RepairsStatus.status(info.getStatus()).getStatusInfo());
            if (StringUtils.isNotBlank(info.getStarType())) {
                info.setStarType(RepairsStar.star(info.getStarType()).getStartInfo());
            }
            if (info.getLingerTime() != null) {
                info.setLingerTime(DateUtils.longTimeToDay(Long.valueOf(info.getLingerTime())));
            } else {
                info.setLingerTime(DateUtils.longTimeToDay(new Date().getTime() - info.getCreateTime().getTime()));
            }

            info.setUserRealName(AESUtil.decrypt(info.getUserRealName()));
            info.setRepairsType(RepairsTypeEnum.repairs(info.getRepairsType()).getRepairsInfo());
            if (info.getImgId() != null) {
                SysFileEntity file = sysFileService.selectById(info.getImgId());
                if (file != null) {
                    info.setUserHeadImg(file.getPath());
                }
            }
            if (params.getRepairsId() != null) {
                List<UserRepairs> userRepairsList = userRepairsDao.selectByRepairsId(info.getId());
                String repairsName = "", repairsPhone = "";
                for (UserRepairs repairs : userRepairsList) {
                    repairsName += AESUtil.decrypt(repairs.getRepairsName()) + " ";
                    repairsPhone += repairs.getRepairsPhone() + " ";
                }
                info.setRepairsPhone(repairsPhone);
                info.setRepairsName(repairsName);
            }
            if (resultMap.get(info.getRepairsType()) != null) {
                resultMap.get(info.getRepairsType()).add(info);
            } else {
                List<RepairsInfo> mapList = new ArrayList<>();
                mapList.add(info);
                resultMap.put(info.getRepairsType(), mapList);
            }

        }

        return resultMap;
    }

    @Override
    public List<RepairsInfo> getCompanyRepairStatistic(RepairsInfo repairsInfo) {
        List<RepairsInfo> infos = this.baseMapper.getCompanyRepairStatistic(repairsInfo);
        infos.forEach(info->{
            info.setRepairsName(RepairsTypeEnum.repairs(info.getRepairsType()).getRepairsInfo());
        });
        return infos;
    }
}
