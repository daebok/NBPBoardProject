<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
<html>
<head>
<script type="text/javascript">
	var socket = new SockJS('/stomp');
	var stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		$('#console').append('Connected: ' + frame + '<br>');
		stompClient.subscribe('/topic/stomp', function(message) {
			$('#console').append(message.body + '<br>');
		});
	});

	function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
		}
		$('#console').append('Disconnected<br>');
	}

	function messageSend() {
		stompClient.send("/stomp", {}, $('#message').val());
	}
</script>
</head>
<body>
	<input type="text" id="message" />
	<input type="button" value="전송" onclick="messageSend();" />
	<input type="button" value="종료" onclick="disconnect();" />
	<div id="console"></div>
</body>
</html>



