package com.qiaomu.common.reposity;

import com.qiaomu.common.entity.UserExtend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-16 23:45
 */
public interface UserExtendRepository extends JpaRepository<UserExtend,Long> {
    List<UserExtend> findByCommunityId(Long communityId);
}
