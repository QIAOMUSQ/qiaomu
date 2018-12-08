package io.renren.modules.android.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.android.dao.CommunityDao;
import io.renren.modules.android.entity.Community;
import io.renren.modules.android.service.CommunityService;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2018-11-28 21:38
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityDao, Community> implements CommunityService {
}
