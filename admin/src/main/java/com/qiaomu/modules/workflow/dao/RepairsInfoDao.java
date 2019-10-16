package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.workflow.entity.RepairsInfo;

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

}