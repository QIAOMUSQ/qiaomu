package com.qiaomu.modules.protocol.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.Query;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.protocol.entity.ProtocolEntity;
import com.qiaomu.modules.protocol.service.ProtocolService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:App协议控制类
 * @Date 2020-03-22 11:06
 */
@RestController
public class ProtocolController {

    @Resource
    private ProtocolService protocolService;


    @RequestMapping(value = "protocol/queryPage",method = RequestMethod.POST)
    public R queryPage(@RequestParam Map<String, Object> params){
        String title = (String) params.get("title");
        EntityWrapper<ProtocolEntity> entityWrapper = new EntityWrapper<>();
        entityWrapper.like(StringUtils.isNotBlank(title), "TITLE", StringUtils.trimToEmpty(title));
        Page<ProtocolEntity>  pages = protocolService.selectPage(new Query<ProtocolEntity>(params).getPage(),entityWrapper);
        return R.ok().put("page",  new PageUtils(pages));
    }

    @RequestMapping(value = "protocol/save",method = RequestMethod.POST)
    public R save(@RequestBody ProtocolEntity protocol){
       try {
           protocol.setCreateTime(new Date());
           protocol.setType("app");
           System.out.println("protocol = " + JSON.toJSON(protocol));
           protocolService.insert(protocol);
           return R.ok();
       }catch (Exception e){
           e.printStackTrace();
           return R.error();
       }

    }

    @RequestMapping(value = "protocol/update",method = RequestMethod.POST)
    public R update(@RequestBody ProtocolEntity protocol){
        try {
            protocol.setType("app");
            protocolService.updateById(protocol);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }

    }
    /**
     * 用户信息
     */
    @RequestMapping("protocol/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ProtocolEntity protocol = protocolService.selectById(id);
        return R.ok().put("protocol", protocol);
    }

    @RequestMapping("protocol/delete")
    public R delete(@RequestBody Long[] ids) {
        protocolService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


//    @RequestMapping("mobile/protocol")
    @RequestMapping("outapp/readStaticFile")
    public Object mobileProtocol() {
        try {
            String type = "app";
            EntityWrapper<ProtocolEntity> wrapper = new EntityWrapper<>();
            wrapper.eq(StringUtils.isNotBlank(type), "TYPE", StringUtils.trimToEmpty(type))
                    .orderBy("create_time",false);
            List<ProtocolEntity> list = protocolService.selectList(wrapper);
            if (list.size()>0) {
                return BuildResponse.success(list.get(0));
            }else {
                return BuildResponse.fail("无数据");
            }
        }catch (Exception e){
            e.printStackTrace();
            return BuildResponse.fail();
        }

    }
}
