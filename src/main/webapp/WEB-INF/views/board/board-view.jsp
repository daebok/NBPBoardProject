<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jsp"%>

	<div class="container">
		<div class="container-fluid">
			<div class="pull-right" style="margin-bottom:10px;">
				<c:choose>
					<c:when test="${board.bookmarkCheck eq 1}">
						<div class="glyphicon glyphicon-check" id="book-mark-check" onclick="bookmarkCheck(${board.boardNo}, this)" style="font-size:25px; color:#FF3636;"></div>
					</c:when>
					<c:otherwise>
						<div class="glyphicon glyphicon-check" id="book-mark-uncheck"  onclick="bookmarkCheck(${board.boardNo}, this)" style="font-size:25px; color:#888;"></div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-md-12">
				<span class="label label-warning">${board.categoryItem} </span>
				<h2><c:out value="${board.boardTitle}" escapeXml="true"/></h2>
				<p><c:out value="${board.boardContent}" escapeXml="false"/></p>
				<c:if test="${not empty board.boardFileList}">
					<div class="panel panel-default">
						<div class="list-group">
							<div class="list-group-item">
								<div class="list-1"><b>파일명</b></div>
								<div class="list-2"><b>크기</b></div>
							</div>
							<c:forEach var="attach" items="${board.boardFileList}">
								<div class="uploadedList">
									<div class="list-group-item">
										<div class="list-1"><a href='/board/downloadFile?fileName=${attach.fileName}'>${attach.fileOriginName}</a></div>
										<div class="list-2"><file:size value="${attach.fileSize}" /> </div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<div align="right" >
					<b><a class="board-writer"  href="javascript:otherQuestion(<user:id no='${board.userNo}'/>)"><user:id no="${board.userNo}"/>의 다른 게시물 보기<span class="glyphicon glyphicon-hand-right"></span></a></b>
				</div>
				<hr>
				<div class="pull-right">
					<c:if test="${user.userNo == board.userNo}">
						<a href="<c:url value='/board/modify?boardNo=${board.boardNo}'/>" id="modify" class="btn btn-primary">Modify</a>
						<button class="btn btn-primary" onclick="questionDelete(${board.boardNo})">Delete</button>
					</c:if>
					<c:if test="${user.userNo != board.userNo}"> <!-- 관리자 권한 -->
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<button class="btn btn-primary" onclick="questionDelete(${board.boardNo})">Delete</button>
						</sec:authorize>
					</c:if>
				</div>
				<div class="pull-left">
					<form:form name="list" action="${uri}" id="list-form"  method="get">
						<input type="hidden" name="boardNo" value="${board.boardNo}" /> 
						<input type="hidden" name="tab" value="${criteria.tab}" /> 
						<input type="hidden" name="page" value="${criteria.page}" /> 
						<input type="hidden" name="perPageNum" value="${criteria.perPageNum}" />
						<input type="hidden" name="searchType" value="${criteria.searchType}" /> 
						<input type="hidden" name="categoryType" value="${criteria.categoryType}" />
						<input type="hidden" name="toDate" value="${criteria.toDate}" />
						<input type="hidden" name="fromDate" value="${criteria.fromDate}" />
						<input type="hidden" name="keyword" value="${criteria.keyword}" />
						<input type="submit" name="listButton" class="btn btn-primary" value="List"/>
					</form:form>
				</div>
			</div>
		</div>
		<!-- answer -->
		<div id="listComment">
			<%@include file="answer.jsp"%>
		</div>
		
		<!-- answer editor -->
		<div class="col-lg-8" style="margin-top: 50px;">
			<form:form name="form" method="get" class="answer-form">
				<label for="content">Your Answer</label>
				<textarea class="answer-summernote" name="commentContent" id="comment-content"></textarea><br>
				<input type="hidden" name="boardNo" value="${board.boardNo}">
				<input type="hidden" name="userNo" value="${board.userNo}">
				<input type="hidden" name="commentNo" id="answer-no" value="0">
				<div class="pull-right">
					<button id="answer-regist-button" onclick="answerRegist(this, event)" class="btn btn-default">Answer</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
</body>
</html>

<link href="<c:url value="/resources/common/css/file.css" />"		rel="stylesheet">
<link href="<c:url value="/resources/common/css/answer.css" />" 	rel="stylesheet">
<link href="<c:url value="/resources/common/css/board.css" />" 		rel="stylesheet">
<script src="<c:url value="/resources/common/js/answer.js" />">		</script>
<script src="<c:url value="/resources/common/js/board.js" />">		</script>
<script src="<c:url value="/resources/common/js/bookmark.js" />">	</script>
