package com.qiaomu.modules.workflow.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.modules.workflow.entity.RepairsInfo;

import java.util.Map;

/**
 * @author 李品先
 * @description:维修处理
 * @Date 2019-10-06 14:51
 */
public interface RepairsInfoService extends IService<RepairsInfo> {


    /**
     * 获取维修信息
     * @param params
     *      userId 用户Id
     *      communityId 社区id
     *      repairsType 维修类型 0：电力 1：供水 2：煤气 3房屋
     *      status  状态  0：已提交 1：物业已分派人员 2：处理完成
     *      repairsId 维修人员ID
     * @return
     */
    PageUtils findRepairsPage(Map<String, Object> params);

    RepairsInfo findRepairsById(Long id);

    /**
     * 分配维修人员
     * @param userId
     * @param id
     * @return
     */
    Object apportionRepairsPerson(Long userId, Long id) throws Exception;

    /**
     *
     * @param id id
     * @param starType 星级评价
     * @param userOpinion 用户评价
     */
    void finishRepairs(Long id, String starType, String userOpinion);

    /**
     * 维修作废
     * @param id
     */
    void invalidRepairs(Long id);
}
