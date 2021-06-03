<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ax:layout name="${_layout}">
	<ax:div name="user_css_script">
	
	<link rel="stylesheet" href="<c:url value='/css/bpw/style.css'/>" type="text/css" />
	<script type="text/javascript">
		$(function(){
			$("#grid_div").hide();
			setTimeout("fn_selEventLog()", 1000);
		})
		function fn_selEventLog(){
			var url = "<c:url value='/bpw/ap/APEventLog'/>";
			var data = $("#fmApViewEventLog").serializeArray();
			$.post(url, data, function(json) {
				if(json.result.list.length > 0) {
					$("#grid_div").show();
					kjs.grid.setList(APEventLog_grid, json.result);
				} else {
					$("#grid_div").hide();
					$("#_msg").text("조회된 데이터가 없습니다.");
				}
				
			});
		}
		
		function fn_replace(menu) {
			var url = '';
			if (menu == 'summery') {
				url = "<c:url value='/bpw/ap/AccessPointView.do?_idxn=212'/>";
			} else if (menu == 'event') {
				url = "<c:url value='/bpw/ap/AccessPointViewEventLog.do?_idxn=212'/>";
			} else {
				url = "<c:url value='/bpw/ap/AccessPointViewConnection.do?_idxn=212'/>";
			}
			var frm = document.fmApViewEventLog;
			frm.action = url;
			frm.submit();
		}
		
		function fDetail() {
			return '클라이언트 ID : ' + this.item.clientId + ',   SSID : ' + this.item.ssidName;
		}
		
	</script>
	</ax:div>

	<ax:div name="contents">
		<form id="fmApViewEventLog" name="fmApViewEventLog" method="post">
			<input type="hidden" id="t0" name="t0" value="${data.t0 }">
			<input type="hidden" id="t1" name="t1" value="${data.t1 }">
			<input type="hidden" id="timespan" name="timespan" value="86400">
			<input type="hidden" name="serial" id="serial" value="${data.serial}">
			<input type="hidden" name="networkId" id="networkId"  value="${data.networkId}">
			<input type="hidden" name="organizationId" id="organizationId"  value="${data.organizationId}">
			<input type="hidden" id="clientId" name="clientId" value="${data.clientId }">
			<input type="hidden" id="ssidNumber" name="ssidNumber" value="${data.ssidNumber }">
			<input type="hidden" id="lng" name="lng" value="${data.lng }">
			<input type="hidden" id="lat" name="lat" value="${data.lat }">
			
			<div class="row">
				<div class="col-sm-3"> 
					<%@ include file="/WEB-INF/jsp/bpw/ap/AccessPointLeftView.jsp" %>
				</div>                				
				<div class="col-sm-9" >
					<nav>
						<div class="btn-group">
							<a class="btn btn-default" href="#"  onclick="fn_replace('summery');" >요약</a>
							<a class="btn active" href="#" onclick="fn_replace('event');">이벤트 로그</a>
							<a class="btn btn-default" href="#" onclick="fn_replace('connt');">연결</a>
						</div>
					</nav>
		
					<div class="box box-info">
						<div class="box-header with-border">
							<h4>로그</h4>
							<label class="control-label">모든 이벤트</label>
						</div>
						<div class="box-body"> 
							<div id= "grid_div">
								<ax:custom customid="grid_${config['mode.grid']}" id="APEventLog_grid"  page="false" sortable="true"> 
									{key: "occurredAt", label: "시간" , align:"center", width:200}	    
									,{key: "clientDescription", label: "클라이언트" , align:"left", width: 150 }	    
									,{key: "description", label: "이벤트 유형" , align:"center", width:170    }	
									,{key: "", label: "세부정보" , align:"left", width:'*', formatter: fDetail }
								</ax:custom>
							</div>
							<h5 id="_msg"></h5>
						</div>
					</div>
				</div>
			</div>
		</form>
	</ax:div>
</ax:layout>