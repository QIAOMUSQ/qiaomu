package com.qiaomu.modules.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiaomu.common.exception.RRException;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.modules.auth.service.KafkaTemplateService;
import com.qiaomu.modules.sys.dao.UserExtendDao;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysFileService;
import com.qiaomu.modules.workflow.VO.ImgFile;
import com.qiaomu.modules.workflow.VO.TransmissionContentVO;
import com.qiaomu.modules.workflow.dao.InvitationDao;
import com.qiaomu.modules.workflow.entity.InvitationEntity;
import com.qiaomu.modules.workflow.VO.PushMessageVO;
import com.qiaomu.modules.workflow.service.InvitationService;
import com.qiaomu.modules.workflow.service.PushRedisMessageService;
import com.qiniu.util.Json;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
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

  /*  @Resource
    private PushRedisMessageService pushRedisMessageService;

    @Resource
    private KafkaTemplateService kafkaTemplateService;*/
    @Resource
    private UserExtendDao userExtendDao;

    @Resource
    private SysFileService sysFileService;

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
        if (StringUtils.isNotBlank((String)params.get("communityIds"))){
            String[] ids = ((String) params.get("communityIds")).split(",");
            entity.setCommunityIds(Arrays.asList(ids));
        }
        Page<InvitationEntity> page = new Query(params).getPage();// 当前页，总条

        page.setRecords(this.baseMapper.selectPageAll(page,entity));
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(InvitationEntity invitation) {
        try {
            if (invitation.getId()!=null){
                InvitationEntity oldData =  this.baseMapper.selectById(invitation.getId());
                String imgJson = oldData.getImgJson();
                List<ImgFile> oldfile = JSONObject.parseArray(oldData.getImgJson(), ImgFile.class);
                List<ImgFile> newfile = JSONObject.parseArray(invitation.getImgJson(), ImgFile.class);
                //删除服务器中不需要数据
                for (ImgFile oldEntry: oldfile) {
                    boolean isEqual = false;
                    //循环遍历出不旧数据中和新数据不相同的数据
                    for (ImgFile newEntry: newfile) {
                        if (oldEntry.getPath().equals(newEntry.getPath())){
                            isEqual = true;
                            break;
                        }
                    }
                    //当旧数据中不存在新数据，删除旧数据
                    if (!isEqual){
                        sysFileService.deleteFileByHttpUrl((String) oldEntry.getPath());
                    }
                }
                invitation.setImgJson(invitation.getImgJson().replace("\\", ""));
                invitation.setUpdateTime(new Date());
                invitation.setCreateTime(oldData.getCreateTime());
                this.baseMapper.updateById(invitation);
            }else {
                invitation.setCreateTime(new Date());
                invitation.setImgJson(invitation.getImgJson().replace("\\", ""));
                this.baseMapper.insert(invitation);
           /* kafkaTemplateService.pushInvitationInfo(
                    new PushMessageVO(
                            "物业社区公告",
                            invitation.getCommunityId(),
                            invitation.getContentHtml(),
                            JSON.toJSONString(new TransmissionContentVO("社区维修",invitation.getCommunityId(),invitation.getId()))
                            ));*/
                //pushRedisMessageService.pushMessage(invitation.getUserId(),null,"公告信息","2","您有新的社区公告信息",invitation.getCommunityId());
            }
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
