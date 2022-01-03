package com.example.playground.gogo;

import org.osgi.service.component.annotations.Component;
import org.apache.felix.service.command.CommandProcessor;

@Component(property = {
        CommandProcessor.COMMAND_SCOPE + "=hello",
        CommandProcessor.COMMAND_FUNCTION + "=hello"},
		service=HelloWorld.class)
public class HelloWorld {

	public void hello() {
		System.out.println("Hello World2");
	}
}
