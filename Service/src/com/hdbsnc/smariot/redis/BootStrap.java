package com.hdbsnc.smariot.redis;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.example.redis.api.IBufferManagerFactory;
import com.hdbsnc.smariot.redis.impl.BufferManagerFactory;

public class BootStrap implements BundleActivator {

	private ServiceRegistration bufferFactoryService;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		IBufferManagerFactory bufferFactory = new BufferManagerFactory();
		this.bufferFactoryService = bundleContext.registerService(IBufferManagerFactory.class.getName(), bufferFactory, null);
		System.out.println("registerService: " + IBufferManagerFactory.class.getName());
		
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Un registerService: " + IBufferManagerFactory.class.getName());
		this.bufferFactoryService.unregister();
	}

}
