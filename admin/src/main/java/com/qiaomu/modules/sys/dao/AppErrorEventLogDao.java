package com.qiaomu.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.sys.entity.AppErrorEventLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 李品先
 * @description:
 * @Date 2020-08-16 18:56
 */
public interface AppErrorEventLogDao  extends BaseMapper<AppErrorEventLog> {

    @Select("select * from app_error_event_log where error_log_id = #{logId}")
    List<AppErrorEventLog> selectListByLogId(@Param("logId") Long logId);
}
