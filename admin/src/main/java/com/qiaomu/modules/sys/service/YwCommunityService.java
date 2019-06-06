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
     * 增加社区成员
     * @param paramString1
     * @param paramString2
     * @param paramLong
     * @param paramString3
     * @param paramString4
     * @param paramString5
     * @param paramString6
     * @return
     */
    String addCommunityMember(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4, String paramString5, String paramString6);

    /**
     * 根据社区ID获取社区
     * @param paramLong
     * @return
     */
    YwCommunity queryById(Long paramLong);

    List<YwCommunity> findAll(YwCommunity paramYwCommunity);

    void save(YwCommunity paramYwCommunity);

    /**
     * 根据物业公司id和社区名称获取社区id
     * @param communityName
     * @param companyId
     * @return
     */
    List<Long> getCommunityIdList(String communityName, Long companyId);

    List<YwCommunity> findAllByCondition(YwCommunity condition);
}


