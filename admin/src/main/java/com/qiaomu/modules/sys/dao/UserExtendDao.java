package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.sys.entity.UserExtend;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-17 7:07
 */
public interface UserExtendDao extends BaseMapper<UserExtend> {
    UserExtend getUserExtend(String userPhone);
}
