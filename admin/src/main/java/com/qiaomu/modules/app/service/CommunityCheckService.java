package com.qiaomu.modules.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.app.entity.CommunityCheckEntity;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-06-04 19:46
 */
public interface CommunityCheckService extends IService<CommunityCheckEntity> {
    PageUtils queryPage(Map<String, Object> params);

    String save(CommunityCheckEntity community);

    String changeCompany(Long checkCommunityId, Long companyId);
}
