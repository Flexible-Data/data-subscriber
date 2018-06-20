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
package io.flexibledata.datasubscribe.examples.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;

import io.flexibledata.datasubscribe.examples.kafka.subscribe.TestDataSubscribe;
import io.flexibledata.datasubscribe.kafka.KafkaConsumerUtil;
import io.flexibledata.datasubscribe.kafka.KafkaDataSubscribeThread;

/**
 * @author tan.jie
 *
 */
@SpringBootConfiguration
public class Application implements CommandLineRunner {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.33.12:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		List<String> topics = new ArrayList<>();
		topics.add("data-subscribe");

		KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerUtil.getKafkaConsumer(props, topics);

		KafkaDataSubscribeThread dataSubscribeThread = new KafkaDataSubscribeThread(kafkaConsumer,
				new TestDataSubscribe());
		dataSubscribeThread.start();

	}
}
