package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.workflow.entity.RepairsInfo;

import java.util.HashMap;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-10-06 14:50
 */
public interface RepairsInfoDao extends BaseMapper<RepairsInfo> {



    List<RepairsInfo> findRepairs(RepairsInfo repairs);

    List<RepairsInfo> selectPages(Page<RepairsInfo> page, RepairsInfo repairs);

    RepairsInfo findRepairsById(Long id);

    List<RepairsInfo> selectPagesByRepairs(Page<RepairsInfo> page, RepairsInfo repairs);

    List<HashMap<String,String>> staticRepairsByStatus(String communityId);

    List<HashMap<String,String>> staticRepairsByrepairsType(String communityId);

    List<RepairsInfo> staticRepairsByAssign(String communityId);

    List<RepairsInfo> selectPagesByRepairs(RepairsInfo repairs);
    List<RepairsInfo> selectPages(RepairsInfo repairs);

    List<RepairsInfo> getCompanyRepairStatistic(RepairsInfo repairsInfo);
}
