package com.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.example.redis.api.IBufferManager;
import com.example.redis.api.IBufferManagerFactory;

public class Activator implements BundleActivator {

	IBufferManager bufferManager;
	
	@Override
	public void start(BundleContext context) throws Exception {

		ServiceTracker bufferTracker = new ServiceTracker(context, IBufferManagerFactory.class.getName(), null);
		bufferTracker.open();
		IBufferManagerFactory bufferFactory = (IBufferManagerFactory) bufferTracker.waitForService(0);
		
		System.out.println("Hello Activator!!");
		
		bufferManager = bufferFactory.createJedis("127.0.0.1", 6379);

		try {
			System.out.println("push redis");
			bufferManager.open();
			bufferManager.setData("TEST", "DBKIM");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		bufferTracker.close();
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {

		try {
			System.out.println(bufferManager.getData("TEST"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Goodbye Activator!!");
	}

}
