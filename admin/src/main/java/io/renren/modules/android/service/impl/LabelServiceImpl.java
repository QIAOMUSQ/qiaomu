package io.renren.modules.android.service.impl;

import com.quark.common.base.BaseServiceImpl;
import com.quark.common.dao.LabelDao;
import com.quark.common.entity.Label;
import com.quark.rest.service.LabelService;
import org.springframework.stereotype.Service;

/**
 * @Author wonly
 * Create By 2019/1/1
 */
@Service
public class LabelServiceImpl extends BaseServiceImpl<LabelDao,Label> implements LabelService{
}
