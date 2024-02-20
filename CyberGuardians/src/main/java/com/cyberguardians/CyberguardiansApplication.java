package com.cyberguardians;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cyberguardians.controller.ChatController;
import com.cyberguardians.controller.UrlController;
import com.cyberguardians.listener.CyberGuardiansListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

@SpringBootApplication
public class CyberguardiansApplication {
	
	public static void main(String[] args) throws LoginException {
		ApplicationContext context = SpringApplication.run(CyberguardiansApplication.class, args);
	    ChatController chatController = context.getBean(ChatController.class);
	    UrlController urlController = context.getBean(UrlController.class);
	    String token = context.getEnvironment().getProperty("app.discord.token");
	    JDA jda = JDABuilder.createDefault(token)
	            .setActivity(Activity.playing("메세지 기다리는 중..."))
	            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
	            .addEventListeners(new CyberGuardiansListener(chatController, urlController))
	            .build();		
    }

}
