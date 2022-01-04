package com.hdbsnc.smariot.redis.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class JedisBufferManagerTest {

	@Before
	@Ignore
	public void jedisPush() throws Exception {
		
		JedisBufferManager manager = (JedisBufferManager) new BufferManagerFactory().createJedis("127.0.0.1", 6379);
		manager.open();
		manager.setData("JUNIT TEST", "JUNIT TEST");
	}
	
	@After
	@Test
	@Ignore
	public void jedisPop() throws Exception {
		JedisBufferManager manager = (JedisBufferManager) new BufferManagerFactory().createJedis("127.0.0.1", 6379);

		String testData = manager.getData("JUNIT TEST");
		assertEquals(testData, "JUNIT TEST");
	}
}
