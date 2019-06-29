/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.qiaomu.modules.sys.controller;

import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.UserExtendService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller公共组件
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {

    @Autowired
    private UserExtendService userExtendService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

    protected Long getDeptId() {
        return getUser().getDeptId();
    }

    protected Long getCompanyId(){
        return  getUser().getCompanyId();
    }

    /**
     * 根据登陆用户查询该用户物业公司或社区
     *
     * @param type 获取物业或者社区
     *             1：物业
     *             2：社区
     * @return
     */
    protected List<Long> getCompanyOrCommunityByType(String type) {
        //根据登陆用户查询该物业公司
        List<Long> data = new ArrayList<>();
        List<UserExtend> userExtend = userExtendService.getUserExtend(getUser().getUsername());

        for (UserExtend user : userExtend){
            if (type.equals("1") && userExtend != null) {
                data.add(user.getCompanyId());
            } else if (type.equals("0") && userExtend != null) {
                data.add(user.getCommunityId());
            }
        }
        return data;
    }



    /**
     * 获取用户扩展信息
     * @param userPhone
     * @return
     */
    protected List<UserExtend> getUserExtend(String userPhone){
      return  userExtendService.getUserExtend(userPhone);
    }

}
