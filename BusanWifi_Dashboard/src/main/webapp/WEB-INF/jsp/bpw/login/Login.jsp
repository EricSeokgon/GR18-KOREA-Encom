<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<ax:layout name="bpw/bpwMainLayout.jsp">

<%-- <ax:layout name="blankLayout.jsp"> --%>
	<ax:div name="user_css_script">
		<script type="text/javascript">

		
		
		$(function() {
			//쿠키값 얻기
			var cookie_user_id = getId();
			if(cookie_user_id != "") {
				$("#user_id").val(cookie_user_id);
				$("#chkId").attr("checked", true);
			}		
		});
		
//	 	$(function() {
//	 		var url = "<c:url value='/base/login/GetLogoFileId.json'/>";
//	 		var data = $( "#fm_login" ).serializeArray();	
//	 		$.post(url,data, function(json) {
//	 			$("#login_message").html(json.cmpny_login_message)
//	  			if(json.atch_file_id != undefined || json.file_sn == 2){
//	  				var logo_url = "<c:url value='/base/file/FileDownload.do'/>?atch_file_id="+json.atch_file_id+"&file_sn=2";
//	  				$("#form_login_logo").html('<img src="'+logo_url+'" alt="logo_image"/>');
//	  			}else{
//	  				$("#form_login_logo").html("이미지를 설정해주세요.");
//	  			}
//	 		});
//	 	});

		function fn_loging() {
			
	    	//쿠키저장 
	    	if($("#chkId").is(":checked")) saveId($("#user_id").val());
	    	else saveId("");

			if(!$("#fm_login").ncForm("validForm")){
				return;
			}
			
			var url = "<c:url value='/base/login/Login.json'/>";
			
			var data = $( "#fm_login" ).serializeArray();
			
			$.post(url,data, function(json) {
				if(json.result ==1){
					document.location.href='/bpw/dashboard/dashBoard.do?_idxn=247';
// 					alert(json.from_url);
				} else{
					alert(json.result);
				}
			});
		}
		
		//아이디 쿠키에 저장
		function saveId(id) {
			if (id != "") {
				setCookie("userid", id, 7); //userid라는 이름으로 7일동안 저장 
			} else {
				setCookie("userid", id, -1);
			}
		}
		function getId() {
			var cook = document.cookie + ";";
			var idx = cook.indexOf("userid", 0);
			var val = "";
			if (idx != -1) {
				cook = cook.substring(idx, cook.length);
				begin = cook.indexOf("=", 0) + 1;
				end = cook.indexOf(";", begin);
				val = unescape(cook.substring(begin, end));
			}
			return val;
		}
		function setCookie(cookie_name, value, exdays) {
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + exdays);
			var cookie_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString())+"; path=/";
			document.cookie = cookie_name + "=" + cookie_value;
		} 

		</script>
	</ax:div>

	<ax:div name="contents">

	<div id="wrap" class="cf">
        <div class="logo"><a href="#"><img src="<c:url value='/css/bpw/login/images/busan_logo.png'/>" alt="logo" /></a></div>
        
        <div class="login_form">
            <div>
                <div class="title"><p>부산시 공공 wifi <span>관리 시스템</span></p></div>
                <div class="form">
                	<p class="tit">LOGIN</p>
				    <form name="fm_login" id="fm_login" method="post">
                    <input type="id" id="user_id" name="user_id" placeholder="아이디를 입력하세요." ncMsg="아이디를 입력하세요."/>
                    <input type="password" id="password"  name="password" placeholder="비밀번호를 입력하세요." ncMsg="패스워드를 입력하세요."  enter="fn_loging()"/>
                    <label for="chkId"><input type="checkbox" id="chkId" /><span></span> ID 기억하기</label>
                    <button class="login_btn" onclick="javascript:fn_loging();">LOGIN</button>
                    </form>
                </div>
                <div class="copyright"><copyright>Copyright © Busan Metropolitan City. All rights reserved.</copyright></div>
            </div>	
        </div>
    </div>


	</ax:div>
</ax:layout>