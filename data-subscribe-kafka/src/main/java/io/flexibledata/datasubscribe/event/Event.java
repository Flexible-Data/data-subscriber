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
package io.flexibledata.datasubscribe.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据订阅上下文
 * 
 * @author tan.jie
 *
 */
@Data
@AllArgsConstructor
public class Event implements Serializable {

	private static final long serialVersionUID = -5506725507250224052L;

	/**
	 * 库名
	 */
	private String schemaName;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 取值范围：insert、update、delete
	 */
	private EventType eventType;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 事件发生时间
	 */
	private String timestamp;
}
