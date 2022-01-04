package com.hdbsnc.smariot.redis.jedis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author DBeom REDIS DB를 사용하기 위해 JEDIS를 사용한 API
 */
public class RedisApi {

	private int _port;
	private String _ip;
	private int _timeout;
	private JedisPool _pool;
	private Jedis _jedis;

	private static final int DEFAULT_MAX_POP_SIZE = 100;

	private int _popCount = DEFAULT_MAX_POP_SIZE;

	public RedisApi(String ip, int port) {
		this(ip, port, 3000);
	}
	
	public RedisApi(String ip, int port, int timeout) {
		_ip = ip;
		_port = port;
		_timeout = timeout;
	}

	public int getPort() {
		return _port;
	}

	public String getIp() {
		return _ip;
	}

	public int getTimeout() {
		return _timeout;
	}

	/**
	 * JedisDB에 값을 RPUSH 합니다.
	 * 
	 * @param keys
	 * @param values
	 * @throws Exception
	 */
	public synchronized void rPush(String key, String value) throws Exception {
		String[] values = new String[1];
		values[0] = value;
		this.rPush(key, values);
	}

	/**
	 * JedisDB에 값을 RPUSH 합니다.
	 * 
	 * @param keys
	 * @param values
	 * @throws Exception
	 */
	public synchronized void rPush(String key, String[] values) throws Exception {

		try {
			for (int i = 0; i < values.length; i++) {
				_jedis.rpush(key, values[i]);
			}

		} catch (Exception e) {
			reconnect();
			throw e;
		}
	}

	/**
	 * JedisDB에 값을 LPUSH 합니다.
	 * 
	 * @param keys
	 * @param values
	 * @throws Exception
	 */
	public synchronized void lPush(String key, String value) throws Exception {
		String[] values = new String[1];
		values[0] = value;
		this.lPush(key, values);
	}

	/**
	 * JedisDB에 값을 LPUSH 합니다.
	 * 
	 * @param keys
	 * @param values
	 * @throws Exception
	 */
	public synchronized void lPush(String key, String[] values) throws Exception {

		try {
			for(int i = values.length-1; i >= 0; i--) {
				_jedis.lpush(key, values[i]);
			}
//			for (int i = 0; i < values.length; i++) {
//				_jedis.lpush(key, values[i]);
//			}

		} catch (Exception e) {
			reconnect();
			throw e;
		}
	}

	/**
	 * 디폴트 100개 부터 N개까지 데이터를 POP 합니다.
	 * 
	 * @param key
	 */
	public synchronized List<String> popAll(String key) throws Exception {

		List<String> result;
		int idx = 0;

		result = new ArrayList<>();

		try {

			while (_jedis.exists(key)) {
				idx++;
				result.add(_jedis.lpop(key));
				if (idx == _popCount) {
					break;
				}
			}

		} catch (Exception e) {
			reconnect();
			throw e;
		}

		return result;
	}

	/**
	 * 파라미터에 입력받은 수까지 데이터를 POP 합니다.
	 * @param key
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public synchronized List<String> pop(String key, int count) throws Exception {

		List<String> result;
		int idx = 0;

		result = new ArrayList<>();

		try {

			while (_jedis.exists(key)) {
				idx++;
				result.add(_jedis.lpop(key));
				if (idx == count) {
					break;
				}
			}

		} catch (Exception e) {
			reconnect();
			throw e;
		}

		return result;
	}

	public synchronized String pop(String key) throws Exception {

		String result;
		result = _jedis.lpop(key);
			
		return result;
	}
	
	public synchronized void save() {
		try {
			_jedis.bgsave();
		} catch (Exception e) {
			throw e;
		}
	}

	public synchronized int getCount(String key) {
		return _jedis.lrange(key, 0, -1).size();
	}

	public synchronized void reconnect() throws Exception {
		try {

			this.jedisClose();
			_jedis = _pool.getResource();

		} catch (Exception e) {
			throw e;
		}
	}

	public synchronized boolean isConnected() {

		if (_jedis.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * POP 갯수가 최대 100개까지 생성이 가능한 JEDIS를 생성합니다.
	 * 
	 * @param ip
	 * @param port
	 * @param timeout
	 * @param maxPopCount
	 */
	public void open() {

		_pool = new JedisPool(new JedisPoolConfig(), _ip, _port, _timeout);
		_jedis = _pool.getResource();
	}

	/**
	 * 최대 N개까지 생성이 가능한 JEDIS를 생성합니다.
	 * 
	 * @param ip
	 * @param port
	 * @param timeout
	 * @param maxPopCount
	 */
	public void open(int maxPopCount) {

		_popCount = maxPopCount;

		_pool = new JedisPool(new JedisPoolConfig(), _ip, _port, _timeout);
		_jedis = _pool.getResource();
	}

	private void jedisClose() {
		_jedis.disconnect();
		_jedis.close();
	}

	public void close() {
		_pool.close();
	}

	public void removeKey(String key) {
		_jedis.del(key);
	}
	
	public synchronized void setData(String key, String value) throws Exception {
		try {
			_jedis.set(key, value);
		} catch (Exception e) {
			reconnect();
			throw e;
		}
	}
	
	public synchronized String getData(String key) throws Exception {
		try {
			return _jedis.get(key);
		} catch (Exception e) {
			reconnect();
			throw e;
		}
	}
}
