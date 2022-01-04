package com.hdbsnc.smariot.redis.impl;

import java.util.List;

import com.example.redis.api.IBufferManager;
import com.hdbsnc.smariot.redis.jedis.RedisApi;

public class JedisBufferManager implements IBufferManager {
	
	private RedisApi _api;

	public JedisBufferManager(String ip, int port) {
		_api = new RedisApi(ip, port);
	}

	public JedisBufferManager(String ip, int port, int timeout) {
		_api = new RedisApi(ip, port, timeout);
	}
	
	@Override
	public void rPush(String key, String value) throws Exception {
		_api.rPush(key, value);
	}

	@Override
	public void rPush(String key, String[] values) throws Exception {
		_api.rPush(key, values);
	}

	@Override
	public void lPush(String key, String value) throws Exception {
		_api.lPush(key, value);
	}

	@Override
	public void lPush(String key, String[] values) throws Exception {
		_api.lPush(key, values);
	}
	
	@Override
	public void open() throws Exception{
		_api.open();
	}
	
	@Override
	public void open(int maxPopCount) throws Exception{
		_api.open(maxPopCount);
	}

	@Override
	public void close() {
		_api.close();
	}

	@Override
	public int getCount(String key) {
		return _api.getCount(key);
	}

	@Override
	public void save() {
		_api.save();
	}

	@Override
	public List<String> popAll(String key) throws Exception {
		return _api.popAll(key);
	}

	@Override
	public List<String> pop(String key, int count) throws Exception {
		return _api.pop(key, count);
	}

	@Override
	public String pop(String key) throws Exception {
		return _api.pop(key);
	}

	@Override
	public boolean isConnected() {
		return _api.isConnected();
	}
	
	@Override
	public void removeKey(String key) {
		 _api.removeKey(key);
	}

	@Override
	public void reconnect() throws Exception {
		 _api.reconnect();
	}

	@Override
	public void setData(String key, String value) throws Exception {
		_api.setData(key, value);
	}

	@Override
	public String getData(String key) throws Exception {
		return _api.getData(key);
	}
	
}
