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

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.google.common.base.Preconditions;

/**
 * KafkaConsumer工具类
 * 
 * @author tan.jie
 *
 */
public class KafkaConsumerUtil {

	/**
	 * 创建Kafka消费者
	 * 
	 * @param props
	 * @param topics
	 * @return
	 */
	public static KafkaConsumer<String, String> getKafkaConsumer(Properties props, List<String> topics) {
		Preconditions.checkNotNull(props, "Kafka connection properties can't null!");
		Preconditions.checkArgument(topics != null && !topics.isEmpty(), "Kafka topic is not empty!");
		KafkaConsumer<String, String> result = new KafkaConsumer<>(props);
		result.subscribe(topics);
		return result;
	}
}
