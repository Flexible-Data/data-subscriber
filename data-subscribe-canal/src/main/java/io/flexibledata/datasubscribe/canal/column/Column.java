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
package io.flexibledata.datasubscribe.canal.column;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 变更数据的列
 * 
 * @author tan.jie
 *
 */
@Getter
@ToString
public class Column implements Serializable {
	private static final long serialVersionUID = 2893771144440353117L;

	private String name;
	private String value;
	@Setter
	private String oldValue;
	private boolean changed;

	public Column(final String name, final String value, final boolean changed) {
		this.name = name;
		this.value = value;
		this.changed = changed;
	}
}
