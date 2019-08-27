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

package com.qiaomu.common.exception;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.welfare.exception.WelfareException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2016-10-27
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public String handleRRException(RRException e){
		return JSON.toJSONString(BuildResponse.fail());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public String handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return JSON.toJSONString(BuildResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(AuthorizationException.class)
	public String handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return JSON.toJSONString(BuildResponse.fail("没有权限，请联系管理员授权"));
	}

	@ExceptionHandler(WelfareException.class)
	public String handleWelfareException(WelfareException e){
		logger.error(e.getMessage(), e);
		return JSON.toJSONString(BuildResponse.fail(e.getMessage()));
	}


	@ExceptionHandler(CommentException.class)
	public String handleWelfareException(CommentException e){
		logger.error(e.getMessage(), e);
		return JSON.toJSONString(BuildResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e){
		logger.error(e.getMessage(), e);
		return JSON.toJSONString(BuildResponse.fail());
	}
	@ExceptionHandler(UnknownSessionException.class)
	public String UnknownSessionException(UnknownSessionException e){
		return JSON.toJSONString(BuildResponse.fail("请先登录"));
	}
}
