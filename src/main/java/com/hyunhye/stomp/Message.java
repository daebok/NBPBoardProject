package com.hyunhye.stomp;

import lombok.Data;

@Data
public class Message {
	private String messageSender;
	private String messageContent;

	public Message() {}

	public Message(String messageSender) {
		this.messageSender = messageSender;
	}
}
