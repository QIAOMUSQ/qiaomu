package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.modules.sys.entity.UserExtend;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-17 7:07
 */
public interface UserExtendDao extends BaseMapper<UserExtend> {
    List<UserExtend> getUserExtend(String userPhone);

    UserExtend selectAll(UserExtend userExtend);

    List<UserExtend> selectCommunityList(UserExtend userExtend);

    List<UserExtend> getUserExtendInfoByUserId(Long userId);

    void deleteByUserIds(List<Long> userIds);

    UserExtend queryUserExtend(UserExtend userExtend);


    List<UserExtend> selectPageCondition(Page<UserExtend> page, UserExtend condition);

    void deleteStaff(List<Long> ids);

    @Override
    UserExtend selectById(Serializable id);

    void deleteByCommunity(Long communityId);

    List<UserExtend> findAll(UserExtend userExtend);

}
