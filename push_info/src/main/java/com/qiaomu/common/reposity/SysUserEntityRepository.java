package com.qiaomu.common.reposity;

import com.qiaomu.common.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-16 23:55
 */
public interface SysUserEntityRepository  extends JpaRepository<SysUserEntity,Long> {
    SysUserEntity findByUserId(Long userId);
}
