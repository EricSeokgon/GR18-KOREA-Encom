<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ax:layout name="bpw/basicLayout.jsp">

<ax:div name="user_css_script">
	<style type="text/css">
		.tooltip_template {
			position: absolute;
			display: block;
			top: 55%;
			left:10%;
			width:30%;
		}
	</style>
	<script src="<c:url value='/plug-in/echarts/dist/echarts.min.js'/>"></script>
	<%-- <script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBrVlKgjKPlpGLb33cF5dobtNHYLSEDgXc&callback=initMap&libraries=&v=weekly"
      async
    >
	</script> --%>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f371bd0769b75fc1f7adfd8c55f38f74"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f371bd0769b75fc1f7adfd8c55f38f74&libraries=services"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f371bd0769b75fc1f7adfd8c55f38f74&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript">
		<%--function initMap() {
		    var lat = parseFloat($("#lat").val());
		    var lng = parseFloat($("#lng").val());
		    var p = { lat: lat, lng: lng};
		    var map = new google.maps.Map(document.getElementById("google_map"), {
		     center: p,
		     zoom: 15,
		   });
		    
		 const marker = new google.maps.Marker({
		     position: p,
		     map: map,
		   });
		 }--%>
	
		$(function() {
			$("#timespan").val("86400");
			$("#nav_overview").click();
			$("#tooltip_template_network").hide();
			$("#tooltip_template_conn").hide();
			$(".img_chk").hide();
			
			if($("#clientId").val() == '' || $("#clientId").val() == null)
			{
				//var url = "<c:url value='/bpw/nw/clientList.do'/>?_idxn=243";
				//window.location.href = "<c:url value='/bpw/nw/clientList.do'/>?_idxn=243";
				window.history.back();
			}
			client_view();
			//client_conn_cnt();
			network_wireless();
			client_conn_stats();
			client_conn_fail();
		})
	
		function client_view() {
			//var url = "<c:url value='/bpw/api/networks/"+$("#networkId").val()+"/clients/"+ $("#clientId").val() +"'/>"
			var url = "<c:url value='/bpw/nw/client_vw_one'/>";
			var setData = $("#fmDetail").serializeArray();
			$.post(url, setData, function(json) {
				var data = json.result.client;
				$("#status").html(data.status);
				$("#device").html(data.os);
				
				$(".SSID").html(data.ssid);
				
				var src = "/images/network/clients/";
				var wifiSrc = "";
				var alt = "";
				var chkSrc = "";
				if(data.status == "Offline")
				{
					$("#usageWirelessName").hide();
					tool_conn = "No connection to "+data.ssid;
					chkSrc = src+"cli-noconn-check.png";
					wifiSrc = src+"cli-wireless-off.png";
					alt = "wifi off";
				}
				else
				{
					$("#usageWirelessName").show();
					tool_conn = "Connected to " + data.ssid;
					chkSrc = src+"cli-conn-check.png";
					wifiSrc = src+"cli-wireless-on.png";
					alt = "wifi on";
				}
				$("#tool_conn").html(tool_conn);
				$(".img_chk").attr("src", chkSrc);
				$("#status_img").attr("src", wifiSrc);
				$("#status_img").attr("alt", alt);
				
				
				$("#conn_device").html(data.description);
				//$("#capa").html(data.wirelessCapabilities);
				var scr = data.description == null ? data.mac : data.description;
				$("#description").html(scr);
				
				client_usage(data.status);
			});
		}
		
		function client_usage(status) {
			//var url = "<c:url value='/bpw/nw/client_vw'/>";
			//var data = $("#fmDetail").serializeArray();
			$('#modal-info').modal('show');
			$.ajax({
				url: "<c:url value='/bpw/nw/client_traffic_one'/>",
				async: true,
				type: "post",
				data: $("#fmDetail").serializeArray(),
				success: function(json) {
					var trafficOne = json.result.traffic[0];
					var usage = json.result.usage;
					
					if(status == "online")
					{
						$("#tool_usage").html("Usage On "+trafficOne.recentDeviceName);
					}
					$(".img_chk").show();
					
					var traffic = json.result.traffic
					var chartData = {"ts":[], "usage":[], "json":[]};
					if(traffic.length > 0)
					{
						$.each(traffic, function(index, item) {
							chartData.ts.push(item.showtstime);
							var sum = (item.received + item.sent);
							sum = (sum/1024).toFixed(5);
							chartData.usage.push(sum);
						});
					}
					clients_chart(chartData);
					
					var pieData = [];
					var sumUsage = 0;
					if(usage.length > 0)
					{
						$.each(usage, function(index, item) {
							if(index >= 10)
							{
								sumUsage += (item.received + item.sent);
								if(index+1 == usage.length)
								{
									sumUsage = (sumUsage/1024).toFixed(5);
									var pie = {"name":"others", "value":sumUsage};
									pieData.push(pie);
								}
							}
							else
							{
								var usageCalc = (item.received + item.sent);
								usageCalc = (usageCalc/1024).toFixed(5);
								var pie = {"name":item.application, "value":usageCalc};
								pieData.push(pie);
							}
						});
					}
					else
					{
						pieData.push({"name":"no data", "value":0});
					}
					
					clients_pie_chart(pieData);
					$('#modal-info').modal('hide');
				},
				error: function(json) {
					alert(json);
				}
			});
		}
		
		
		function client_conn_stats()
		{
			var b = "/bpw/api/networks/"+$("#networkId").val()+"/clients/"+$("#clientMac").val()+"/connectionStats?timespan="+$("#timespan").val()+"&v0=true";
			var url = "<c:url value='"+b+"'/>";
			$.get(url, function(json) {
				var data = json.apiDataList[0].connectionStats;
				var fail = 0;
				var total = 0;
				var calc = 0;
				var pers = 0;
				if(data != null)
				{
					fail = data.assoc+data.auth+data.dhcp+data.dns;
					total = fail+data.success;
					calc = fail/total*100;
					pers = 100-calc.toFixed(0);
					
					$("#assocCnt").html(data.assoc);
					$("#authCnt").html(data.auth);
					$("#dhcpCnt").html(data.dhcp);
					$("#dnsCnt").html(data.dns);
					$("#successCnt").html(data.success);
					$("#assocPers").html((data.assoc/total*100).toFixed(0)+"%");
					$("#authPers").html((data.auth/total*100).toFixed(0)+"%");
					$("#dhcpPers").html((data.dhcp/total*100).toFixed(0)+"%");
					$("#dnsPers").html((data.dns/total*100).toFixed(0)+"%");
					$("#successPers").html((data.success/total*100).toFixed(0)+"%");
				}
				
				$(".total_conn").html(total);
				$(".fail_conn").html(fail);
				$("#conn_pers").html(pers + " %");
			});
		}
		
		function client_conn_fail()
		{
			var url = "<c:url value='/bpw/api/networks/"+$("#networkId").val()+"/wireless/failedConnections?timespan="+$("#timespan").val()+"&clientId="+$("#clientMac").val()+"&serial="+$("#serial").val()+"'/>";
			$.get(url, function(json) {
				var data = json.apiDataList[0];
				var str = data.length > 0 ? data[0].type : "N/A";
				$("#fail_type").html(str);
			});
		}
		
		function network_wireless() {
			$("#ipv4Addr").html($("#ip").val());
			$("#ipv6Addr").html($("#ip6").val());
			$("#macAddr").html($("#mac").val());
			
			var url = "<c:url value='/bpw/nw/network_wireless'/>";
			var data = $("#fmDetail").serializeArray();
			$.post(url, data, function(json) {
				var wireless = json.result.wireless;
				$(".wireless_model").html(wireless.model);
				$(".wireless_name").html(wireless.name);
				$("#lat").val(wireless.lat);
				$("#lng").val(wireless.lng);
				
				setMap();
			});
		}
		
		function setMap() {
			var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
			var options = { //지도를 생성할 때 필요한 기본 옵션
				center: new kakao.maps.LatLng($("#lat").val(), $("#lng").val()), //지도의 중심좌표.
				level: 3, //지도의 레벨(확대, 축소 정도)
				marker: marker
			};
			var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
			
			// 마커가 표시될 위치입니다 
			var markerPosition  = new kakao.maps.LatLng($("#lat").val(), $("#lng").val()); 

			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
			    position: markerPosition
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);
		}
	
		function clients_chart(chartData) {
				var chart = echarts.init(document.getElementById("clients_chart"));
				
				var option = {
					title: {
						text:"시간별 사용량",
						left:"center"
					},
					tooltip: {
						trigger: "item",
						formatter: function(param) {
							return param.name + "&nbsp;&nbsp;&nbsp;&nbsp;<b>" + param.data + "</b> "+ " Mb";
						}
					},
					xAxis: {
						type: "category",
						data:chartData.ts,
						boundaryGap: false
					},
					yAxis: {
						type: "value",
						axisLabel: {
							formatter: "{value} Mb"
						}
					},
					series: [{
						data: chartData.usage,
						type: "line",
						areaStyle: {}
					}]
				};
				chart.setOption(option);
			}
			
			
			function clients_pie_chart(chartData) {
				var chart = echarts.init(document.getElementById("clients_pie_chart"));
				
				var option = {
						title: {
							text: "Application",
							left:"center"
						},
						tooltip: {
							trigger: "item",
							formatter: function(param) {
								return param.name + "&nbsp;&nbsp;&nbsp;&nbsp;<b>" + param.value + "</b> " + " Mb";
							}
						},
						series: [{
							name: "",
							type: "pie",
							avoidLabelOverlap: false,
							label: {
				                show: false
				            },
							radius: "50%",
				            data: chartData
						}]
				}
				chart.setOption(option);
			}
	</script>
</ax:div>
	
<ax:div name="contents">
<div class="modal fade" id="modal-info" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 15%; overflow-y: visible; display: none;">
	<div class="modal-dialog modal-m">
		<div class="modal-content">
			<div class="modal-header">
				<h3 style="margin:0;">Loading</h3>
			</div>
			<div class="modal-body">
				<div class="progress progress-striped active" style="margin-bottom:0;">
					<div class="progress-bar" style="width: 100%"></div>
				</div>
			</div>
		</div>
	</div>
</div>
	<!-- <div class="row"> -->
		<div class="box box-info">
			<form id="fmDetail" name="fmDetail" method="POST">
				<%-- <input type="hidden" id="clientId" name="clientId" value="${data.clientId }">
				<input type="hidden" id="networkId" name="networkId" value="${data.networkId }">
				<input type="hidden" id="serial" name="serial" value="${data.serial }">
				<input type="hidden" id="mac" name="mac" value="${data.mac }">
				<input type="hidden" id="ip" name="ip" value="${data.ip }">
				<input type="hidden" id="ip6" name="ip6" value="${data.ip6 }">
				<input type="hidden" id="timespan" name="timespan" value="${data.timespan }">
				<input type="hidden" id="lat" name="lat" value="${data.wireless.lat }">
				<input type="hidden" id="lng" name="lng" value="${data.wireless.lng }"> --%>
				<input type="hidden" id="clientId" name="clientId" value="${data.clientId }">
				<input type="hidden" id="clientMac" name="clientMac" value="${data.clientMac }">
				<input type="hidden" id="networkId" name="networkId" value="${data.networkId }">
				<input type="hidden" id="serial" name="serial" value="${data.serial }">
				<input type="hidden" id="mac" name="mac" value="${data.mac }">
				<input type="hidden" id="ip" name="ip" value="${data.ip }">
				<input type="hidden" id="ip6" name="ip6" value="${data.ip6 }">
				<input type="hidden" id="lat" name="lat" value="">
				<input type="hidden" id="lng" name="lng" value="">
			
				<div class="box-header with-border">
					<div class="form-group">
						<a class="col-md-12" href="<c:url value='/bpw/nw/clientList.do?_idxn=244'/>">클라이언트 목록</a>
						<div class="col-md-10">
							<h1 id="description"></h1>
						</div>
						<div class="col-md-2" style="padding-top:20px;">
							<select id="timespan" name="timespan" class="form-control" onchange="client_view();client_conn_stats();client_conn_fail();">
								<option value="7200">지난 2시간</option>
								<option value="86400">지난 1일</option>
								<option value="604800">지난 1주</option>
								<option value="2592000">지난 30일</option>
							</select>
						</div>
					</div>
				</div>
				
				<div class="box-body">
					<div class="card text-center">
						<div class="card-header">
							<ul class="nav nav-tabs card-header-tabs">
								<li class="nav-item">
									<a class="nav-link active" id="nav_overview" href="#tab_overview" role="tab" data-toggle="pill">개요</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" id="nav_connection" href="#tab_connection" role="tab" data-toggle="pill">연결</a>
								</li>
							</ul>
						</div>
						<div class="card-body">
							<div class="tab-content" id="custom-tabs-three-tabContent">
								<%--개요 --%>
								<div class="tab-pane fade" id="tab_overview" role="tabpanel" aria-labelledby="custom-tabs-three-home-tab">
									<div class="col-md-4" style="padding-top:15px;">
										<div class="form-group col-sm-12 text-right">  
					    				    <label class="col-md-4 control-label">상태</label>
					    				    <div class="col-md-8 text-left">
					    				    	<img id="status_img" alt="" src="/images/network/client/" width="15px" height="12px">
					    				    	&nbsp;&nbsp;  
												<label id="status"></label>			
											</div>
										</div>
										<div class="form-group col-sm-12 text-right"> 
					    				    <label class="col-md-4 control-label">SSID</label>
					    				    <div class="col-md-8 text-left">                       
												<label class="SSID"></label>			
											</div>
										</div>
										<div class="form-group col-sm-12 text-right">
					    				    <label class="col-md-4 control-label">액세스 포인트</label>
					    				    <div class="col-md-8 text-left">                       
												<label class="wireless_model"></label>
											</div>
										</div>
										<!-- <div class="form-group col-sm-12 text-right"> 
					    				    <label class="col-md-4 control-label">스플래시</label>
					    				    <div class="col-md-8 text-left">                       
												<label></label>			
											</div>
										</div> -->
										<div class="form-group col-sm-12 text-right"> 
					    				    <label class="col-md-4 control-label">장치 유형</label>
					    				    <div class="col-md-8 text-left">                       
												<label id="device"></label>			
											</div>
										</div>
										<%-- <div class="form-group col-sm-12 text-right"> 
					    				    <label class="col-md-4 control-label">기능</label>
					    				    <div class="col-md-8 text-left">                       
												<label id="capa"></label>			
											</div>
										</div>
										<div class="form-group col-sm-12 text-right"> 
					    				    <label class="col-md-4 control-label">참고</label>
					    				    <div class="col-md-8 text-left">                       
												<label></label>			
											</div>
										</div>--%>
									</div>
									
									<div class="col-md-8" style="height:215px" id="map">
									</div>
									
									
									<div class="col-md-12">
										<div class="col-md-6">
											<h3 class="text-left">현재 클라이언트 연결</h3>
											<div class="panel panel-default">
												<div class="panel-body" style="height:150px;">
													<div style="width:43%;display:inline-block;float:left;">
														<div class="pull-right">
															<img alt="connection device" src="/images/network/clients/cli-conn-device.png" width="70px" height="70px">
															<br>
															<label id="conn_device"></label>
														</div>
													</div>
													<div  style="width:14%;float:left;padding:0px 20px;" class="text-center" onmouseover="$('#tooltip_template_conn').show();" onmouseout="$('#tooltip_template_conn').hide();" data-placement="bottom" data-toggle="tooltip">
														<div style="border-bottom:2px dashed grey;height:50px;"></div>
														<div>
															<img alt="" src="" width="15px" class="img_chk">
														</div>
													</div>
													<div  style="width:43%;display:inline-block;">
														<div class="pull-left">
															<!-- <div style="height:70px;"> --><img alt="connection wifi" src="/images/network/clients/cli-conn-wifi.png" width="55px" height="55px" style="margin-top:15px;"><!-- </div> -->
															<br>
															<label id="con_tool" class="wireless_name" data-placement="bottom" data-toggle="tooltip" onmouseover="$('#tooltip_template_network').show();" onmouseout="$('#tooltip_template_network').hide();"></label>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<h3 class="text-left">네트워크</h3>
											<div class="panel panel-default">
												<div class="panel-body" style="height:150px;">
													<div class="form-group col-sm-12 text-right"> 
								    				    <label class="col-md-4 control-label">IPv4 address:</label>
								    				    <div class="col-md-8 text-left">                       
															<label>${data.ip }</label>			
														</div>
													</div>
													<div class="form-group col-sm-12 text-right"> 
								    				    <label class="col-md-4 control-label">IPv6 address(link-local):</label>
								    				    <div class="col-md-8 text-left">                       
															<label>${data.ip6 }</label>			
														</div>
													</div>
													<div class="form-group col-sm-12 text-right"> 
								    				    <label class="col-md-4 control-label">MAC address:</label>
								    				    <div class="col-md-8 text-left">                       
															<label>${data.mac }</label>			
														</div>
													</div>
												</div>
											</div>
									</div>
									</div>
									
									<!-- <div class="col-md-12">
										<h3 class="text-left">무선 상태</h3>
										<div class="panel panel-default">
											<div class="panel-body">
												
											</div>
										</div>
									</div> -->
									<div class="col-md-12" id="overview_chart">
											<h3 class="text-left">
												사용량
											</h3>
										<div class="panel panel-default">
											<div class="panel-body">
												<div class="col-md-12" style="padding-top:20px;">
													<div id="clients_chart" style="height:250px;width:85%;float:left;"></div>
													<div id="clients_pie_chart" style="height:250px;width:15%;float:right;"></div>
												</div>
											</div>
										</div>
									</div>
									
								<%-- tooltip
								<div id="tooltip_template_network" class="tooltip_template">
							  		<div class="panel panel-default">
										<div class="panel-body">
											<!-- <div class="form-group col-sm-12"> 
						    				    <label class="col-md-4 control-label">장치 유형</label>
						    				    <div class="col-md-8">                       
													<label id="device_type"></label>
												</div>
											</div>
											<div class="form-group col-sm-12"> 
												<label class="col-md-4 control-label">LAN IP</label>
						    				    <div class="col-md-8">
													<label id="lan_ip"></label>
												</div>
											</div> -->
										</div>
										<div class="panel-body">
											<div class="form-group col-sm-12"> 
						    				    <label class="col-md-4 control-label">usage</label>
						    				    <div class="col-md-8">
													<label id="usage"></label>
												</div>
												<label></label>
											</div>
										</div>
									</div>
								</div> --%>
								
								<%-- tooltip
								<div id="tooltip_template_conn" class="tooltip_template">
							  		<div class="panel panel-default">
										<div class="panel-body">
											<div class="form-group col-sm-12 text-left"> 
												<img alt="" src="" width="15px" class="img_chk">
						    				    <label id="tool_conn"></label>
											</div>
											<div class="form-group col-sm-12 text-left" id="usageWirelessName"> 
												<label>AP : ${data.wireless.name }</label>
											</div>
										</div>
									</div>
								</div> --%>
								
							</div>
							<%--연결 --%>
							<div class="tab-pane fade" id="tab_connection" role="tabpanel" aria-labelledby="custom-tabs-three-profile-tab">
								<%-- <div class="col-md-12">
									<h3 class="text-left">연결</h3>
									<select>
									
									</select>
									<div>
										<h5>SSID</h5>
										<h5>AP</h5>
										<h5>BAND</h5>
									</div>
								</div>--%>
								
								<div class="col-md-12">
									<h3 class="text-left">전체 연결 통계</h3>
									<div class="panel panel-default">
										<div class="panel-body">
											<div class="col-md-4">
												<span>% 연결 성공 비율</span>
												<h2 id="conn_pers"></h2>
											</div>
											<div class="col-md-4">
												<span># 총 연결 수</span>
												<h2 class="total_conn"></h2>
											</div>
											<div class="col-md-4">
												<span># 연결 실패 수</span>
												<h2 class="fail_conn"></h2>
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-md-12">
									<h3 class="text-left">SSID별 문제</h3>
									<div class="card-body table-responsive p-0">
										<table class="table table-hover text-nowrap" style="border:1px solid #ddd">
											<colgroup>
												<col width="26%">
												<col width="37%">
												<col width="37%">
											</colgroup>
											<thead>
												<tr>
													<th>SSID</th>
													<th>연결 실패</th>
													<th>실패 단계</th>
												</tr>
											</thead>
											<tbody>
												<tr class="text-left">
													<td class="SSID"></td>
													<td><span class="fail_conn"></span>/<span class="total_conn"></span></td>
													<td id="fail_type"></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								
								<div class="col-md-12">
									<h3 class="text-left">문제가 있는 연결 단계</h3>
									<div class="card-body table-responsive p-0">
											<table class="table table-bordered">
												<colgroup>
													<col width="40%">
													<col width="15%">
													<col width="15%">
													<col width="15%">
													<col width="15%">
												</colgroup>
												<thead>
													<tr>
														<th rowspan="2" class="text-center" style="vertical-align:middle;">AP</th>
														<th colspan="4" class="text-center">오류</th>
													</tr>
													<tr>
														<th class="text-center">단계</th>
														<th class="text-center">타입</th>
														<th class="text-center">횟수</th>
														<th class="text-center">비율</th>
													</tr>
												</thead>
												<tbody>
													<tr class="text-center">
														<td class="SSID" rowspan="5" style="vertical-align:middle;"></td>
														<td>assoc</td>
														<td>연결 시도</td>
														<td><label id="assocCnt" class="text-red">0</label></td>
														<td id="assocPers">0%</td>
													</tr>
													<tr class="text-center">
														<td>auth</td>
														<td>인증 실패</td>
														<td><label id="authCnt" class="text-red">0</label></td>
														<td id="authPers">0%</td>
													</tr>
													<tr class="text-center">
														<td>DHCP</td>
														<td>DHCP</td>
														<td><label id="dhcpCnt" class="text-red">0</label></td>
														<td id="dhcpPers">0%</td>
													</tr>
													<tr class="text-center">
														<td>DNS</td>
														<td>DNS</td>
														<td><label id="dnsCnt" class="text-red">0</label></td>
														<td id="dnsPers">0%</td>
													</tr>
													<tr class="text-center">
														<td>success</td>
														<td>성공</td>
														<td><label id="successCnt" class="text-green">0</label></td>
														<td id="successPers">0%</td>
													</tr>
												</tbody>
											</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div id="modal">
		<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 15%; overflow-y: visible; display: none;"><div class="modal-dialog modal-m"><div class="modal-content"><div class="modal-header"><h3 style="margin:0;">Loading</h3></div><div class="modal-body"><div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div></div></div></div></div>
	</div>
</ax:div>
	
</ax:layout>