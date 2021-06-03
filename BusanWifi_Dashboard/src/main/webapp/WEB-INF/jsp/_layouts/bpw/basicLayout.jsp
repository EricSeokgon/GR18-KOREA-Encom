<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title></title>
  <c:import url="/PageLink.do?link=_layouts/adminLte/inc/head_css_js" />  
  
    <!-- 커뮤니티키친플랫폼 css   -->
<%--   <link rel="stylesheet" href="<c:url value='/css/ckf/style.css'/>"> --%>
  
 

  
 <script type="text/javascript">
	
 <%--	
 	//사이드메뉴 토글 고정때문에 반드시 최상위에 필요.
	//app.js 336, 339 line
--%>
	function setCookie(cookie_name, value, exdays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var cookie_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString())+"; path=/";
		document.cookie = cookie_name + "=" + cookie_value;
	} 
			
	
	function fn_main_Search()
	{	
		
		if($("#fmMainSearch #main_search_cn").val().replace(/\s/gi, "")=="")	{
			alert("검색어를 입력하세요.");
			$("#fmMainSearch #main_search_cn").focus();
			return false;
		}
		return true;
	}	
	
// 	$(function(){
// 		fn_get_cmpny_info();
// 	});
	
// 	function fn_get_cmpny_info(){
// 		var url ="<c:url value='/copn/siteinfo/MenuSiteInfo.json'/>";
// 		var data= null;

// 		$("#").ncPost(url, function(menu_cmpny_data)
// 		{
// 			$("#footer_cmpny_nm").html(menu_cmpny_data.cmpny_nm)
// 			$.each(menu_cmpny_data.list, function (index, item) {
// 				if(item.file_sn == '0'){
// 					var logo_left_large_url = "<c:url value='/base/file/FileDownload.do'/>?atch_file_id="+menu_cmpny_data.atch_file_id+"&file_sn=0";
// 					$("#large_logo_header").html('<img src="'+logo_left_large_url+'" alt="logo_left_lagre_image"/>');
					
// 				}
// 				if(item.file_sn == '1'){
// 					var logo_left_small_url = "<c:url value='/base/file/FileDownload.do'/>?atch_file_id="+menu_cmpny_data.atch_file_id+"&file_sn=1";
// 					$("#small_logo_header").html('<img src="'+logo_left_small_url+'" alt="logo_left_small_image"/>');
				
// 				}
// 				if(item.file_sn == '3'){
// 					var logo_left_circle_url = "<c:url value='/base/file/FileDownload.do'/>?atch_file_id="+menu_cmpny_data.atch_file_id+"&file_sn=3";
// 					$("#log_circle").attr('src',logo_left_circle_url);
// 				}
// 			});		
// 		}); 
// 	}
	
	
</script>   
 
</head>

<%--  <body class="hold-transition skin-blue sidebar-mini  ${sidebar_flag}"> --%>
<body class="hold-transition skin-blue sidebar-mini  ${cookie.sidebar_flag.value}">
<!-- Site wrapper -->
<div class="wrapper">

  <header class="main-header">
    <!-- Logo -->
	<div id="logo_image" name="logo_image">
	<a href="<c:url value='/bpw/dashboard/dashBoard.do?_idxn=247'/>" class="logo">
	<!-- mini logo for sidebar mini 50x50 pixels 	-->	<span class="logo-mini" id="small_logo_header" name="small_logo_header"><img src="<c:url value='/css/bpw/logo1.png'/>" alt="로고" class="logo_top" style="width:100%"></span> 
	<!-- logo for regular state and mobile devices 	-->	<span class="logo-lg"	id="large_logo_header" name="large_logo_header"><img src="<c:url value='/css/bpw/logo.png'/>" alt="로고" class="logo_top" style="width:100%"></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="" class="sidebar-toggle" data-toggle="offcanvas" role="button">
<!--       <a href="javascript:fn_toggle();" class="sidebar-toggle"  role="button"> -->
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
        
	      <%-- top 메뉴 --%>
	      <c:import url="/base/menu/menuTreeList.do?baseMenuNo=${_baseMenuNo}&nextUrl=_layouts/adminLte/inc/topMenu" />
          <li>
            <a href="<c:url value='/base/login/Logout.do'/>" ><i class="fa fa-sign-out"></i> 로그아웃</a>
          </li>
        </ul>
      </div>

    </nav>
 
  </header>

  <!-- =============================================== -->

  <!-- Left side column. contains the sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <div class="user-panel">
			<div class="pull-left image">
<%-- 				<img src="<c:url value='/plug-in/_layouts/adminLte/dist/img/user2-160x160.jpg'/>" class="img-circle" alt="User Image" id="log_circle"> --%>
				<img src="<c:url value='/css/bpw/logo2.png'/>" class="img-circle" alt="User Image" id="log_circle"> 
			</div>
			<div class="pull-left info">
				<i class="fa fa-circle text-success"></i> ${user.user_nm} <br><br>
<!-- 				<i class="fa  fa-user margin-r-5"></i><a href="/sks/sks/user/UserReg.do?_idxn=75">정보수정</a> -->
			</div>	
		</div>
      <!-- /.search form -->
	  <c:import url="/base/menu/menuSubTreeList.do?baseMenuNo=${_baseMenuNo}&nextUrl=_layouts/adminLte/inc/sidebarMenu" />
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <%--   title and navi  --%>
    <c:import url="/base/menu/menuNavi.do?nextUrl=_layouts/adminLte/inc/titleAndNavi" />

    <!-- Main content -->
    <section class="content"  id="content">

	<!-- 컨텐츠 -->
	<ax:write divname="contents" />
	<!-- //컨텐츠 -->      

    </section>
    <!-- /.content --> 
  </div>
  <!-- /.content-wrapper -->
  <!-- modal -->
  <div class="modal fade" id="modal">
	  <div class="modal-dialog">
	    <div class="modal-content" >
	           <div class="modal-header">
	             <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	               <span aria-hidden="true">&times;</span></button>
	               <h4 class="modal-title" id="modal-title"></h4>
	           </div>
	           <div class="modal-body"  id="modal-body">
	           </div>
	           <div class="modal-footer">
	             <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	           </div>
	    </div>
	  </div>
    </div> 
	<!-- /.modal -->    
  <!-- search_cd_modal -->
  <div class="modal fade" id="sub-modal">
	  <div class="modal-dialog">
	    <div class="modal-content" >
	           <div class="modal-header">
	             <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	               <span aria-hidden="true">&times;</span></button>
	               <h4 class="modal-title" id="sub-modal-title"></h4>
	           </div>
	           <div class="modal-body"  id="sub-modal-body">
	           </div>
	           <div class="modal-footer">
	             <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	           </div>
	    </div>
	  </div>
    </div> 
	<!-- /.search_cd_modal -->  	
	
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 1.0.0
    </div>
    <strong>Copyright &copy; 2018 <b id="footer_cmpny_nm" name="footer_cmpny_nm"></b></strong> All rights
    reserved.
  </footer>


</div>
<!-- ./wrapper -->


 <%-- 사용자 scripts --%>
 <script type="text/javascript">
			$(function(){	
				$(".select2").select2();
			});
</script>
 <ax:write divname="user_css_script" /> 
<ax:write divname="_x_button_scripts" />
</body>
</html>
