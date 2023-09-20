package com.goesmodul.springboot.firstrestapi.helloworld;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController //Return the value instead of view
public class HelloWorldResource {
	//hello-world = "Hello World"
	@RequestMapping("/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	
	@RequestMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@RequestMapping("/hello-world-bean/{name}")
	public HelloWorldBean helloWorldBeanPathParam(@PathVariable String name) {
		return new HelloWorldBean("Hello World " + name);
	}
	
	@RequestMapping("/hello-world-bean/{name}/message/{message}")
	public HelloWorldBean helloWorldBeanMultiplePathParam(@PathVariable String name, @PathVariable String message) {
		return new HelloWorldBean("Hello Wolrd " + name + "," + message);
	}
}
