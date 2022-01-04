package com.example.redis.api;

import java.util.List;

public interface IBufferManager {
	
	void lPush(String key, String value) throws Exception;
	void lPush(String key, String[] values) throws Exception;
	
	void rPush(String key, String value) throws Exception;
	void rPush(String key, String[] values) throws Exception;
	List<String> popAll(String key) throws Exception;
	List<String> pop(String key, int count) throws Exception;
	String pop(String key) throws Exception;
	
	void setData(String key, String value) throws Exception ;
	String getData(String key) throws Exception ;
	
	void save();
	void open() throws Exception;
	void open(int maxPopCount) throws Exception;
	int getCount(String key);
	void close();	
	void removeKey(String key);
	
	boolean isConnected();
	void reconnect() throws Exception;
}
