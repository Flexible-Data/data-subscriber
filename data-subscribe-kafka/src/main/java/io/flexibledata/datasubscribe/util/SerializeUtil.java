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
package io.flexibledata.datasubscribe.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.flexibledata.datasubscribe.event.Event;

/**
 * @author tan.jie
 *
 */
public class SerializeUtil {
	/**
	 * 将Event序列化后的字符串反序列化
	 * 
	 * @param serStr
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Event deserializeFromStr(String serStr) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		String deserStr = java.net.URLDecoder.decode(serStr, "UTF-8");
		byteArrayInputStream = new ByteArrayInputStream(deserStr.getBytes("ISO-8859-1"));
		objectInputStream = new ObjectInputStream(byteArrayInputStream);
		return (Event) objectInputStream.readObject();
	}

	/**
	 * 将Event对象序列化成字符串
	 * 
	 * @param event
	 * @return
	 * @throws IOException
	 */
	public static String writeToStr(Event event) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		String serStr = null;
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(event);
		serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		return serStr;
	}
}
