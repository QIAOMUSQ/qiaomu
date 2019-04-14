package io.renren.modules.android.controller;

import com.quark.common.base.BaseController;
import com.quark.common.dto.QuarkResult;
import io.renren.modules.android.entity.Label;
import io.renren.modules.android.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author wonly
 * Create By 2019/1/2
 */
@Api(value = "标签接口",description = "获取标签")
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;


    @ApiOperation("获取标签")
    @GetMapping
    public QuarkResult getAllLabel(){

        QuarkResult result = restProcessor(() -> {
            List<Label> labels = labelService.findAll();
            return QuarkResult.ok(labels);
        });

        return result;
    }



}
