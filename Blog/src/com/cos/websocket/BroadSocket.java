package com.cos.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/broadCasting")
public class BroadSocket {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen //사용자 접속시 바로 이 함수 실행.
	public void onOpen(Session session) {
		System.out.println("OnOpen 호출");
		clients.add(session);
	}
	
	public static void serverMessage(String message) throws IOException {
		synchronized (clients) {
			for(Session client : clients) 
					client.getBasicRemote().sendText(message);
			}
		}
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException{
		System.out.println("OnMessage 호출");
		System.out.println("message: "+message);
		synchronized (clients) {
			for(Session client : clients) {
				if(!client.equals(session)) {
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}
	
	@OnClose
	public void onClose(Session session) {
		System.out.println("OnClose 호출");
		clients.remove(session);
	}
}
