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
package io.flexibledata.datasubscribe.kafka;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.flexibledata.datasubscribe.api.IDataSubscribe;
import io.flexibledata.datasubscribe.event.Event;
import io.flexibledata.datasubscribe.util.SerializeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Kafka数据订阅线程
 * 
 * @author tan.jie
 *
 */
@Slf4j
@AllArgsConstructor
public class KafkaDataSubscribeThread extends Thread {
	private KafkaConsumer<String, String> kafkaConsumer;
	private IDataSubscribe dataSubscribe;

	@Override
	public void run() {
		process();
	}

	public void process() {
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				try {
					Event event = SerializeUtil.deserializeFromStr(record.value());
					dataSubscribe.subscribeData(event);
				} catch (ClassNotFoundException e) {
					log.error("Can't deserialize {} to Event object.", record.value(), e);
				} catch (IOException e) {
					log.error("IOException when deserializing {} to Event object ", e);
				}
			}
		}
	}

}
