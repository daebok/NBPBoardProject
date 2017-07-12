var ajaxPostSend =  function() {
    
    parent.startLayer();      //레이어 시작
    var url = "signup.do";   //Controller 호출
    var postString = "";       // post방식으로 처리하기 위한 파라미터들
    postString   = "id=" + $('#id').val();
    postString += "&password=" + $('#password').val();
    postString += "&name=" + $('#name').val();
    // postString += "&email=" + $('#email').val();

    
    $.ajax({                          // 이부분부터 비동기통신을 하게 된다. 위에서 설정한 값들을 입력후
        type: "POST",
        url: url,
        data: postString,
        success: function(msg) {  //성공시 이 함수를 호출한다.
            setTimeout('parent.stopLayer('+msg+')',2500); 
       }
    });
 };


