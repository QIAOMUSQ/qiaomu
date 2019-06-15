package com.qiaomu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.sys.entity.YwCommunity;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-19 22:43
 */

public interface YwCommunityService extends IService<YwCommunity> {
    /**
     * 获取社区
     * @param paramMap
     * @return
     */
    PageUtils queryPage(Map<String, Object> paramMap);


    /**
     * 根据社区ID获取社区
     * @param paramLong
     * @return
     */
    YwCommunity queryById(Integer paramLong);

    List<YwCommunity> findAll(YwCommunity paramYwCommunity);

    void save(YwCommunity paramYwCommunity);

    /**
     * 根据物业公司id和社区名称获取社区id
     * @param communityName
     * @param companyId
     * @return
     */
    List<Integer> getCommunityIdList(String communityName, Integer companyId);

    List<YwCommunity> findAllByCondition(YwCommunity condition);


    /**
     * 增加社区成员
     * @param pathId
     * @param phone
     * @param communityId
     * @param realName
     * @param address
     * @param identityInfo
     * @param sex
     * @return
     */
    String addCommunityMember(String pathId, String phone, Integer communityId, String realName, String address, String identityInfo, String sex);
}


