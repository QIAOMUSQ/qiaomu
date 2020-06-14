package com.qiaomu.modules.workflow.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.workflow.entity.InvitationEntity;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-22 22:46
 */
public interface InvitationDao extends BaseMapper<InvitationEntity> {

    List<InvitationEntity> selectPageAll(Page<InvitationEntity> page, @Param("entity") InvitationEntity entity);

    @Override
    InvitationEntity selectById(Serializable id);

    void deleteByCommunity(Long communityId);

    List<InvitationEntity> selectByCommunityId(Long communityId);
}
