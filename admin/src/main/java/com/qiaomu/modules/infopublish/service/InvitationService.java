package com.qiaomu.modules.infopublish.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.infopublish.entity.InvitationEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-22 23:06
 */
public interface InvitationService extends IService<InvitationEntity> {
    PageUtils queryPage(Map<String, Object> params);


    void save(InvitationEntity invitation);

    List<InvitationEntity> selectByCommunityId(Long communityId);

    void deleteByCommunity(Long communityId);
}
