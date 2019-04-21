package com.qiaomu.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.sys.entity.YwUserExtend;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-03-29 17:08
 */
public interface YwUserExtendService extends IService<YwUserExtend> {

    YwUserExtend getUserExtend(String userPhone);

    PageUtils queryPage(Map<String, Object> params);

    YwUserExtend getUserExtendInfo(Long id);

    /**
     * 保存审核信息
     * @param userPhone 用户手机号
     * @param info 通过信息
     * @param type 1：通过 2：不通过 3：禁用
     * @param roleType 物业角色
     */
    void saveCheckInfo(String userPhone, String info, String type, String roleType);

    void delect(Long[] userIds);
}
