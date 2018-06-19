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
package io.flexibledata.datasubscribe.canal.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;

import io.flexibledata.datasubscribe.canal.column.Column;
import io.flexibledata.datasubscribe.canal.event.Event;
import io.flexibledata.datasubscribe.canal.event.delete.DeleteEvent;
import io.flexibledata.datasubscribe.canal.event.insert.InsertEvent;
import io.flexibledata.datasubscribe.canal.event.update.UpdateEvent;

/**
 * 事件处理器接口
 * 
 * @author tan.jie
 *
 */
public class CanalEventHandler {

	public List<Event> onEvent(Message message) {
		List<Event> events = new ArrayList<>();
		for (Entry entry : message.getEntries()) {
			if (entry.getEntryType() == EntryType.ROWDATA) {
				List<Event> eventList = handlerRowData(entry);
				if (!CollectionUtils.isEmpty(eventList)) {
					events.addAll(eventList);
				}
			}
		}
		return events;
	}

	/**
	 * 处理Canal中的Entry数据
	 * 
	 * @param entry
	 * @return
	 */
	private List<Event> handlerRowData(Entry entry) {
		RowChange rowChage = null;
		try {
			rowChage = RowChange.parseFrom(entry.getStoreValue());
		} catch (Exception e) {
			throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
		}

		EventType eventType = rowChage.getEventType();
		if (eventType == EventType.QUERY || rowChage.getIsDdl()) {
			return null;
		}

		long executeTime = entry.getHeader().getExecuteTime();
		String schemaName = entry.getHeader().getSchemaName();
		String tableName = entry.getHeader().getTableName();

		List<Event> events = new ArrayList<>(rowChage.getRowDatasList().size());
		for (RowData rowData : rowChage.getRowDatasList()) {
			Event event = null;
			if (eventType == EventType.DELETE) {
				event = handleDeleteData(rowData, schemaName, tableName, executeTime);
			} else if (eventType == EventType.INSERT) {
				event = handleInsertData(rowData, schemaName, tableName, executeTime);
			} else {
				event = handleUpdateData(rowData, schemaName, tableName, executeTime);
			}
			if (null != event) {
				events.add(event);
			}
		}

		return events;
	}

	private Event handleInsertData(RowData rowData, String schemaName, String tableName, long executeTime) {
		List<Column> columns = convertEventColumns(rowData);
		Event result = new InsertEvent(schemaName, tableName, executeTime, columns);
		return result;
	}

	private Event handleDeleteData(RowData rowData, String schemaName, String tableName, long executeTime) {
		List<Column> columns = convertEventColumns(rowData);
		Event result = new DeleteEvent(schemaName, tableName, executeTime, columns);
		return result;
	}

	private List<Column> convertEventColumns(RowData rowData) {
		ArrayList<Column> result = new ArrayList<Column>();

		for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
			result.add(convertEventColumn(column));
		}
		return result;
	}

	private Column convertEventColumn(CanalEntry.Column column) {
		return new Column(column.getName(), column.getValue(), column.getUpdated());
	}

	private Event handleUpdateData(RowData rowData, String schemaName, String tableName, long executeTime) {

		List<Column> columns = new ArrayList<>();
		for (int i = 0; i < rowData.getAfterColumnsList().size(); i++) {
			CanalEntry.Column afterColumn = rowData.getAfterColumnsList().get(i);
			Column column = convertEventColumn(afterColumn);
			CanalEntry.Column beforeColumn = rowData.getBeforeColumnsList().get(i);
			column.setOldValue(beforeColumn.getValue());
			columns.add(column);
		}
		Event updateEvent = new UpdateEvent(schemaName, tableName, executeTime, columns);
		return updateEvent;
	}

}
