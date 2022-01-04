package com.hdbsnc.smariot.redis.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.redis.api.IBufferManager;
import com.example.redis.api.IBufferManagerFactory;

public class BufferManagerFactory implements IBufferManagerFactory{

	private static final Map<String, IBufferManager> UNIQUE_KEY_MAP = new ConcurrentHashMap<String, IBufferManager>();
	
	@Override
	public IBufferManager createJedis(String ip, int port) {
		String uniqueKey = ip + ":" + port;
		if(!UNIQUE_KEY_MAP.containsKey(uniqueKey)) {
			IBufferManager buffer = new JedisBufferManager(ip, port);
			UNIQUE_KEY_MAP.put(uniqueKey, buffer);
		} 
		
		return UNIQUE_KEY_MAP.get(uniqueKey);
	}
	
	@Override
	public IBufferManager createJedis(String ip, int port, int timeout) {
		String uniqueKey = ip + ":" + port;
		if(!UNIQUE_KEY_MAP.containsKey(uniqueKey)) {
			IBufferManager buffer = new JedisBufferManager(ip, port, timeout);
			UNIQUE_KEY_MAP.put(uniqueKey, buffer);
		} 
		
		return UNIQUE_KEY_MAP.get(uniqueKey);
	}
}
