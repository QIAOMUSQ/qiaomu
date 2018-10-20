package io.renren.modules.info_management.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.info_management.entity.AppraiseEntity;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2018-10-15 22:25
 */

public interface AppraiseDao  extends BaseMapper<AppraiseEntity> {
    List<Long> queryDetpIdList(Long parentId);
}
