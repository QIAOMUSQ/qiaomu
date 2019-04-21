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
    PageUtils queryPage(Map<String, Object> paramMap);

    String findAdministratorNum(YwCommunity paramYwCommunity);

    String addCommunityMember(String paramString1, String paramString2, Long paramLong, String paramString3, String paramString4, String paramString5, String paramString6);

    YwCommunity queryById(Long paramLong);

    List<YwCommunity> findAll(YwCommunity paramYwCommunity);

    void save(YwCommunity paramYwCommunity);

    List<Long> getCommunityIdList(String paramString, Long paramLong);
}


