package io.renren.modules.info_management.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.info_management.entity.NewsInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2018-10-15 22:32
 */
public interface NewsInfoService extends IService<NewsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
