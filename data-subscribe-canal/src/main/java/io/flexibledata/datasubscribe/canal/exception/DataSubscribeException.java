/*
 * Copyright 2016-2018 flexibledata.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package io.flexibledata.datasubscribe.canal.exception;

/**
 * 基类异常
 * 
 * @author tan.jie
 *
 */
public class DataSubscribeException extends RuntimeException {
	private static final long serialVersionUID = -62621495475009441L;

	/**
	 * 使用格式化的错误信息和参数构造一个异常
	 * 
	 * @param errorMessage
	 *            格式化的错误信息
	 * @param args
	 *            错误信息的参数
	 */
	public DataSubscribeException(final String errorMessage, final Object... args) {
		super(String.format(errorMessage, args));
	}

	/**
	 * 使用错误信息和异常构造一个异常信息
	 * 
	 * @param message
	 *            错误信息
	 * @param cause
	 *            错误异常
	 */
	public DataSubscribeException(final String message, final Exception cause) {
		super(message, cause);
	}

	/**
	 * 通过错误异常构造一个异常
	 * 
	 * @param cause
	 *            错误异常
	 */
	public DataSubscribeException(final Exception cause) {
		super(cause);
	}
}
