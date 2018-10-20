package io.renren.modules.info_management.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.info_management.service.NewsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 李品先
 * @description: 新闻消息
 * @Date 2018-10-15 22:45
 */

@RestController
@RequestMapping("/App/newsInfo")
public class NewsInfoController {

    @Autowired
    private NewsInfoService newsInfoService;


    /**
     * 根据消息类型infoType获取新闻并按时间远近排序，若infoType=""或null，获取所有新闻消息
     * @param params
     *          currPage:当前页码
     *          limit:每页条数(默认10)
     * @return
     */
    @RequestMapping("/pageList")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = newsInfoService.queryPage(params);
        return R.ok().put("page", page);
    }
}
