package io.renren.modules.android.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.android.dao.CityDao;
import io.renren.modules.android.entity.City;
import io.renren.modules.android.service.CityService;
import org.springframework.stereotype.Service;

/**
 * @author 李品先
 * @description:
 * @Date 2018-11-28 21:38
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityDao, City> implements CityService {
}
