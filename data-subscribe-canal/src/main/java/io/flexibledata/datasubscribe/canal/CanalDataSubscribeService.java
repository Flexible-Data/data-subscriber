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

import io.flexibledata.datasubscribe.api.EventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Canal数据订阅服务
 * 
 * @author tan.jie
 *
 */
@Slf4j
public class CanalDataSubscribeService {

	public void subscribe(CanalClient canalClient, EventListener eventListener) {
		CanalDataSubscribeThread canalDataSubscribeThread = new CanalDataSubscribeThread(canalClient, eventListener);
		canalDataSubscribeThread.setName("CanalDataSubscribeThread");
		log.info("Started CanalDataSubscribeThread.");
		canalDataSubscribeThread.start();
		
	}
}
