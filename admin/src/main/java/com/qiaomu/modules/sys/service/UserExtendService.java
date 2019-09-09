package com.qiaomu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.sys.entity.UserExtend;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-03-29 17:08
 */
public interface UserExtendService extends IService<UserExtend> {

    List<UserExtend> getUserExtend(String userPhone);

    PageUtils queryPage(Map<String, Object> params);

    UserExtend getUserExtendInfo(Long id);


    void delect(Long[] userIds);

    String getUserByPhone(String phone);

    /**
     * 设置个人中心
     * @param userPhone
     * @param newPhone 新手机号码
     * @param nickName  昵称
     * @param sex   性别
     * @param imgId 图片id
     * @param communityId 社区id
     * @return
     */
    String setPersonalCenter(String userPhone,String newPhone,String nickName, String sex, Long imgId,Long communityId);

    /**
     * 保存审核信息
     *
     * @param id 用户手机号
     * @param info      通过信息
     * @param type      0:待审核 1：通过 2：不通过 3：禁用
     * @param companyRoleType  物业角色
     */
    void saveCheckInfo(String info, String type, String companyRoleType, Long id,Long userId);


    UserExtend getUserCommunity(Long userId);

    void deleteByUserIds(List<Long> userIds);

    UserExtend queryUserExtend(UserExtend userExtend);

    void deleteStaff(List<Long> longs);

    void deleteByCommunity(Long communityId);
}
