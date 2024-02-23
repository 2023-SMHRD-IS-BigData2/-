package com.cyberguardians.listener;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberguardians.controller.ChatController;
import com.cyberguardians.controller.UrlController;
import com.cyberguardians.entity.Javas_Url;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Service
@Slf4j
public class CyberGuardiansListener extends ListenerAdapter {

	@Autowired
	private ChatController chatController;

	@Autowired
	private UrlController urlController;

	public CyberGuardiansListener(ChatController chatController, UrlController urlController) {
		this.chatController = chatController;
		this.urlController = urlController;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		User user = event.getAuthor();
		TextChannel textChannel = event.getChannel().asTextChannel();
		Message message = event.getMessage();

		log.info("get message : " + message.getContentDisplay());

		if (user.isBot()) {
			return;
		} else if (message == null || message.getContentDisplay() == null || message.getContentDisplay().equals("")) {
			log.info("디스코드 Message 문자열 값 공백");
		}

		if (message != null || message.getContentDisplay() != null) {
			// URL 추출을 위한 정규표현식 패턴
			String regex = "(http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(message.getContentRaw());
			// 매칭된 URL 출력
			while (matcher.find()) {
				int result = -1;
				String matchedURL = matcher.group(0);
				System.out.println("matchedURL : " + matchedURL);
				Javas_Url selected = urlController.selectUrl(matchedURL);
				System.out.println("selected : " + selected);
				if (selected == null) {
					result = chatController.checkUrl(matchedURL);
					System.out.println("checkUrl result : " + result);
				} else if (selected != null) {
					result = selected.getUrl_result();
					System.out.println("selected result : " + result);
				}

				if (result > 0) {
					message.delete().queue();
					textChannel.sendMessage(user.getAsMention() + " ||" + message.getContentDisplay() + "||").queue();
					if (result == 1) {
						textChannel.sendMessage("위 사이트는 '딥페이지' 위험이 있습니다. 주의 하세요! \n자세한 내용은 명령어(!)를 통해 '딥페이지'를 검색해주세요.")
								.queue();
					} else if (result == 2) {
						textChannel.sendMessage("위 사이트는 '피싱사이트' 위험이 있습니다. 주의 하세요! \n자세한 내용은 명령어(!)를 통해 '피싱사이트'를 검색해주세요.")
								.queue();
					} else if (result == 3) {
						textChannel.sendMessage("위 사이트는 '멀웨어페이지' 위험이 있습니다. 주의 하세요! \n자세한 내용은 명령어(!)를 통해 '멀웨어페이지'를 검색해주세요.")
								.queue();
					}
				} else if (result == 0) {
					textChannel.sendMessage("링크가 안전합니다!").queue();
					if (selected == null) {
						urlController.insertUrl(matchedURL, result);
					}
				} else if (result < 0) {
					textChannel.sendMessage("링크 확인 불가!").queue();
				}
			}
		}

		String[] messageArray = message.getContentDisplay().split("! ");
		if (messageArray[0].equalsIgnoreCase("")) {
			String[] messageArgs = Arrays.copyOfRange(messageArray, 1, messageArray.length);
			for (String msg : messageArgs) {
				String returnMessage = sendMessage(event, msg);
				textChannel.sendMessage(returnMessage).queue();
			}
		}

	}

	private String sendMessage(MessageReceivedEvent event, String message) {
		User user = event.getAuthor();
		String returnMessage = "";

		switch (message) {
		case "hi":
			returnMessage = user.getAsMention() + "님 반갑습니다 !";
			break;
		case "딥페이지":
			returnMessage = chatController.chat("'딥페이지' 관련된 사이트 위험요소와 피해 사례와 해결방안 구체적으로 설명해줘").getChoices().get(0).getMessage()
					.getContent();
			break;
		case "피싱사이트":
			returnMessage = chatController.chat("피싱사이트 관련된 사이트 위험요소와 피해 사례와 해결방안 구체적으로 설명해줘").getChoices().get(0).getMessage()
					.getContent();
			break;
		case "멀웨어페이지":
			returnMessage = chatController.chat("멀웨어 관련된 사이트 위험요소와 피해 사례와 해결방안 구체적으로 설명해줘").getChoices().get(0).getMessage().getContent();
			break;
		case "소개":
			returnMessage = user.getAsMention() + "님 저는 Javas팀이 JDA로 구현한 Discord Bot이에요 !\n자세한 소개는 http://220.80.88.122:8082/javas 참고하세요 !";
			break;
		default:
			returnMessage = "명령어를 확인해 주세요.";
			break;
		}
		return returnMessage;
	}

}
