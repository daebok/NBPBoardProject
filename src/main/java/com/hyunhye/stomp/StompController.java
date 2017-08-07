package com.hyunhye.stomp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyunhye.comment.model.Comment;

@Controller
public class StompController {

	@MessageMapping("/stomp")
	@SendTo("/topic/stomp")
	public ResponseEntity<String> stomp(@ModelAttribute Comment commentModel) {
		int boardNo = commentModel.getBoardNo();
		return new ResponseEntity<String>("답변이 달렸습니다!", HttpStatus.OK);
	}

	@RequestMapping("test")
	public String goTestPage() {
		return "websocket";
	}
}
