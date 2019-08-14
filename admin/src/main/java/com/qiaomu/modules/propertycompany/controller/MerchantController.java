package com.qiaomu.modules.propertycompany.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.PageUtils;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.propertycompany.entity.Merchant;
import com.qiaomu.modules.propertycompany.service.MerchantService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 17:10
 */
@Controller
@RequestMapping("merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @ResponseBody
    @RequestMapping(value = "pageList",method = RequestMethod.POST)
    @RequiresPermissions({"merchant:list"})
    public R pageList(@RequestParam Map<String, Object> params, ServletRequest request) {
        PageUtils page = merchantService.pageList(params);
        return R.ok().put("page",page);
    }

    @RequestMapping(value = "add")
    public String add(Long id,ModelMap model){
        model.addAttribute("merchant", JSON.toJSONString(merchantService.selectById(id)));
        return "modules/merchant/add";
    }

    @ResponseBody
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @RequiresPermissions(value = {"merchant:save","merchant:update"},logical = Logical.OR)
    public R save(Merchant merchant){
        return R.ok().put("info",merchantService.save(merchant));
    }

    @ResponseBody
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @RequiresPermissions({"merchant:delete"})
    public R delete(@RequestBody Long[] ids){
        return R.ok().put("info",merchantService.deleteBatchIds(Arrays.asList(ids)));
    }

    @ResponseBody
    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    public R getAll(){
        return  R.ok().put("data",JSON.toJSON( merchantService.getAll()));
    }
}
