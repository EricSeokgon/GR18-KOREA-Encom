<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ax:layout name="${_layout}">
	<ax:div name="user_css_script">
	
		<script src="<c:url value='/plug-in/echarts/dist/echarts.min.js'/>"></script>
		<link rel="stylesheet" href="<c:url value='/css/bpw/style.css'/>" type="text/css" />
		<script type="text/javascript">
			var client_cnt = 0; // 클라이언트 수
			var client_roop_cnt = 0; //클라이언트 리스트 루트 카운트
			var gridC_body = ''; // 문제가 있는 클라리언트 그리드 boby
			
			$(function(){
				setInterval(function(){
					fn_selCurrentClients();
				}, 30000)
				
				setTimeout("fn_selFailSSid()", 1400);
				setTimeout("fn_selSsidStst()", 1600);
				setTimeout("fn_selPreClients()", 1800);
			});
			
			// Current clients list
			function fn_selCurrentClients() {
				var str = "/bpw/api/devices/"+$("#serial").val()+"/clients?timespan=30"
				var url = "<c:url value='"+str+"'/>";
				var data = null;
				$.get(url, data, function(json) {
					var tempList = json.apiDataList[0];
					for (var i in json.apiDataList[0]){
						json.apiDataList[i] = tempList[i];
					} 
					if (json.hasOwnProperty('apiDataList')) {
						json.list= json.apiDataList;
						delete json.apiDataList;
					}
					
					json.list.forEach(function(item){
						fn_current_fail_cnt(item.mac);
					});
					
					if(json.list[0] && json.list[0].length !== 0) {
						client_cnt = json.list.length === 0 ? client_cnt : json.list.length;
					} 
					
					$("#total").html(client_cnt + ' devices');
				});
				
			}
			
			var fail_cnt = 0;
			// 현재 클라이언트 문제있는수
			function fn_current_fail_cnt(clientId) {
				var ssid = $("#ssidNumber").val();
				var failureStep = 'N/A';
				
				var str = '';
				if(ssid) {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/failedConnections?timespan=5&v0=true&ssid="+ssid +"&clientId="+clientId + "&serial=" + $("#serial").val();
				} else {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/failedConnections?timespan=5&v0=true&clientId="+clientId + "&serial=" + $("#serial").val();
				}
				
				var url = "<c:url value='"+str+"'/>";
				
				$.get(url, function(json) {
					var listData = json.apiDataList[0];
					if(listData){
						fail_cnt += listData.length;
					}
					$("#fail_cnt").text(fail_cnt);
				});
			}
			
			
			var all_client_cnts = 0;
			var endCount = 0;
			
			// 지난날 클라리언트 목록
			function fn_selPreClients(){
				var client_usage = 0;
				client_roop_cnt = 0;
				var str = "/bpw/api/devices/"+$("#serial").val()+"/clients?timespan="+$("#timespan").val();
				var url = "<c:url value='"+str+"'/>";
				var data = null;
				$.get(url, data, function(json) {
					var tempList = json.apiDataList[0];
					for (var i in json.apiDataList[0]){
						json.apiDataList[i] = tempList[i];
					} 
					if (json.hasOwnProperty('apiDataList')) {
						json.list= json.apiDataList;
						delete json.apiDataList;
					}
					
					all_client_cnts = json.list.length;
					
					if($("#select_type").val() == "all") {
						endCount = all_client_cnts;
					} else {
						endCount = 5;
					}
					
					
					for(var i=0; i< endCount; i++) {
						var item = json.list[i];
						fn_fail_step(item.mac);
					}
				});
			}
			
			function fn_change_select_type() {
				fn_selPreClients();
				$("#gridC_body").html('<tr class="text-center"><td colspan="4">조회중 입니다.</td></tr>');
			}
			
			// 실패단계
			function fn_fail_step(clientId) {
				var ssid = $("#ssidNumber").val();
				var failureStep = 'N/A';
				
				var str = '';
				if(ssid) {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/failedConnections?timespan="+$("#timespan").val() +"&v0=true&ssid="+ssid +"&clientId="+clientId + "&serial=" + $("#serial").val();
				} else {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/failedConnections?timespan="+$("#timespan").val()+"&v0=true&clientId="+clientId + "&serial=" + $("#serial").val();
				}
				
				var url = "<c:url value='"+str+"'/>";
				
				$.get(url, function(json) {
					var listData = json.apiDataList[0];
					if(listData){
						if(listData.length > 0) {
							failureStep = listData[0].failureStep;
						}
					}
					
					fn_selConnectionStat(clientId, failureStep);
				});
			}
			
			//문제가있는 클라이언트 
			function fn_selConnectionStat(mac, failureStep) {
				var connectCnt = 0;
				var failCnt = 0;
				var failePercnt = 0;
				
				var ssid = $("#ssidNumber").val();
				var str = '';
				
				if(ssid) {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/clients/"+mac+"/connectionStats?timespan="+$("#timespan").val()+"&v0=true&ssid="+ssid;
				} else {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/clients/"+mac+"/connectionStats?timespan="+$("#timespan").val()+"&v0=true";
				}
				var url = "<c:url value='"+str+"'/>";
				
				$.get(url, function(json) {
					client_roop_cnt ++;
					var listData = json.apiDataList[0];
					
					if(listData) {
						var stats = listData.connectionStats;
						if(stats) {
							connectCnt = stats.success + stats.assoc + stats.auth +  stats.dhcp +  stats.dns;
							failCnt = stats.assoc +  stats.auth +  stats.dhcp +  stats.dns;
							failePercnt = ((failCnt / connectCnt)*100).toFixed(1)== 0.000 ? 0: ((failCnt / connectCnt)*100).toFixed(3);
						}
					}
					
					if(failePercnt == 100) {
						gridC_body += '<tr class="text-center"><td>' + mac + '</td><td><div class="StatusCircle statusBad" title="Bad"></div> ' + failePercnt + '%' + '</td><td>' + failCnt + '/' + connectCnt + '</td><td>' + failureStep + '</td></tr>';
					} else if(100 > failePercnt >= 30) {
						gridC_body += '<tr class="text-center"><td>' + mac + '</td><td><div class="StatusCircle statusAlerting" title="Alerting"></div> ' + failePercnt + '%' + '</td><td>' + failCnt + '/' + connectCnt + '</td><td>' + failureStep + '</td></tr>';
					} else {
						gridC_body += '<tr class="text-center"><td>' + mac + '</td><td><div class="StatusCircle statusGood" title="Good"></div> ' + failePercnt + '%' + '</td><td>' + failCnt + '/' + connectCnt + '</td><td>' + failureStep + '</td></tr>';
					}
					
					if (client_roop_cnt == endCount) {
						if(all_client_cnts > 0) {
							$("#client_grid").show();
							$("#_client_msg").text("");
							$("#gridC_body").html(gridC_body);
						} else {
							$("#client_grid").hide();
							$("#_client_msg").text("조회된 데이터가 없습니다.");
						}
					} 
				});
			}
			
			var ssidItem = {"failureStep" : "N/A"};
			
			// SSID 문제 step
			function fn_selFailSSid() {
				var ssid = $("#ssidNumber").val();
				
				var str = "";
				if (ssid) {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/failedConnections?timespan="+$("#timespan").val()+"&v0=true&ssid="+ssid+"&serial="+$("#serial").val();
				} else {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/failedConnections?timespan="+$("#timespan").val()+"&v0=true&serial="+$("#serial").val();
				}
				
				var url = "<c:url value='"+str+"'/>";
				
				$.get(url, function(json) {
					var listData = json.apiDataList[0];
					if(listData){
						if(listData.length > 0) {
							if(listData[0].failureStep == "assoc") ssidItem.failureStep = "Association";
							if(listData[0].failureStep == "dns") ssidItem.failureStep = "DNS";
							if(listData[0].failureStep == "dhcp") ssidItem.failureStep = "DHCP";
							if(listData[0].failureStep == "auth") ssidItem.failureStep = "Authentication";
						}
					}
				});
			}
			
			// SSID 별 문제
			function fn_selSsidStst() {
				var connectCnt = 0;
				var failCnt = 0;
				var failePercnt = 0;
				var gridA_body = "";
				
				
				var gridB_body = "";
				var stepConnectCnt = 0;
				
				var assocPercent = 0;
				var authPercent = 0;
				var dhcpPercent = 0;
				var dnsPercent = 0;
				
				
				var authFailPercent = 0;
				var dhcpFailPercent = 0;
				var dnsFailPercent = 0;
				
				var ssid = $("#ssidNumber").val();
				var str = "";
				if (ssid) {
					str = "/bpw/api/networks/"+$("#networkId").val()+"/devices/"+$("#serial").val()+"/connectionStats?timespan="+$("#timespan").val()+"&v0=true&ssid="+ssid;
				} else {
					 str = "/bpw/api/networks/"+$("#networkId").val()+"/devices/"+$("#serial").val()+"/connectionStats?timespan="+$("#timespan").val()+"&v0=true";
				}
				var url = "<c:url value='"+str+"'/>";
				
				$.get(url, function(json) {
					var listData = json.apiDataList[0];
					
					if(listData) {
						var stats = listData.connectionStats;
						
						connectCnt = stats.success + stats.assoc +  stats.auth +  stats.dhcp +  stats.dns;
						failCnt = stats.assoc +  stats.auth +  stats.dhcp +  stats.dns;
						failePercnt = ((failCnt / connectCnt)*100).toFixed(2);
						
						assocPercent = (stats.assoc / connectCnt)*100;
						authPercent = (stats.auth / connectCnt)*100;
						dhcpPercent = (stats.dhcp / connectCnt)*100;
						dnsPercent = (stats.dns / connectCnt)*100;
						
						authFailPercent = assocPercent + authPercent;
						dhcpFailPercent = authFailPercent + dhcpPercent;
						dnsFailPercent = dhcpFailPercent + dnsPercent;
					}
					
					ssidItem.connectCnt = connectCnt;
					ssidItem.fail_conntCnt = failCnt + '/' + connectCnt;
					ssidItem.failePercnt = failePercnt;
					ssidItem.ssid_name = $("#ssid_nm").text();
					
					if(ssidItem.failePercnt == 100) {
						gridA_body += '<tr class="text-center"><td>' + ssidItem.ssid_name + '</td><td>' + ssidItem.fail_conntCnt + '</td><td><div class="StatusCircle statusBad" title="Bad"></div> ' + ssidItem.failePercnt + '%' + '</td><td>' + ssidItem.failureStep + '</td></tr>';
					} else if(100 > ssidItem.failePercnt >= 30) {
						gridA_body += '<tr class="text-center"><td>' + ssidItem.ssid_name + '</td><td>' + ssidItem.fail_conntCnt + '</td><td><div class="StatusCircle statusAlerting" title="Alerting"></div> ' + ssidItem.failePercnt + '%' + '</td><td>' + ssidItem.failureStep + '</td></tr>';
					} else {
						gridA_body += '<tr class="text-center"><td>' + ssidItem.ssid_name + '</td><td>' + ssidItem.fail_conntCnt + '</td><td><div class="StatusCircle statusGood" title="Good"></div> ' + ssidItem.failePercnt + '%' + '</td><td>' + ssidItem.failureStep + '</td></tr>';
					}
					
					$("#ssid_grid").show();
					$("#gridA_body").html(gridA_body);
					$("#connt_label").text('연결 SSID : ' + $("#ssid_nm").text());
					
					
					
					gridB_body += '<tr class="text-center"><td> Association </td><td>' + assocPercent.toFixed(2) + '% fail to associate' + '</td><td>' + (100-assocPercent).toFixed(2) + '%' + '</td></tr>';
					gridB_body += '<tr class="text-center"><td> Authentication </td><td>' + authFailPercent.toFixed(2) + '% fail to auth' + '</td><td>' + (100-authFailPercent).toFixed(2) + '%' + '</td></tr>';
					gridB_body += '<tr class="text-center"><td> DHCP </td><td>' + dhcpFailPercent.toFixed(2) + '% fail DHCP' + '</td><td>' + (100-dhcpFailPercent).toFixed(2) + '%' + '</td></tr>';
					gridB_body += '<tr class="text-center"><td> DNS </td><td>' + dnsFailPercent.toFixed(2) + '% fail DNS' + '</td><td>' + (100-dnsFailPercent).toFixed(2) + '%' + '</td></tr>';
					gridB_body += '<tr class="text-center"><td> Success </td><td>' + failePercnt + '% fail to pass traffic' + '</td><td>' + (100-failePercnt).toFixed(2) + '%' + '</td></tr>';
					
					
					$("#step_grid").show();
					$("#gridB_body").html(gridB_body);
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
				var frm = document.fmApViewConnection;
				frm.action = url;
				frm.submit();
			}
		</script>
	</ax:div>
	
	<ax:div name="contents">
		<form id="fmApViewConnection" name="fmApViewConnection" method="post">
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
			<input type="hidden" id="ssid" name="ssid">
		
			<div class="row">
				<div class="col-sm-3"> 
					<%@ include file="/WEB-INF/jsp/bpw/ap/AccessPointLeftView.jsp" %>
				</div>                				
				<div class="col-sm-9" >
					<nav>
						<div class="btn-group">
							<a class="btn btn-default" href="#"  onclick="fn_replace('summery');" >요약</a>
							<a class="btn btn-default" href="#" onclick="fn_replace('event');">이벤트 로그</a>
							<a class="btn active" href="#" onclick="fn_replace('connt');">연결</a>
						</div>
					</nav>
	
					<div class="box box-info">
						<div class="box-header with-border" >
							<div class="form-group">
								<h4 id="connt_label">연결 </h4>
							</div>
						</div>
						
						<div class="box-header with-border">
							<h4>전체 연결 통계</h4>
						</div>
						<div class="box-body box-profile" style="text-align: center;">
							<div class="form-group">
								<div class="col-md-12">
									<div class="BigNumberSet ConnectionsStatusCard">
										<div class="BigNumber">
											<div class="BigNumber__label">연결 문제의 영향을받는 클라이언트 장치</div>
											<div class="BigNumber__container">
												<div class="BigNumber__valueWrapper notranslate">
													<span class="HoverHelp">
													<div class="BigNumber__value">
														<span id="fail_cnt">0</span>&nbsp;
														<span class="BigNumberValueOverTotal__Total">/ 
														<span id="total">0 devices</span>
														</span>
													</div>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="box-header with-border" >
							<div class="form-group">
								<h4>SSID 문제</h4>
							</div>
						</div>
						<div class="box-body box-profile" >
							<div id="ssid_grid">
								<div class="card-body table-responsive p-0">
									<table id="tbl_conn" class="table table-hover text-nowrap" style="border:1px solid #ddd" role="grid">
										<colgroup>
											<col width="25%">
											<col width="25%">
											<col width="25%">
											<col width="25%">
										</colgroup>
										<thead>
											<tr class="text-center">
												<th class="text-center">SSID</th>
												<th class="text-center">연결 문제의 영향을받는 클라이언트 수</th>
												<th class="text-center">연결 문제의 영향을 받는 클라이언트 장치 비율(%)</th>
												<th class="text-center">연결 실패 단계</th>
											</tr>
										</thead>
										<tbody id="gridA_body">
											<tr class="text-center">
												<td colspan="4">조회중 입니다.</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						
						<div class="box-header with-border">
							<h4>문제가있는 연결 단계</h4>
						</div>
						<div class="box-body box-profile" >
							<div id="step_grid">
								<div class="card-body table-responsive p-0">
									<table id="tbl_conn" class="table table-hover text-nowrap" style="border:1px solid #ddd" role="grid">
										<colgroup>
											<col width="33%">
											<col width="33%">
											<col width="33%">
										</colgroup>
										<thead>
											<tr class="text-center">
												<th class="text-center">연결 단계</th>
												<th class="text-center">실패 비율</th>
												<th class="text-center">성공 비율</th>
											</tr>
										</thead>
										<tbody id="gridB_body">
											<tr class="text-center">
												<td colspan="3">조회중 입니다.</td>
											</tr>
										</tbody>
									</table>
								</div>
								
							</div>
							<h5 id="_step_msg"></h5>
						</div>
						
						<div class="box-header with-border" >
							<div class="form-group">
								<h4 class="col-md-8">모든 단계에 대한 문제가있는 클라이언트</h4>
								<div class="col-md-4">
									<select id="select_type" name="select_type" class="form-control" onchange="fn_change_select_type();">
										<option value="five" selected>5개만 보기</option>
										<option value="all">전체보기</option>
									</select>
								</div>
							</div>
						</div>
						<div class="box-body box-profile" >
							<div id="client_grid">
								<div class="card-body table-responsive p-0">
									<table id="tbl_conn" class="table table-hover text-nowrap" style="border:1px solid #ddd" role="grid">
										<colgroup>
											<col width="25%">
											<col width="25%">
											<col width="25%">
											<col width="25%">
										</colgroup>
										<thead>
											<tr class="text-center">
												<th class="text-center">클라이언트 장치</th>
												<th class="text-center">연결 실패율(%)</th>
												<th class="text-center">실패한 연결</th>
												<th class="text-center">연결 실패 단계</th>
											</tr>
										</thead>
										<tbody id="gridC_body">
											<tr class="text-center">
												<td colspan="4">조회중 입니다.</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						<h5 id="_client_msg"></h5>
						</div>
				
					</div>
				</div>
			</div>
		</form>
		
	</ax:div>
</ax:layout>
