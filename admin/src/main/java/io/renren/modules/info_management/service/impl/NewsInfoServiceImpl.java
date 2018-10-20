package io.renren.modules.info_management.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.info_management.dao.NewsInfoDao;
import io.renren.modules.info_management.entity.NewsInfoEntity;
import io.renren.modules.info_management.service.NewsInfoService;
import io.renren.modules.sys.entity.SysConfigEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2018-10-15 22:33
 */
@Service("NewsInfoService")
public class NewsInfoServiceImpl  extends ServiceImpl<NewsInfoDao, NewsInfoEntity> implements NewsInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //根据消息类型查询
        String infoType = (String)params.get("infoType");
        Page<NewsInfoEntity> page;

        if(infoType==null || infoType == ""){
             page = this.selectPage(
                    new Query<NewsInfoEntity>(params).getPage(),
                    new EntityWrapper<NewsInfoEntity>().orderBy("time",false)
            );
        }else{
            page = this.selectPage(
                    new Query<NewsInfoEntity>(params).getPage(),
                    new EntityWrapper<NewsInfoEntity>().eq("info_Type", infoType).orderBy("time",false)
            );
        }

        return  new PageUtils(page);
    }


}
