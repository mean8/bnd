package com.example.redis.api;

public interface IBufferManagerFactory {

	IBufferManager createJedis(String ip, int port);
	IBufferManager createJedis(String ip, int port, int timeout);
}
