package com.qiaomu.common.reposity;

import com.qiaomu.common.entity.PushMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2019-07-10 15:00
 */
public interface PushMessageRepository extends JpaRepository<PushMessage,Long> {

    List<PushMessage> findByReceivePhoneAndStatus(String receivePhone, boolean status);
}
