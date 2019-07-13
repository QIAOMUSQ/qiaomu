package com.qiaomu.websocket.bootstrap.data;


import com.qiaomu.websocket.common.bean.InChatMessage;

/**
 * Created by MySelf on 2018/12/3.
 */
public interface InChatToDataBaseService {

    Boolean writeMessage(InChatMessage message);

}
