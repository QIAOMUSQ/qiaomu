package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.exception.RRException;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.auth.service.KafkaTemplateService;
import com.qiaomu.modules.workflow.VO.TransmissionContentVO;
import com.qiaomu.modules.workflow.dao.InvitationDao;
import com.qiaomu.modules.workflow.entity.InvitationEntity;
import com.qiaomu.modules.workflow.VO.PushMessageVO;
import com.qiaomu.modules.workflow.service.InvitationService;
import com.qiaomu.modules.workflow.service.PushRedisMessageService;
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

    @Autowired
    private KafkaTemplateService kafkaTemplateService;

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
            kafkaTemplateService.pushInvitationInfo(
                    new PushMessageVO(
                            "物业社区公告",
                            invitation.getCommunityId(),
                            invitation.getContentHtml(),
                            JSON.toJSONString(new TransmissionContentVO("社区维修",invitation.getCommunityId(),invitation.getId()))
                            ));
            //pushRedisMessageService.pushMessage(invitation.getUserId(),null,"公告信息","2","您有新的社区公告信息",invitation.getCommunityId());
        }catch (Exception e){
            e.printStackTrace();
            throw new RRException("异常", "500");
        }
    }

    @Override
    public List<InvitationEntity> selectByCommunityId(Long communityId) {
        return baseMapper.selectByCommunityId(communityId);
    }

    @Override
    public void deleteByCommunity(Long communityId) {
        baseMapper.deleteByCommunity(communityId);
    }

}
