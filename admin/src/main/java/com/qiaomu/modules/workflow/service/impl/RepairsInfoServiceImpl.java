package com.qiaomu.modules.workflow.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.propertycompany.service.YwCommunityService;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.YwCommunity;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.workflow.dao.RepairsInfoDao;
import com.qiaomu.modules.workflow.entity.RepairsInfo;
import com.qiaomu.modules.workflow.enums.RepairsStar;
import com.qiaomu.modules.workflow.enums.RepairsStatus;
import com.qiaomu.modules.workflow.enums.RepairsTypeEnum;
import com.qiaomu.modules.workflow.service.RepairsInfoService;
import jodd.util.ArraysUtil;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(RepairsInfo entity) {
        entity.setCreateTime(new Date());
        entity.setStatus(RepairsStatus.commit.getStatus());
        return super.insert(entity);
    }

    /**
     * 获取维修信息
     * @param params
     *      userId 用户Id
     *      communityId 社区id
     *      repairsType 维修类型 0：电力 1：供水 2：煤气 3房屋
     *      status  状态  0：已提交 1：物业已分派人员 2：处理完成
     *      repairsId 维修人员ID
     * @return
     */
    @Override
    public PageUtils findRepairsPage(Map<String, Object> params) {
        RepairsInfo repairs = new RepairsInfo();
        if (StringUtils.isBlank((String) params.get("companyId"))){
            repairs.setUserId(Long.valueOf((String) params.get("userId")));
            repairs.setCommunityId(Long.valueOf((String) params.get("communityId")));
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
            repairs.setStatus((String)params.get("status"));
        }
        if (params.get("repairsId")!=null){
            repairs.setRepairsId(Long.valueOf((String)params.get("repairsId")));
        }

        Page<RepairsInfo> page = new Query(params).getPage();
        page.setRecords(this.baseMapper.selectPages(page, repairs));
        for (RepairsInfo info: page.getRecords()){
            info.setStatus(RepairsStatus.status(info.getStatus()).getStatusInfo());
            if (StringUtils.isNotBlank(info.getStarType())){
                info.setStarType(RepairsStar.star(info.getStarType()).getStartInfo());
            }
            info.setUserRealName(AESUtil.decrypt(info.getUserRealName()));
            info.setRepairsType(RepairsTypeEnum.repairs(info.getRepairsType()).getRepairsInfo());
            if (StringUtils.isNotBlank((String) params.get("companyId"))){
                info.setCommunityName(communityService.selectById(info.getCommunityId()).getName());
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
        if (info.getRepairsId()!=null){
            SysUserEntity user = sysUserService.queryById(info.getRepairsId());
            if (user!=null){
                info.setRepairsPhone(user.getUsername());
                info.setRepairsName(AESUtil.decrypt(user.getRealName()));
            }
        }
        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void apportionRepairsPerson(Long userId, Long id) {
        RepairsInfo info = baseMapper.findRepairsById(id);
        info.setRepairsId(userId);
        info.setApportionTime(new Date());
        info.setStatus(RepairsStatus.assign.getStatus());
        baseMapper.updateById(info);
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
        info.setStatus(RepairsStatus.finish.getStatus());
        baseMapper.updateById(info);
    }

    @Override
    public void invalidRepairs(Long id) {
        RepairsInfo info = baseMapper.findRepairsById(id);
        info.setStatus(RepairsStatus.invalid.getStatus());
        baseMapper.updateById(info);
    }


}
