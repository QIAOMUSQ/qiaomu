package com.qiaomu.modules.infopublish.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.exception.RRException;
import com.qiaomu.common.exception.RRExceptionHandler;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.infopublish.dao.InvitationDao;
import com.qiaomu.modules.infopublish.entity.InvitationEntity;
import com.qiaomu.modules.infopublish.service.InvitationService;
import com.qiaomu.modules.infopublish.service.PushRedisMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-22 23:06
 */
@Service
public class InvitationServiceImpl extends ServiceImpl<InvitationDao,InvitationEntity> implements InvitationService{

    @Autowired
    private PushRedisMessageService pushRedisMessageService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        InvitationEntity entity = new InvitationEntity();
        entity.setCompanyId((Long) params.get("companyId"));
        if(StringUtils.isNotBlank((String) params.get("communityId"))){
            entity.setCommunityId(Long.valueOf((String)params.get("communityId")));
        }
        if(StringUtils.isNotBlank((String) params.get("title"))){
            entity.setTitle((String) params.get("title"));
        }
        Page<InvitationEntity> page = new Query(params).getPage();// 当前页，总条
        page.setRecords(this.baseMapper.selectPageAll(page,entity));
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(InvitationEntity invitation) {
        try {
            invitation.setImgJson(invitation.getImgJson().replace("\\",""));
            invitation.setCreateTime(new Date());
            this.baseMapper.insert(invitation);
            pushRedisMessageService.pushMessage(invitation.getUserId(),null,"公告信息","2","您有新的社区公告信息",invitation.getCommunityId());
        }catch (Exception e){
            e.printStackTrace();
            throw new RRException("异常", "500");
        }
    }

    @Override
    public List<InvitationEntity> selectByCommunityId(Long communityId) {
        return null;
    }

    @Override
    public void deleteByCommunity(Long communityId) {
        baseMapper.deleteByCommunity(communityId);
    }
}
