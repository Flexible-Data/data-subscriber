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
package io.flexibledata.datasubscribe.examples.canal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;

import io.flexibledata.datasubscribe.canal.CanalClient;
import io.flexibledata.datasubscribe.canal.CanalConfig;
import io.flexibledata.datasubscribe.canal.CanalDataSubscribeService;
import io.flexibledata.datasubscribe.examples.canal.subscribe.TestEventLisenter;

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
		Boolean cluster = Boolean.FALSE;
		String destination = "huieryun-test";
		String connectUrl = "192.168.33.12:8092";
		String tableFilterRegex = "nuskin_dev_item.it_item,nuskin_dev_item.it_item_shelf";

		CanalConfig canalConfig = new CanalConfig(cluster, destination, connectUrl, tableFilterRegex);
		CanalClient canalClient = new CanalClient(canalConfig);
		CanalDataSubscribeService dataSubscribeService = new CanalDataSubscribeService();
		dataSubscribeService.subscribe(canalClient, new TestEventLisenter());
	}
}
