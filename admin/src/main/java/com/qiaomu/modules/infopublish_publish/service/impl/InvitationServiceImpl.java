package com.qiaomu.modules.infopublish_publish.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.utils.Constant;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.infopublish_publish.dao.InvitationDao;
import com.qiaomu.modules.infopublish_publish.entity.InvitationEntity;
import com.qiaomu.modules.infopublish_publish.service.InvitationService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-22 23:06
 */
@Service
public class InvitationServiceImpl extends ServiceImpl<InvitationDao,InvitationEntity> implements InvitationService{
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        /**
         * 查找条件
         * 1：用户名
         * 2：信息类型
         */
        Long communityId = (Long) params.get("communityId");
        String userPhone  = (String) params.get("userPhone");
        String infoType = (String)params.get("infoType");
        Page<InvitationEntity> page = this.selectPage(
                new Query<InvitationEntity>(params).getPage(),
                new EntityWrapper<InvitationEntity>()
                        .eq("TYPE",1)
                        .eq("COMMUNITY_ID",communityId)
                        .or("TYPE",0)
                        .eq(infoType!=null,"info_type",infoType)
                        .eq(userPhone!=null,"user_phone",userPhone)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }
}
