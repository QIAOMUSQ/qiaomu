package com.qiaomu.modules.propertycompany.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.propertycompany.dao.MerchantDao;
import com.qiaomu.modules.propertycompany.entity.Merchant;
import com.qiaomu.modules.propertycompany.service.MerchantService;
import com.qiaomu.modules.workflow.entity.YwWorkflowInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 12:43
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantDao,Merchant>  implements MerchantService{

    @Override
    public PageUtils pageList(Map<String, Object> params) {
        Page<Merchant> page = new Query(params).getPage();// 当前页，总条
        Merchant info = new Merchant();
        String name = (String)params.get("name");
        if (StringUtils.isNotBlank(name)){
            info.setName(name);
        }
        page.setRecords(this.baseMapper.selectPageAll(page,info));
        return new PageUtils(page);
    }
}
