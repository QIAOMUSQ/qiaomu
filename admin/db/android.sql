/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : android

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-12-21 00:20:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', null, 'ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158BAF593307874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000017400047465737474000672656E72656E74000FE69C89E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', null, 'ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158C377C4607874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000574657374327074000FE697A0E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'lipx1545322115239', '1545322171118', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', null, '1545323400000', '-1', '5', 'WAITING', 'CRON', '1537967361000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158BAF593307874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000017400047465737474000672656E72656E74000FE69C89E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', 'TASK_2', 'DEFAULT', null, '1537968600000', '-1', '5', 'PAUSED', 'CRON', '1537967361000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158C377C4607874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000574657374327074000FE697A0E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('1', 'testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO `schedule_job` VALUES ('2', 'testTask', 'test2', null, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES ('1', '1', 'testTask', 'test', 'renren', '0', null, '1042', '2018-09-26 21:12:26');
INSERT INTO `schedule_job_log` VALUES ('2', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2018-09-26 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('3', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-09-26 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('4', '1', 'testTask', 'test', 'renren', '0', null, '1285', '2018-10-14 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('5', '1', 'testTask', 'test', 'renren', '0', null, '1022', '2018-10-14 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('6', '1', 'testTask', 'test', 'renren', '0', null, '1012', '2018-10-14 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('7', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-14 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('8', '1', 'testTask', 'test', 'renren', '0', null, '1006', '2018-10-14 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('9', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-14 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('10', '1', 'testTask', 'test', 'renren', '0', null, '1022', '2018-10-14 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('11', '1', 'testTask', 'test', 'renren', '0', null, '1010', '2018-10-14 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('12', '1', 'testTask', 'test', 'renren', '0', null, '1093', '2018-10-15 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('13', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2018-10-15 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('14', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2018-10-15 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('15', '1', 'testTask', 'test', 'renren', '0', null, '1226', '2018-10-15 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('16', '1', 'testTask', 'test', 'renren', '0', null, '1102', '2018-10-16 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('17', '1', 'testTask', 'test', 'renren', '0', null, '1181', '2018-10-16 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('18', '1', 'testTask', 'test', 'renren', '0', null, '1119', '2018-10-21 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('19', '1', 'testTask', 'test', 'renren', '0', null, '1034', '2018-10-21 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('20', '1', 'testTask', 'test', 'renren', '0', null, '1054', '2018-10-22 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('21', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2018-10-22 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('22', '1', 'testTask', 'test', 'renren', '0', null, '1008', '2018-10-22 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('23', '1', 'testTask', 'test', 'renren', '0', null, '1006', '2018-10-22 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('24', '1', 'testTask', 'test', 'renren', '0', null, '1008', '2018-10-22 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('25', '1', 'testTask', 'test', 'renren', '0', null, '1909', '2018-10-23 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('26', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2018-10-23 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('27', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2018-10-23 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('28', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2018-10-23 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('29', '1', 'testTask', 'test', 'renren', '0', null, '1024', '2018-10-23 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('30', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-23 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('31', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2018-10-23 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('32', '1', 'testTask', 'test', 'renren', '0', null, '1027', '2018-10-23 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('33', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2018-10-23 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('34', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2018-10-23 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('35', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('36', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-23 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('37', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-23 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('38', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2018-10-23 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('39', '1', 'testTask', 'test', 'renren', '0', null, '1108', '2018-10-23 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('40', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-23 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('41', '1', 'testTask', 'test', 'renren', '0', null, '1008', '2018-10-23 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('42', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2018-10-23 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('43', '1', 'testTask', 'test', 'renren', '0', null, '1043', '2018-10-23 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('44', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2018-10-23 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('45', '1', 'testTask', 'test', 'renren', '0', null, '1088', '2018-10-23 20:30:01');
INSERT INTO `schedule_job_log` VALUES ('46', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2018-10-23 21:00:01');
INSERT INTO `schedule_job_log` VALUES ('47', '1', 'testTask', 'test', 'renren', '0', null, '1074', '2018-10-24 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('48', '1', 'testTask', 'test', 'renren', '0', null, '1016', '2018-10-24 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('49', '1', 'testTask', 'test', 'renren', '0', null, '1066', '2018-10-24 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('50', '1', 'testTask', 'test', 'renren', '0', null, '1071', '2018-10-24 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('51', '1', 'testTask', 'test', 'renren', '0', null, '1006', '2018-10-24 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('52', '1', 'testTask', 'test', 'renren', '0', null, '1064', '2018-10-30 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('53', '1', 'testTask', 'test', 'renren', '0', null, '1019', '2018-10-30 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('54', '1', 'testTask', 'test', 'renren', '0', null, '1015', '2018-10-30 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('55', '1', 'testTask', 'test', 'renren', '0', null, '1020', '2018-10-30 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('56', '1', 'testTask', 'test', 'renren', '0', null, '1019', '2018-10-30 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('57', '1', 'testTask', 'test', 'renren', '0', null, '1010', '2018-10-30 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('58', '1', 'testTask', 'test', 'renren', '0', null, '1018', '2018-10-30 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('59', '1', 'testTask', 'test', 'renren', '0', null, '1007', '2018-10-30 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('60', '1', 'testTask', 'test', 'renren', '0', null, '1036', '2018-10-30 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('61', '1', 'testTask', 'test', 'renren', '0', null, '1035', '2018-10-31 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('62', '1', 'testTask', 'test', 'renren', '0', null, '1395', '2018-11-01 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('63', '1', 'testTask', 'test', 'renren', '0', null, '1101', '2018-11-01 22:30:00');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '超级管理员', '0', '-1');
INSERT INTO `sys_dept` VALUES ('2', '0', '乔木社区', '1', '0');
INSERT INTO `sys_dept` VALUES ('3', '1', '管理员', '2', '-1');
INSERT INTO `sys_dept` VALUES ('4', '3', '普通用户', '0', '-1');
INSERT INTO `sys_dept` VALUES ('5', '3', '一级用户', '1', '-1');
INSERT INTO `sys_dept` VALUES ('6', '0', '东冠社区', '2', '0');
INSERT INTO `sys_dept` VALUES ('7', '0', '信息管理员', '1', '-1');
INSERT INTO `sys_dept` VALUES ('8', '0', '浦沿社区', '3', '0');
INSERT INTO `sys_dept` VALUES ('9', '0', '长河社区', '0', '0');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '性别', 'sex', '0', '女', '0', null, '0');
INSERT INTO `sys_dict` VALUES ('2', '性别', 'sex', '1', '男', '1', null, '0');
INSERT INTO `sys_dict` VALUES ('3', '性别', 'sex', '2', '未知', '3', null, '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '立即执行任务', 'ScheduleJobController.run()', '[1]', '186', '0:0:0:0:0:0:0:1', '2018-09-26 21:12:26');
INSERT INTO `sys_log` VALUES ('2', 'admin', '保存用户', 'SysUserController.save()', '{\"userId\":2,\"username\":\"123456\",\"password\":\"8adc2b2d4aa95a5722e563a39e86f6a2c5fc998b568c76ed1fb538f7060e03bb\",\"salt\":\"LGwRVAqDebIGWyLxgCJS\",\"email\":\"1249254045@qq.com\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Sep 26, 2018 10:04:46 PM\",\"deptId\":4,\"deptName\":\"普通用户\"}', '222', '0:0:0:0:0:0:0:1', '2018-09-26 22:04:47');
INSERT INTO `sys_log` VALUES ('3', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":1,\"roleName\":\"超级管理员\",\"remark\":\"所有权限\",\"deptId\":2,\"deptName\":\"超级管理员\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,29,30,31,32,33,34,35,36,37,38,39,40],\"deptIdList\":[1,2,3,4,5],\"createTime\":\"Sep 26, 2018 10:05:56 PM\"}', '682', '0:0:0:0:0:0:0:1', '2018-09-26 22:05:57');
INSERT INTO `sys_log` VALUES ('4', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":2,\"roleName\":\"管理员\",\"deptId\":3,\"deptName\":\"管理员\",\"menuIdList\":[],\"deptIdList\":[3,4,5],\"createTime\":\"Sep 26, 2018 10:06:46 PM\"}', '181', '0:0:0:0:0:0:0:1', '2018-09-26 22:06:47');
INSERT INTO `sys_log` VALUES ('5', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":3,\"roleName\":\"普通用户\",\"remark\":\"普通用户\",\"deptId\":4,\"deptName\":\"普通用户\",\"menuIdList\":[],\"deptIdList\":[4],\"createTime\":\"Sep 26, 2018 10:07:11 PM\"}', '47', '0:0:0:0:0:0:0:1', '2018-09-26 22:07:12');
INSERT INTO `sys_log` VALUES ('6', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":4,\"roleName\":\"一级用户\",\"remark\":\"一级用户\",\"deptId\":5,\"deptName\":\"一级用户\",\"menuIdList\":[],\"deptIdList\":[4,5],\"createTime\":\"Sep 26, 2018 10:07:45 PM\"}', '361', '0:0:0:0:0:0:0:1', '2018-09-26 22:07:46');
INSERT INTO `sys_log` VALUES ('7', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"新闻信息发布管理\",\"type\":0,\"orderNum\":1}', '31', '0:0:0:0:0:0:0:1', '2018-10-14 17:03:23');
INSERT INTO `sys_log` VALUES ('8', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"新闻信息发布管理\",\"name\":\"政策公告\",\"url\":\"modules/info_management/infoManagement.html\",\"type\":1,\"icon\":\"fa fa-newspaper-o\",\"orderNum\":0}', '36', '0:0:0:0:0:0:0:1', '2018-10-14 17:30:37');
INSERT INTO `sys_log` VALUES ('9', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":43,\"parentId\":41,\"parentName\":\"新闻信息发布管理\",\"name\":\"家事讨论\",\"url\":\"modules/info_management/housekeeping.html\",\"type\":1,\"icon\":\"fa fa-users\",\"orderNum\":0}', '27', '0:0:0:0:0:0:0:1', '2018-10-14 17:48:00');
INSERT INTO `sys_log` VALUES ('10', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":44,\"parentId\":41,\"parentName\":\"新闻信息发布管理\",\"name\":\"七彩生活管理\",\"url\":\"modules/info_management/dailyLife\",\"type\":1,\"icon\":\"fa fa-leaf\",\"orderNum\":0}', '31', '0:0:0:0:0:0:0:1', '2018-10-14 17:50:41');
INSERT INTO `sys_log` VALUES ('11', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":4,\"roleName\":\"一级用户\",\"remark\":\"一级用户\",\"deptId\":5,\"deptName\":\"一级用户\",\"menuIdList\":[1,2,15,16,17,18],\"deptIdList\":[4,5],\"createTime\":\"Sep 26, 2018 10:07:46 PM\"}', '280', '0:0:0:0:0:0:0:1', '2018-10-16 20:48:19');
INSERT INTO `sys_log` VALUES ('12', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":3,\"roleName\":\"普通用户\",\"remark\":\"普通用户\",\"deptId\":4,\"deptName\":\"普通用户\",\"menuIdList\":[1,2,17],\"deptIdList\":[4],\"createTime\":\"Sep 26, 2018 10:07:12 PM\"}', '42', '0:0:0:0:0:0:0:1', '2018-10-16 20:48:43');
INSERT INTO `sys_log` VALUES ('13', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":3,\"roleName\":\"普通用户\",\"remark\":\"普通用户\",\"deptId\":4,\"deptName\":\"普通用户\",\"menuIdList\":[1,2,17],\"deptIdList\":[4],\"createTime\":\"Sep 26, 2018 10:07:12 PM\"}', '405', '0:0:0:0:0:0:0:1', '2018-10-16 20:50:03');
INSERT INTO `sys_log` VALUES ('14', 'admin', '修改用户', 'SysUserController.update()', '{\"userId\":2,\"username\":\"123456\",\"salt\":\"LGwRVAqDebIGWyLxgCJS\",\"email\":\"1249254045@qq.com\",\"status\":1,\"roleIdList\":[3],\"createTime\":\"Sep 26, 2018 10:04:47 PM\",\"deptId\":4,\"deptName\":\"普通用户\"}', '190', '0:0:0:0:0:0:0:1', '2018-10-16 20:51:18');
INSERT INTO `sys_log` VALUES ('15', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":3,\"roleName\":\"普通用户\",\"remark\":\"普通用户\",\"deptId\":4,\"deptName\":\"普通用户\",\"menuIdList\":[1,2,15,17],\"deptIdList\":[4],\"createTime\":\"Sep 26, 2018 10:07:12 PM\"}', '132', '0:0:0:0:0:0:0:1', '2018-10-16 20:52:38');
INSERT INTO `sys_log` VALUES ('16', 'admin', '删除角色', 'SysRoleController.delete()', '[4,3]', '1861', '0:0:0:0:0:0:0:1', '2018-10-23 10:32:17');
INSERT INTO `sys_log` VALUES ('17', 'admin', '删除用户', 'SysUserController.delete()', '[2]', '51', '0:0:0:0:0:0:0:1', '2018-10-23 10:32:26');
INSERT INTO `sys_log` VALUES ('18', 'admin', '删除角色', 'SysRoleController.delete()', '[2]', '114', '0:0:0:0:0:0:0:1', '2018-10-23 10:34:13');
INSERT INTO `sys_log` VALUES ('19', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"信息发布管理\",\"type\":0,\"orderNum\":1}', '300', '0:0:0:0:0:0:0:1', '2018-10-23 10:53:55');
INSERT INTO `sys_log` VALUES ('20', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":45,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"闲置分享\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '38', '0:0:0:0:0:0:0:1', '2018-10-23 11:01:28');
INSERT INTO `sys_log` VALUES ('21', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":44,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"七彩生活\",\"url\":\"modules/info_management/dailyLife\",\"type\":1,\"icon\":\"fa fa-leaf\",\"orderNum\":0}', '25', '0:0:0:0:0:0:0:1', '2018-10-23 11:01:46');
INSERT INTO `sys_log` VALUES ('22', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":45,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"闲置分享\",\"url\":\"modules/info_management/\",\"type\":1,\"icon\":\"fa fa-users\",\"orderNum\":0}', '40', '0:0:0:0:0:0:0:1', '2018-10-23 11:02:53');
INSERT INTO `sys_log` VALUES ('23', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"政策公告\",\"url\":\"modules/info_management/infoManagement.html\",\"type\":0,\"icon\":\"fa fa-newspaper-o\",\"orderNum\":0}', '29', '0:0:0:0:0:0:0:1', '2018-10-23 11:06:39');
INSERT INTO `sys_log` VALUES ('24', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":43,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"家事讨论\",\"url\":\"modules/info_management/housekeeping.html\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '26', '0:0:0:0:0:0:0:1', '2018-10-23 11:06:47');
INSERT INTO `sys_log` VALUES ('25', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":44,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"七彩生活\",\"url\":\"modules/info_management/dailyLife\",\"type\":0,\"icon\":\"fa fa-leaf\",\"orderNum\":0}', '32', '0:0:0:0:0:0:0:1', '2018-10-23 11:06:54');
INSERT INTO `sys_log` VALUES ('26', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":45,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"闲置分享\",\"url\":\"modules/info_management/\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '124', '0:0:0:0:0:0:0:1', '2018-10-23 11:07:04');
INSERT INTO `sys_log` VALUES ('27', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":46,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"兴趣交流\",\"type\":0,\"icon\":\"fa-users\",\"orderNum\":0}', '42', '0:0:0:0:0:0:0:1', '2018-10-23 11:08:59');
INSERT INTO `sys_log` VALUES ('28', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":46,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"兴趣交流\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '59', '0:0:0:0:0:0:0:1', '2018-10-23 11:09:29');
INSERT INTO `sys_log` VALUES ('29', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":46,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"兴趣交流\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '25', '0:0:0:0:0:0:0:1', '2018-10-23 11:09:31');
INSERT INTO `sys_log` VALUES ('30', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":46,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"兴趣交流\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '30', '0:0:0:0:0:0:0:1', '2018-10-23 11:09:32');
INSERT INTO `sys_log` VALUES ('31', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":47,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"理财交流\",\"type\":0,\"icon\":\"fa fa-credit-card-alt\",\"orderNum\":0}', '70', '0:0:0:0:0:0:0:1', '2018-10-23 11:12:38');
INSERT INTO `sys_log` VALUES ('32', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":47,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"理财交流\",\"type\":0,\"icon\":\"fa fa-credit-card-alt\",\"orderNum\":0}', '32', '0:0:0:0:0:0:0:1', '2018-10-23 11:13:02');
INSERT INTO `sys_log` VALUES ('33', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":45,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"闲置分享\",\"url\":\"\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '27', '0:0:0:0:0:0:0:1', '2018-10-23 11:13:19');
INSERT INTO `sys_log` VALUES ('34', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":45,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"闲置分享\",\"url\":\"\",\"type\":0,\"icon\":\"fa fa-users\",\"orderNum\":0}', '108', '0:0:0:0:0:0:0:1', '2018-10-23 11:13:31');
INSERT INTO `sys_log` VALUES ('35', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"政策公告\",\"url\":\"\",\"type\":0,\"icon\":\"fa fa-newspaper-o\",\"orderNum\":0}', '31', '0:0:0:0:0:0:0:1', '2018-10-23 11:15:29');
INSERT INTO `sys_log` VALUES ('36', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":5,\"roleName\":\"公告管理员\",\"remark\":\"发布公告\",\"deptId\":2,\"deptName\":\"公告管理员\",\"menuIdList\":[41,42],\"deptIdList\":[2],\"createTime\":\"Oct 23, 2018 11:19:26 AM\"}', '84', '0:0:0:0:0:0:0:1', '2018-10-23 11:19:27');
INSERT INTO `sys_log` VALUES ('37', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"超级管理员\",\"remark\":\"所有权限\",\"deptId\":2,\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47],\"deptIdList\":[1,2,6,8],\"createTime\":\"Sep 26, 2018 10:05:56 PM\"}', '60', '0:0:0:0:0:0:0:1', '2018-10-23 11:19:53');
INSERT INTO `sys_log` VALUES ('38', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":6,\"roleName\":\"信息管理员\",\"remark\":\"家事讨论，七彩生活，闲置分享，兴趣交流\",\"deptId\":6,\"deptName\":\"信息管理员\",\"menuIdList\":[41,43,44,45,46],\"deptIdList\":[6],\"createTime\":\"Oct 23, 2018 11:26:26 AM\"}', '132', '0:0:0:0:0:0:0:1', '2018-10-23 11:26:27');
INSERT INTO `sys_log` VALUES ('39', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":5,\"roleName\":\"公告管理员\",\"remark\":\"发布公告，商户管理\",\"deptId\":2,\"deptName\":\"公告管理员\",\"menuIdList\":[41,42,43,44,45,46],\"deptIdList\":[2,8],\"createTime\":\"Oct 23, 2018 11:19:27 AM\"}', '126', '0:0:0:0:0:0:0:1', '2018-10-23 11:27:07');
INSERT INTO `sys_log` VALUES ('40', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":7,\"roleName\":\"理财管理员\",\"deptId\":8,\"deptName\":\"理财管理员\",\"menuIdList\":[41,47],\"deptIdList\":[8],\"createTime\":\"Oct 23, 2018 11:28:12 AM\"}', '150', '0:0:0:0:0:0:0:1', '2018-10-23 11:28:13');
INSERT INTO `sys_log` VALUES ('41', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"政策公告\",\"url\":\"modules/App\",\"type\":1,\"icon\":\"fa fa-newspaper-o\",\"orderNum\":0}', '21', '0:0:0:0:0:0:0:1', '2018-10-23 11:35:52');
INSERT INTO `sys_log` VALUES ('42', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":43,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"家事讨论\",\"url\":\"modules/App\",\"type\":1,\"icon\":\"fa fa-users\",\"orderNum\":0}', '23', '0:0:0:0:0:0:0:1', '2018-10-23 11:36:05');
INSERT INTO `sys_log` VALUES ('43', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":44,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"七彩生活\",\"url\":\"modules/App\",\"type\":1,\"icon\":\"fa fa-leaf\",\"orderNum\":0}', '23', '0:0:0:0:0:0:0:1', '2018-10-23 11:36:14');
INSERT INTO `sys_log` VALUES ('44', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":45,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"闲置分享\",\"url\":\"modules/App\",\"type\":1,\"icon\":\"fa fa-users\",\"orderNum\":0}', '33', '0:0:0:0:0:0:0:1', '2018-10-23 11:36:25');
INSERT INTO `sys_log` VALUES ('45', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":46,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"兴趣交流\",\"url\":\"modules/App\",\"type\":1,\"icon\":\"fa fa-users\",\"orderNum\":0}', '26', '0:0:0:0:0:0:0:1', '2018-10-23 11:36:39');
INSERT INTO `sys_log` VALUES ('46', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":47,\"parentId\":41,\"parentName\":\"信息发布管理\",\"name\":\"理财交流\",\"url\":\"modules/App\",\"type\":1,\"icon\":\"fa fa-credit-card-alt\",\"orderNum\":0}', '32', '0:0:0:0:0:0:0:1', '2018-10-23 11:36:54');
INSERT INTO `sys_log` VALUES ('47', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":48,\"parentId\":42,\"parentName\":\"政策公告\",\"name\":\"新增\",\"type\":2,\"orderNum\":0}', '117', '0:0:0:0:0:0:0:1', '2018-10-23 13:58:59');
INSERT INTO `sys_log` VALUES ('48', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":48,\"parentId\":42,\"parentName\":\"政策公告\",\"name\":\"新增\",\"perms\":\"\",\"type\":2,\"orderNum\":0}', '51', '0:0:0:0:0:0:0:1', '2018-10-23 15:58:11');
INSERT INTO `sys_log` VALUES ('49', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":49,\"parentId\":42,\"parentName\":\"政策公告\",\"name\":\"查看\",\"type\":2,\"orderNum\":0}', '52', '0:0:0:0:0:0:0:1', '2018-10-23 15:58:58');
INSERT INTO `sys_log` VALUES ('50', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":50,\"parentId\":42,\"parentName\":\"政策公告\",\"name\":\"修改\",\"type\":2,\"orderNum\":0}', '76', '0:0:0:0:0:0:0:1', '2018-10-23 16:00:52');
INSERT INTO `sys_log` VALUES ('51', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":51,\"parentId\":42,\"parentName\":\"政策公告\",\"name\":\"删除\",\"type\":2,\"orderNum\":0}', '25', '0:0:0:0:0:0:0:1', '2018-10-23 16:01:26');
INSERT INTO `sys_log` VALUES ('52', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":52,\"parentId\":43,\"parentName\":\"家事讨论\",\"name\":\"查看\",\"type\":2,\"orderNum\":0}', '116', '0:0:0:0:0:0:0:1', '2018-10-23 16:02:15');
INSERT INTO `sys_log` VALUES ('53', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":53,\"parentId\":43,\"parentName\":\"家事讨论\",\"name\":\"新增\",\"type\":2,\"orderNum\":0}', '99', '0:0:0:0:0:0:0:1', '2018-10-23 16:02:30');
INSERT INTO `sys_log` VALUES ('54', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":54,\"parentId\":43,\"parentName\":\"家事讨论\",\"name\":\"修改\",\"type\":2,\"orderNum\":0}', '41', '0:0:0:0:0:0:0:1', '2018-10-23 16:02:46');
INSERT INTO `sys_log` VALUES ('55', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":55,\"parentId\":43,\"parentName\":\"家事讨论\",\"name\":\"删除\",\"type\":2,\"orderNum\":0}', '32', '0:0:0:0:0:0:0:1', '2018-10-23 16:03:18');
INSERT INTO `sys_log` VALUES ('56', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":56,\"parentId\":44,\"parentName\":\"七彩生活\",\"name\":\"新增\",\"type\":2,\"orderNum\":0}', '22', '0:0:0:0:0:0:0:1', '2018-10-23 16:08:02');
INSERT INTO `sys_log` VALUES ('57', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":57,\"parentId\":44,\"parentName\":\"七彩生活\",\"name\":\"查看\",\"perms\":\"\",\"type\":2,\"orderNum\":0}', '45', '0:0:0:0:0:0:0:1', '2018-10-23 16:51:49');
INSERT INTO `sys_log` VALUES ('58', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":58,\"parentId\":44,\"parentName\":\"七彩生活\",\"name\":\"修改\",\"type\":2,\"orderNum\":0}', '26', '0:0:0:0:0:0:0:1', '2018-10-23 16:52:21');
INSERT INTO `sys_log` VALUES ('59', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":59,\"parentId\":44,\"parentName\":\"七彩生活\",\"name\":\"删除\",\"type\":2,\"orderNum\":0}', '108', '0:0:0:0:0:0:0:1', '2018-10-23 16:52:50');
INSERT INTO `sys_log` VALUES ('60', 'admin', '删除菜单', 'SysMenuController.delete()', '50', '153', '0:0:0:0:0:0:0:1', '2018-10-23 16:52:59');
INSERT INTO `sys_log` VALUES ('61', 'admin', '删除菜单', 'SysMenuController.delete()', '54', '287', '0:0:0:0:0:0:0:1', '2018-10-23 16:53:10');
INSERT INTO `sys_log` VALUES ('62', 'admin', '删除菜单', 'SysMenuController.delete()', '58', '37', '0:0:0:0:0:0:0:1', '2018-10-23 16:53:20');
INSERT INTO `sys_log` VALUES ('63', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":60,\"parentId\":45,\"parentName\":\"闲置分享\",\"name\":\"新增\",\"type\":2,\"orderNum\":0}', '114', '0:0:0:0:0:0:0:1', '2018-10-23 17:03:03');
INSERT INTO `sys_log` VALUES ('64', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":61,\"parentId\":45,\"parentName\":\"闲置分享\",\"name\":\"查看\",\"type\":2,\"orderNum\":0}', '132', '0:0:0:0:0:0:0:1', '2018-10-23 17:03:17');
INSERT INTO `sys_log` VALUES ('65', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":62,\"parentId\":45,\"parentName\":\"闲置分享\",\"name\":\"删除\",\"type\":2,\"orderNum\":0}', '22', '0:0:0:0:0:0:0:1', '2018-10-23 17:03:36');
INSERT INTO `sys_log` VALUES ('66', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":63,\"parentId\":46,\"parentName\":\"兴趣交流\",\"name\":\"查看\",\"type\":2,\"orderNum\":0}', '45', '0:0:0:0:0:0:0:1', '2018-10-23 17:03:54');
INSERT INTO `sys_log` VALUES ('67', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":64,\"parentId\":46,\"parentName\":\"兴趣交流\",\"name\":\"新增\",\"type\":2,\"orderNum\":0}', '20', '0:0:0:0:0:0:0:1', '2018-10-23 17:04:08');
INSERT INTO `sys_log` VALUES ('68', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":65,\"parentId\":46,\"parentName\":\"兴趣交流\",\"name\":\"删除\",\"type\":2,\"orderNum\":0}', '25', '0:0:0:0:0:0:0:1', '2018-10-23 17:04:28');
INSERT INTO `sys_log` VALUES ('69', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":66,\"parentId\":47,\"parentName\":\"理财交流\",\"name\":\"查看\",\"perms\":\"\",\"type\":2,\"orderNum\":0}', '29', '0:0:0:0:0:0:0:1', '2018-10-23 17:05:08');
INSERT INTO `sys_log` VALUES ('70', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":67,\"parentId\":47,\"parentName\":\"理财交流\",\"name\":\"新增\",\"type\":2,\"orderNum\":0}', '28', '0:0:0:0:0:0:0:1', '2018-10-23 17:05:24');
INSERT INTO `sys_log` VALUES ('71', 'admin', '保存菜单', 'SysMenuController.save()', '{\"menuId\":68,\"parentId\":47,\"parentName\":\"理财交流\",\"name\":\"删除\",\"type\":2,\"orderNum\":0}', '21', '0:0:0:0:0:0:0:1', '2018-10-23 17:05:36');
INSERT INTO `sys_log` VALUES ('72', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":5,\"roleName\":\"公告管理员\",\"remark\":\"发布公告，商户管理\",\"deptId\":2,\"deptName\":\"公告管理员\",\"menuIdList\":[41,42,48,49,51,43,44,45,46],\"deptIdList\":[2],\"createTime\":\"Oct 23, 2018 11:19:27 AM\"}', '147', '0:0:0:0:0:0:0:1', '2018-10-23 19:09:04');
INSERT INTO `sys_log` VALUES ('73', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":6,\"roleName\":\"信息管理员\",\"remark\":\"家事讨论，七彩生活，闲置分享，兴趣交流\",\"deptId\":6,\"deptName\":\"信息管理员\",\"menuIdList\":[41,43,52,53,55,44,56,57,59,45,60,61,62,46,63,64,65],\"deptIdList\":[6],\"createTime\":\"Oct 23, 2018 11:26:27 AM\"}', '430', '0:0:0:0:0:0:0:1', '2018-10-23 19:09:32');
INSERT INTO `sys_log` VALUES ('74', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":7,\"roleName\":\"理财管理员\",\"deptId\":8,\"deptName\":\"理财管理员\",\"menuIdList\":[41,47,66,67,68],\"deptIdList\":[8],\"createTime\":\"Oct 23, 2018 11:28:13 AM\"}', '139', '0:0:0:0:0:0:0:1', '2018-10-23 19:09:51');
INSERT INTO `sys_log` VALUES ('75', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":8,\"roleName\":\"普通用户\",\"deptId\":1,\"deptName\":\"超级管理员\",\"menuIdList\":[41,42,48,49,43,52,53,44,56,57,59,45,60,61,62,46,63,64,65,47,68],\"deptIdList\":[9],\"createTime\":\"Oct 23, 2018 7:10:56 PM\"}', '204', '0:0:0:0:0:0:0:1', '2018-10-23 19:10:56');
INSERT INTO `sys_log` VALUES ('76', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":8,\"roleName\":\"普通用户\",\"deptId\":1,\"deptName\":\"超级管理员\",\"menuIdList\":[41,42,49,51,43,52,53,55,44,56,57,59,45,60,61,62,46,63,64,65,47,66],\"deptIdList\":[9],\"createTime\":\"Oct 23, 2018 7:10:56 PM\"}', '134', '0:0:0:0:0:0:0:1', '2018-10-23 19:11:36');
INSERT INTO `sys_log` VALUES ('77', 'admin', '保存用户', 'SysUserController.save()', '{\"userId\":4,\"username\":\"15157150200\",\"password\":\"7d4efd38b2f9231fb8f01307eecb64537941c97621069a971b23121e3c807425\",\"salt\":\"EtXDA5bQDttTk92XvCh8\",\"email\":\"124926@qq.com\",\"status\":1,\"roleIdList\":[8],\"createTime\":\"Oct 23, 2018 7:13:38 PM\",\"deptId\":9,\"deptName\":\"普通用户\"}', '1347', '0:0:0:0:0:0:0:1', '2018-10-23 19:13:40');
INSERT INTO `sys_log` VALUES ('78', 'admin', '保存用户', 'SysUserController.save()', '{\"userId\":5,\"username\":\"eee\",\"password\":\"11c467414483b9a8c3c450300eb152414c0ce2fb53becc957a526e953e789115\",\"salt\":\"f7WKSuSKyRMVdHLYPJWd\",\"mobile\":\"ee\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Oct 23, 2018 7:56:20 PM\",\"deptId\":9,\"deptName\":\"普通用户\"}', '110', '0:0:0:0:0:0:0:1', '2018-10-23 19:56:21');
INSERT INTO `sys_log` VALUES ('79', 'admin', '删除用户', 'SysUserController.delete()', '[5]', '39', '0:0:0:0:0:0:0:1', '2018-10-23 19:56:33');
INSERT INTO `sys_log` VALUES ('80', 'admin', '保存用户', 'SysUserController.save()', '{\"userId\":6,\"username\":\"222\",\"password\":\"64e77a9e51ca57027a0f7e368b5554742855caa62e2f97c8795ac99e5624b62b\",\"salt\":\"FUS6G6ht4YXbHxyNq7fY\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Oct 23, 2018 7:56:47 PM\",\"deptId\":9,\"deptName\":\"普通用户\"}', '42', '0:0:0:0:0:0:0:1', '2018-10-23 19:56:48');
INSERT INTO `sys_log` VALUES ('81', 'admin', '修改用户', 'SysUserController.update()', '{\"userId\":4,\"username\":\"15157150200\",\"password\":\"74a9efd55ecb5bf444737ea8b2d6f56576dd975c71fbf1346864e711f98f480c\",\"salt\":\"EtXDA5bQDttTk92XvCh8\",\"email\":\"124926@qq.com\",\"status\":1,\"roleIdList\":[8],\"createTime\":\"Oct 23, 2018 7:13:38 PM\",\"deptId\":9,\"deptName\":\"普通用户\"}', '506', '0:0:0:0:0:0:0:1', '2018-11-27 22:56:55');
INSERT INTO `sys_log` VALUES ('82', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":31,\"parentId\":1,\"parentName\":\"系统管理\",\"name\":\"社区管理\",\"url\":\"modules/sys/dept.html\",\"type\":1,\"icon\":\"fa fa-file-code-o\",\"orderNum\":1}', '119', '0:0:0:0:0:0:0:1', '2018-12-02 22:07:40');
INSERT INTO `sys_log` VALUES ('83', 'admin', '修改菜单', 'SysMenuController.update()', '{\"menuId\":31,\"parentId\":1,\"parentName\":\"系统管理\",\"name\":\"社区管理\",\"url\":\"modules/sys/dept.html\",\"type\":1,\"icon\":\"fa fa-file-code-o\",\"orderNum\":1}', '36', '0:0:0:0:0:0:0:1', '2018-12-02 22:08:20');
INSERT INTO `sys_log` VALUES ('84', 'admin', '删除角色', 'SysRoleController.delete()', '[5,6,7,8]', '185', '0:0:0:0:0:0:0:1', '2018-12-02 22:24:53');
INSERT INTO `sys_log` VALUES ('85', 'admin', '删除用户', 'SysUserController.delete()', '[6]', '198', '0:0:0:0:0:0:0:1', '2018-12-04 00:00:50');
INSERT INTO `sys_log` VALUES ('86', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":9,\"roleName\":\"社区管理员\",\"remark\":\"社区管理员\",\"deptId\":2,\"deptName\":\"乔木社区\",\"menuIdList\":[],\"deptIdList\":[],\"createTime\":\"Dec 8, 2018 3:28:23 PM\"}', '414', '0:0:0:0:0:0:0:1', '2018-12-08 15:28:24');
INSERT INTO `sys_log` VALUES ('87', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":10,\"roleName\":\"社区管理员\",\"deptId\":6,\"deptName\":\"东冠社区\",\"menuIdList\":[],\"deptIdList\":[],\"createTime\":\"Dec 8, 2018 3:29:15 PM\"}', '183', '0:0:0:0:0:0:0:1', '2018-12-08 15:29:15');
INSERT INTO `sys_log` VALUES ('88', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":11,\"roleName\":\"社区管理员\",\"deptId\":8,\"deptName\":\"浦沿社区\",\"menuIdList\":[],\"deptIdList\":[],\"createTime\":\"Dec 8, 2018 3:29:35 PM\"}', '795', '0:0:0:0:0:0:0:1', '2018-12-08 15:29:37');
INSERT INTO `sys_log` VALUES ('89', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":12,\"roleName\":\"社区管理员\",\"deptId\":9,\"deptName\":\"长河社区\",\"menuIdList\":[],\"deptIdList\":[],\"createTime\":\"Dec 8, 2018 3:29:48 PM\"}', '682', '0:0:0:0:0:0:0:1', '2018-12-08 15:29:49');
INSERT INTO `sys_log` VALUES ('90', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":13,\"roleName\":\"用户\",\"remark\":\"用户\",\"deptId\":2,\"deptName\":\"乔木社区\",\"menuIdList\":[],\"deptIdList\":[2],\"createTime\":\"Dec 9, 2018 9:38:38 PM\"}', '591', '0:0:0:0:0:0:0:1', '2018-12-09 21:38:39');
INSERT INTO `sys_log` VALUES ('91', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":14,\"roleName\":\"用户\",\"remark\":\"用户\",\"deptId\":6,\"deptName\":\"东冠社区\",\"menuIdList\":[],\"deptIdList\":[],\"createTime\":\"Dec 9, 2018 9:39:03 PM\"}', '430', '0:0:0:0:0:0:0:1', '2018-12-09 21:39:04');
INSERT INTO `sys_log` VALUES ('92', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":15,\"roleName\":\"用户\",\"remark\":\"用户\",\"deptId\":8,\"deptName\":\"浦沿社区\",\"menuIdList\":[],\"deptIdList\":[],\"createTime\":\"Dec 9, 2018 9:39:20 PM\"}', '806', '0:0:0:0:0:0:0:1', '2018-12-09 21:39:21');
INSERT INTO `sys_log` VALUES ('93', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":16,\"roleName\":\"用户\",\"remark\":\"用户\",\"deptId\":9,\"deptName\":\"长河社区\",\"menuIdList\":[],\"deptIdList\":[9],\"createTime\":\"Dec 9, 2018 9:39:32 PM\"}', '212', '0:0:0:0:0:0:0:1', '2018-12-09 21:39:33');
INSERT INTO `sys_log` VALUES ('94', 'admin', '保存角色', 'SysRoleController.save()', '{\"roleId\":17,\"roleName\":\"测试\",\"remark\":\"侧耳\",\"deptId\":9,\"deptName\":\"长河社区\",\"menuIdList\":[],\"deptIdList\":[9],\"createTime\":\"Dec 9, 2018 10:35:36 PM\"}', '44864', '0:0:0:0:0:0:0:1', '2018-12-09 22:35:36');
INSERT INTO `sys_log` VALUES ('95', 'admin', '删除用户', 'SysUserController.delete()', '[7]', '390', '0:0:0:0:0:0:0:1', '2018-12-10 22:48:53');
INSERT INTO `sys_log` VALUES ('96', 'admin', '保存用户', 'SysUserController.save()', '{\"userId\":8,\"username\":\"测试\",\"password\":\"a86e885da3bcaf2e8c7dd5e40da3163960071098e8f988a8ee8487ecded41087\",\"salt\":\"UFnmPtcesgBoLASdcMUG\",\"status\":1,\"roleIdList\":[1,11],\"createTime\":\"Dec 10, 2018 10:51:44 PM\",\"deptId\":9,\"deptName\":\"长河社区\"}', '38357', '0:0:0:0:0:0:0:1', '2018-12-10 22:52:23');
INSERT INTO `sys_log` VALUES ('97', 'admin', '删除用户', 'SysUserController.delete()', '[8]', '30', '0:0:0:0:0:0:0:1', '2018-12-10 22:55:03');
INSERT INTO `sys_log` VALUES ('98', 'admin', '删除用户', 'SysUserController.delete()', '[10]', '84', '0:0:0:0:0:0:0:1', '2018-12-10 23:25:18');
INSERT INTO `sys_log` VALUES ('99', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":13,\"roleName\":\"乔木社区用户\",\"remark\":\"用户\",\"deptId\":2,\"deptName\":\"乔木社区\",\"menuIdList\":[],\"deptIdList\":[2],\"deptRole\":\"1\",\"createTime\":\"Dec 9, 2018 9:38:38 PM\"}', '266', '0:0:0:0:0:0:0:1', '2018-12-10 23:27:56');
INSERT INTO `sys_log` VALUES ('100', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":14,\"roleName\":\"东冠社区用户\",\"remark\":\"用户\",\"deptId\":6,\"deptName\":\"东冠社区\",\"menuIdList\":[],\"deptIdList\":[],\"deptRole\":\"1\",\"createTime\":\"Dec 9, 2018 9:39:03 PM\"}', '33', '0:0:0:0:0:0:0:1', '2018-12-10 23:28:20');
INSERT INTO `sys_log` VALUES ('101', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":15,\"roleName\":\"浦沿社区用户\",\"remark\":\"用户\",\"deptId\":8,\"deptName\":\"浦沿社区\",\"menuIdList\":[],\"deptIdList\":[],\"deptRole\":\"1\",\"createTime\":\"Dec 9, 2018 9:39:20 PM\"}', '169', '0:0:0:0:0:0:0:1', '2018-12-10 23:28:55');
INSERT INTO `sys_log` VALUES ('102', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":16,\"roleName\":\"长河社区用户\",\"remark\":\"用户\",\"deptId\":9,\"deptName\":\"长河社区\",\"menuIdList\":[],\"deptIdList\":[9],\"deptRole\":\"1\",\"createTime\":\"Dec 9, 2018 9:39:32 PM\"}', '71', '0:0:0:0:0:0:0:1', '2018-12-10 23:29:07');
INSERT INTO `sys_log` VALUES ('103', 'admin', '修改角色', 'SysRoleController.update()', '{\"roleId\":10,\"roleName\":\"社区管理员\",\"deptId\":6,\"deptName\":\"东冠社区\",\"menuIdList\":[],\"deptIdList\":[],\"deptRole\":\"0\",\"createTime\":\"Dec 8, 2018 3:29:15 PM\"}', '105', '0:0:0:0:0:0:0:1', '2018-12-10 23:29:38');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'modules/sys/role.html', null, '1', 'fa fa-user-secret', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', null, '1', 'fa fa-th-list', '3');
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'druid/sql.html', null, '1', 'fa fa-bug', '4');
INSERT INTO `sys_menu` VALUES ('6', '1', '定时任务', 'modules/job/schedule.html', null, '1', 'fa fa-tasks', '5');
INSERT INTO `sys_menu` VALUES ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'modules/oss/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6');
INSERT INTO `sys_menu` VALUES ('31', '1', '社区管理', 'modules/sys/dept.html', null, '1', 'fa fa-file-code-o', '1');
INSERT INTO `sys_menu` VALUES ('32', '31', '查看', null, 'sys:dept:list,sys:dept:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('33', '31', '新增', null, 'sys:dept:save,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('34', '31', '修改', null, 'sys:dept:update,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('35', '31', '删除', null, 'sys:dept:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('36', '1', '字典管理', 'modules/sys/dict.html', null, '1', 'fa fa-bookmark-o', '6');
INSERT INTO `sys_menu` VALUES ('37', '36', '查看', null, 'sys:dict:list,sys:dict:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('38', '36', '新增', null, 'sys:dict:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('39', '36', '修改', null, 'sys:dict:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('40', '36', '删除', null, 'sys:dict:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('41', '0', '信息发布管理', null, null, '0', null, '1');
INSERT INTO `sys_menu` VALUES ('42', '41', '政策公告', 'modules/App', null, '1', 'fa fa-newspaper-o', '0');
INSERT INTO `sys_menu` VALUES ('43', '41', '家事讨论', 'modules/App', null, '1', 'fa fa-users', '0');
INSERT INTO `sys_menu` VALUES ('44', '41', '七彩生活', 'modules/App', null, '1', 'fa fa-leaf', '0');
INSERT INTO `sys_menu` VALUES ('45', '41', '闲置分享', 'modules/App', null, '1', 'fa fa-users', '0');
INSERT INTO `sys_menu` VALUES ('46', '41', '兴趣交流', 'modules/App', null, '1', 'fa fa-users', '0');
INSERT INTO `sys_menu` VALUES ('47', '41', '理财交流', 'modules/App', null, '1', 'fa fa-credit-card-alt', '0');
INSERT INTO `sys_menu` VALUES ('48', '42', '新增', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('49', '42', '查看', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('51', '42', '删除', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('52', '43', '查看', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('53', '43', '新增', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('55', '43', '删除', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('56', '44', '新增', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('57', '44', '查看', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('59', '44', '删除', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('60', '45', '新增', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('61', '45', '查看', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('62', '45', '删除', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('63', '46', '查看', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('64', '46', '新增', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('65', '46', '删除', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('66', '47', '查看', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('67', '47', '新增', null, null, '2', null, '0');
INSERT INTO `sys_menu` VALUES ('68', '47', '删除', null, null, '2', null, '0');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `dept_role` varchar(1) DEFAULT NULL COMMENT '部门角色  0-管理员  1- 普通用户 ',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '所有权限', '2', '2018-09-26 22:05:56', '0');
INSERT INTO `sys_role` VALUES ('9', '社区管理员', '社区管理员', '2', '2018-12-08 15:28:24', '0');
INSERT INTO `sys_role` VALUES ('10', '社区管理员', null, '6', '2018-12-08 15:29:15', '0');
INSERT INTO `sys_role` VALUES ('11', '社区管理员', null, '8', '2018-12-08 15:29:36', '0');
INSERT INTO `sys_role` VALUES ('12', '社区管理员', null, '9', '2018-12-08 15:29:49', '0');
INSERT INTO `sys_role` VALUES ('13', '乔木社区用户', '用户', '2', '2018-12-09 21:38:38', '1');
INSERT INTO `sys_role` VALUES ('14', '东冠社区用户', '用户', '6', '2018-12-09 21:39:03', '1');
INSERT INTO `sys_role` VALUES ('15', '浦沿社区用户', '用户', '8', '2018-12-09 21:39:20', '1');
INSERT INTO `sys_role` VALUES ('16', '长河社区用户', '用户', '9', '2018-12-09 21:39:32', '1');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES ('18', '1', '1');
INSERT INTO `sys_role_dept` VALUES ('19', '1', '2');
INSERT INTO `sys_role_dept` VALUES ('20', '1', '6');
INSERT INTO `sys_role_dept` VALUES ('21', '1', '8');
INSERT INTO `sys_role_dept` VALUES ('24', '17', '9');
INSERT INTO `sys_role_dept` VALUES ('25', '13', '2');
INSERT INTO `sys_role_dept` VALUES ('26', '16', '9');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('58', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('59', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('60', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('61', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('62', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('63', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('64', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('65', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('66', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('67', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('68', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('69', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('70', '1', '23');
INSERT INTO `sys_role_menu` VALUES ('71', '1', '24');
INSERT INTO `sys_role_menu` VALUES ('72', '1', '25');
INSERT INTO `sys_role_menu` VALUES ('73', '1', '26');
INSERT INTO `sys_role_menu` VALUES ('74', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('75', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('76', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('77', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('78', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('79', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('80', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('81', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('82', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('83', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('84', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('85', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('86', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('87', '1', '31');
INSERT INTO `sys_role_menu` VALUES ('88', '1', '32');
INSERT INTO `sys_role_menu` VALUES ('89', '1', '33');
INSERT INTO `sys_role_menu` VALUES ('90', '1', '34');
INSERT INTO `sys_role_menu` VALUES ('91', '1', '35');
INSERT INTO `sys_role_menu` VALUES ('92', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('93', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('94', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('95', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('96', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('97', '1', '41');
INSERT INTO `sys_role_menu` VALUES ('98', '1', '42');
INSERT INTO `sys_role_menu` VALUES ('99', '1', '43');
INSERT INTO `sys_role_menu` VALUES ('100', '1', '44');
INSERT INTO `sys_role_menu` VALUES ('101', '1', '45');
INSERT INTO `sys_role_menu` VALUES ('102', '1', '46');
INSERT INTO `sys_role_menu` VALUES ('103', '1', '47');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `community_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11', null);
INSERT INTO `sys_user` VALUES ('4', '15157150200', '74a9efd55ecb5bf444737ea8b2d6f56576dd975c71fbf1346864e711f98f480c', 'EtXDA5bQDttTk92XvCh8', '124926@qq.com', null, '1', '9', '2018-10-23 19:13:38', null);
INSERT INTO `sys_user` VALUES ('11', '15157120300', 'f48aba4769b3a843fc720b8ca4db936f49846aee48a34a10eecce093c27fb509', 'MqMuDLbceHSI9P5A7pDA', null, null, '1', '8', '2018-12-10 23:22:58', '8');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '8', '1');
INSERT INTO `sys_user_role` VALUES ('2', '8', '11');
INSERT INTO `sys_user_role` VALUES ('4', '10', '16');
INSERT INTO `sys_user_role` VALUES ('5', '11', '15');

-- ----------------------------
-- Table structure for tb_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_token`;
CREATE TABLE `tb_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

-- ----------------------------
-- Records of tb_token
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `community_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41', null);

-- ----------------------------
-- Table structure for yw_appraise
-- ----------------------------
DROP TABLE IF EXISTS `yw_appraise`;
CREATE TABLE `yw_appraise` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(19) DEFAULT NULL COMMENT '用户id',
  `news_id` bigint(19) DEFAULT NULL COMMENT '新闻id',
  `time` datetime DEFAULT NULL COMMENT '评价时间',
  `content` varchar(500) DEFAULT NULL COMMENT '评价内容',
  `applaud` int(10) DEFAULT '0' COMMENT '评论赞同数',
  `oppose` int(10) DEFAULT '0' COMMENT '评论反对数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储用户评价新闻数据';

-- ----------------------------
-- Records of yw_appraise
-- ----------------------------

-- ----------------------------
-- Table structure for yw_city
-- ----------------------------
DROP TABLE IF EXISTS `yw_city`;
CREATE TABLE `yw_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='社区表';

-- ----------------------------
-- Records of yw_city
-- ----------------------------
INSERT INTO `yw_city` VALUES ('1', 'shanghai', '上海');
INSERT INTO `yw_city` VALUES ('2', 'hangzhou', '杭州');
INSERT INTO `yw_city` VALUES ('3', 'shenzhen', '深圳');
INSERT INTO `yw_city` VALUES ('4', 'beijing', '北京');

-- ----------------------------
-- Table structure for yw_user_indetity
-- ----------------------------
DROP TABLE IF EXISTS `yw_user_indetity`;
CREATE TABLE `yw_user_indetity` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `user_identity` varchar(20) DEFAULT NULL COMMENT '身份证',
  `address` varchar(70) DEFAULT NULL COMMENT '住址',
  `img_url` varchar(100) DEFAULT NULL COMMENT '图片地址',
  `dept_id` int(11) DEFAULT NULL COMMENT '社区id',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- app升级表
-- ----------------------------
DROP TABLE IF EXISTS `pluto_updateAppinfo`;
CREATE TABLE pluto_updateAppinfo (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appVersion` varchar(20) NOT NULL ,
  `appUrl` varchar(100) DEFAULT NULL,
  `updateType` varchar(20) DEFAULT NULL ,
  `createdAs` datetime(6) DEFAULT NULL ,
  `updatedAs` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pluto_article`;
CREATE TABLE pluto_article (
  `articleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` TEXT NOT NULL ,
  `content` LONGTEXT NOT NULL,
  `category` varchar(20) NOT NULL ,
  `commentId` bigint(20) DEFAULT NULL ,
  `praiseNum` bigint(20) DEFAULT 0 ,
  `authorId` varchar(20) DEFAULT NULL ,
  `createdAt` datetime(6) DEFAULT NULL ,
  `updatedAt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE pluto_article_praise (
  `userId` bigint(20) NOT NULL ,
  `isPraise` varchar(20) DEFAULT '0' ,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `pluto_article_comment`;
CREATE TABLE pluto_article_comment (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` LONGTEXT NOT NULL,
  `articleId` varchar(20) NOT NULL ,
  `userId` varchar(20) NOT NULL ,
  `createdAt` datetime(6) DEFAULT NULL ,
  `updatedAt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `welfare_task`;
CREATE TABLE welfare_task (
  `serviceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `serviceName` LONGTEXT NOT NULL,
  `seviceDetail` LONGTEXT NOT NULL ,
  `status` varchar(20) NOT NULL ,
  `points` varchar(20) NOT NULL ,
  `createdAt` datetime(6) DEFAULT NULL ,
  `updatedAt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `welfare_task_publish_user`;
CREATE TABLE welfare_task_publish_user (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `publishUserId` varchar(20) NOT NULL,
  `serviceId` varchar(20) NOT NULL ,
  `status` varchar(20) NOT NULL ,
  `createdAt` datetime(6) DEFAULT NULL ,
  `updatedAt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `welfare_task_recevie_user`;
CREATE TABLE welfare_task_recevie_user (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `receiveUserId` varchar(20) NOT NULL,
  `serviceId` varchar(20) NOT NULL ,
  `status` varchar(20) NOT NULL ,
  `createdAt` datetime(6) DEFAULT NULL ,
  `updatedAt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;