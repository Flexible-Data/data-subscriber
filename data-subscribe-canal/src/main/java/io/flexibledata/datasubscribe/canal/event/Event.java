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
package io.flexibledata.datasubscribe.canal.event;

import java.io.Serializable;
import java.util.List;

import io.flexibledata.datasubscribe.canal.column.Column;
import io.flexibledata.datasubscribe.canal.constant.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 事件基类
 * 
 * @author tan.jie
 *
 */
@Data
@AllArgsConstructor
public abstract class Event implements Serializable {

	private static final long serialVersionUID = 4957985613936777142L;

	private EventType eventType;
	private String schemaName;
	private String tableName;
	private long executeTime;
	protected List<Column> columns;

}
