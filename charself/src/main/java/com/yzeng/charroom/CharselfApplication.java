package com.yzeng.charroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.yzeng.charroom.server.SocketServer;

@SpringBootApplication
public class CharselfApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext 
			= SpringApplication.run(CharselfApplication.class, args);
		SocketServer.setApplicationContext(applicationContext);
	}
}
