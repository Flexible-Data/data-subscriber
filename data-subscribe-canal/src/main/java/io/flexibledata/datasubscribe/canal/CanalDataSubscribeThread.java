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
package io.flexibledata.datasubscribe.canal;

import java.util.List;

import org.slf4j.MDC;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.google.common.eventbus.EventBus;

import io.flexibledata.datasubscribe.api.EventListener;
import io.flexibledata.datasubscribe.canal.event.Event;
import io.flexibledata.datasubscribe.canal.handler.CanalEventHandler;
import lombok.AllArgsConstructor;

/**
 * Canal数据订阅线程
 * 
 * @author tan.jie
 *
 */
@AllArgsConstructor
public class CanalDataSubscribeThread extends Thread {
	private static final int SLEEP_TIME = 500;
	public static final int QUEUE_MAX_SIZE = 10000;

	private CanalClient canalClient;
	private EventListener eventListener;

	@Override
	public void run() {
		process();
	}

	public void process() {
		int batchSize = 5 * 1024;
		MDC.put("destination", canalClient.getCanalConfig().getDestination());
		MDC.put("tableFilterRegex", canalClient.getCanalConfig().getTableFilterRegex());
		CanalConnector connector = canalClient.getConnector();
		EventBus eventBus = new EventBus("CanalEventBus");
		eventBus.register(eventListener);
		CanalEventHandler eventHandler = new CanalEventHandler();
		while (true) {
			// 获取指定数量的数据
			Message message = connector.getWithoutAck(batchSize);
			long batchId = message.getId();
			int size = message.getEntries().size();
			if (batchId == -1 && size == 0) {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				List<Event> events = eventHandler.onEvent(message);
				for (Event each : events) {
					eventBus.post(each);
				}
			}
			connector.ack(batchId);
		}
	}

}
