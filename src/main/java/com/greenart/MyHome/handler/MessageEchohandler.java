package com.greenart.MyHome.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.util.StringUtils;


public class MessageEchohandler extends TextWebSocketHandler {

	List<WebSocketSession> sessions = new ArrayList<>();
	Map<String, WebSocketSession> userSesssions = new HashMap<>();

	//111111111111111111111111111111111111111111111111111
	@Override // 소켓에 연결이 됐을때
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished ..............." + session);
		sessions.add(session);                              // 웹소켓에 연결한 세션들을 모음 관리
		String senderId = getId(session);
		userSesssions.put(senderId, session);
		System.out.println("userSesssions...................................." + userSesssions);
		//String senderEmail = getEmail(session);
	}
	
   //2222222222222222222222222222222222222222222222222222
	@Override // 소켓에 메세지를 보냈을때
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage............." + message +":" +session); // 웹소켓세션 출력  
	
		String senderId = getId(session);
		
		  for(WebSocketSession sess: sessions) {
		   sess.sendMessage(new TextMessage(senderId + ":" + message.getPayload()));
		  }
     //  protocol : cmd, 댓글작성자, 게시글작성자,bno (reply, user2, user1, bno) 
		String msg = message.getPayload();
		
//		if(msg != null) {
//			String[] strs = message.getPayload().split(",");
//		if(strs != null && strs.length == 2 ) {//&& strs.length == 4
//			String cmd = strs[0];
//			String replyWriter = strs[1];
//			String boardWriter = strs[0];
//			String bno = strs[1];
			
//			WebSocketSession boardWriterSession = userSesssions.get(User user);
//			if("introduce2".equals(cmd) && boardWriterSession != null) {
//
//				TextMessage tmpMsg = new TextMessage(user + "님이 " + bno +"번 게시물에 댓글을 달았습니다.");
//				boardWriterSession.sendMessage(tmpMsg);
//			}
		
//		  }
//		}
	}
	
		private String getId(WebSocketSession session) {
			Map<String, Object> httpSession =session.getAttributes();    // SESSION에 잇는것들을 모두 실어서 MAP인 HTTPSESSION에 준다
			
			User loginUser = (User)httpSession.get(httpSession);
			System.out.println("loginUser...................................." + loginUser);
			if(null == loginUser) {
				return session.getId();
			}
			else {
				return loginUser.getName();
			}
			
		}

	//333333333333333333333333333333333333333333333333333333
	@Override // 소켓에 연결이 끊어졌을때
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed ........." + session + status);

	}

}