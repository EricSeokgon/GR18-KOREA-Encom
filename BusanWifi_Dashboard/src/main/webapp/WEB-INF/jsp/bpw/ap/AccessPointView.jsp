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
		
		// Current clients list
		function fn_selCurrentClients() {
			var str = "/bpw/api/devices/"+$("#serial").val()+"/clients?timespan=30";
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
				if(json.list[0] && json.list[0].length !== 0) {
					$("#current_clients").text("현재 클라이언트 " + json.list.length);
					$("#grid_div1").show();
					kjs.grid.setList(Client_grid, json);
					$("#_msg1").text('');
				}
			});
			
		}
		
		//과거부터 접속한 클라언트 
		function fn_selPreClients(){
			var client_usage = 0;
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
				
				
				json.list.forEach(function(item){
					client_usage += (item.usage.sent + item.usage.recv);
				});
				
				$("#client_info_label").text('지난날 ' + json.list.length +' 개의 장치가 ' + ((client_usage/1024)/1024).toFixed(2) +'GB를 전송했습니다.');
				if(json.list.length > 0) {
					$("#grid_div2").show();
					kjs.grid.setList(History_client_grid, json);
					$("#_msg2").text('');
				} else {
					$("#grid_div2").hide();
					$("#_msg2").text("조회된 데이터가 없습니다.");
				}
			});
		}
		
		
		function fn_client_traffic() {
			var url = "<c:url value='/bpw/nw/clientTraffic'/>";
			var data = $("#fmApView").serializeArray();
			$.post(url, data, function(json) {
				var list = json.result.traficList;
				
				var chartData = {"ts":[], "usage":[], "json":[]};
				var chartData2 = {"ts":[], "received":[], "sent" : [], "json":[]};
				
				$.each(list, function(index, item) {
					chartData.ts.push(item.showtstime);//chartData.ts.push(item.tstime);
					var sum = (item.received + item.sent)/1024;
					sum = sum/1024;
					chartData.usage.push(sum.toFixed(1));
				});
				
				$.each(list, function(index, item) {
					chartData2.ts.push(item.showtstime);//chartData.ts.push(item.tstime);
					var received = item.received/1024;
					received = received/1024;
					var sent = item.sent/1024;
					sent = sent/1024;
					chartData2.received.push(received.toFixed(1));
					chartData2.sent.push(sent.toFixed(1));
				});
				
				if(chartData.ts.length > 0 && chartData.usage.length > 0)
				{
					clients_traffic_chart(chartData);
				}
				
				if(chartData2.ts.length > 0 && chartData2.received.length > 0 && chartData2.sent.length > 0)
				{
					fn_Usage_Chart(chartData2);
				}
			});
		}
			
		function clients_traffic_chart(chartData) {
			var chart = echarts.init(document.getElementById("clients_traffic_chart"));
			
			var option = {
				title: {
					text:"시간별 사용량",
					left:"left"
				},
				tooltip: {
					trigger: "item",
					formatter: function(param) {
						return param.name + "&nbsp;&nbsp;&nbsp;&nbsp;<b>" + param.data + "</b> Gb/s";
					}
				},
				xAxis: {
					type: "category",
					data:chartData.ts,//["00:00", "02:00","04:00","06:00","08:00","10:00","12:00","14:00","16:00","18:00","20:00","22:00"],//
					boundaryGap: false
				},
				yAxis: {
					type: "value",
					axisLabel: {
						formatter: "{value} Gb/s"
					}
				},
				series: [{
					data: chartData.usage,//[5,10,15,20,14,3],
					type: "line",
					areaStyle: {}
				}]
			};
			chart.setOption(option);
		}
		

		function fn_Usage_Chart(chartData2){
			var chart = echarts.init(document.getElementById("Usage_Chart"));
			
			var option = {
					title: {
						text:"트래픽",
						left:"left"
					},
					tooltip: {
						trigger: "item",
						formatter: function(param) {
							return param.name + "&nbsp;&nbsp;&nbsp;&nbsp;<b>" + param.data + "</b> Gb/s";
						}
					},
					 legend: {
				        data: ['received 데이터', 'sent 데이터']
				    },
					xAxis: {
						type: "category",
						data:chartData2.ts,//["00:00", "02:00","04:00","06:00","08:00","10:00","12:00","14:00","16:00","18:00","20:00","22:00"],//
						boundaryGap: false
					},
					yAxis: {
						type: "value",
						axisLabel: {
							formatter: "{value} Gb/s"
						}
					},
					series: [
						{
							data: chartData2.received,//[5,10,15,20,14,3],
							type: "line",
							areaStyle: {},
							name: "received 데이터"
						},
						{
							data: chartData2.sent,//[5,10,15,20,14,3],
							type: "line",
							areaStyle: {},
							name: "sent 데이터"
						}
					]
				};
			
				chart.setOption(option);
		}
		
		var client_success = 0;
		var client_assoc = 0;
		var client_auth = 0;
		var client_dhcp = 0;
		var client_dns = 0;
		
		//클라리언트 헬스
		function fn_client_health(mac) {
			var str = "/bpw/api/networks/"+$("#networkId").val()+"/clients/"+mac+"/connectionStats?timespan="+$("#timespan").val()+"&v0=true";
			var url = "<c:url value='"+str+"'/>";
			$.get(url, function(json) {
				var listData = json.apiDataList[0];
				
				if(listData) {
					client_success += listData.connectionStats.success;
					client_assoc += listData.connectionStats.assoc;
					client_auth += listData.connectionStats.auth;
					client_dhcp += listData.connectionStats.dhcp;
					client_dns += listData.connectionStats.dns;
				}
			});
		}
		
		
		// 무선 헬스
		function fn_ap_health() {
			var str = "/bpw/api/networks/"+$("#networkId").val()+"/devices/"+$("#serial").val()+"/connectionStats?timespan="+$("#timespan").val()+"&v0=true";
			var url = "<c:url value='"+str+"'/>";
			var sum = 0;
			var success = 100;
			var assoc = 0;
			var auth = 0;
			var dhcp = 0;
			var dns = 0;
			
			$.get(url, function(json) {
				var listData = json.apiDataList[0];
				if(listData) {
					var stats = listData.connectionStats;
					
					sum = stats.assoc + stats.auth + stats.dhcp +stats.dns +stats.success;
					
					success = ((stats.success / sum)*100).toFixed(2);
					assoc = ((stats.assoc / sum)*100).toFixed(2);
					auth = ((stats.auth / sum)*100).toFixed(2);
					dhcp = ((stats.dhcp / sum)*100).toFixed(2);
					dns = ((stats.dns / sum)*100).toFixed(2);
				}
				var chartData = [
						{name : "success" ,value : success}
						,{name : "assoc" ,value : assoc}
						,{name : "auth" ,value : auth}
						,{name : "dhcp" ,value : dhcp}
						,{name : "dns" ,value : dns}
				];
					
				
				
				ap_health_pie_chart(chartData);
				
				$("#assoc_fail").text(assoc + '%');
				$("#auth_fail").text(auth + '%');
				$("#dhcp_fail").text(dhcp + '%');
				$("#dns_fail").text(dns + '%');
				
			});
		}
		
		
		
		function ap_health_pie_chart(chartData) {
			var chart = echarts.init(document.getElementById("ap_health_pie_chart"));
			var colorPalette = ['#67B346', '#F2A200', '#FF8000', '#E64D2E', '#DF0101']; //success, assoc, auth, dhcp, dns
			var option = {
					tooltip: {
						trigger: "item"
					},
					series: [{
						name: "",
						type: "pie",
						radius: ['50%', '70%'],
						avoidLabelOverlap: false,
						itemStyle: {
							borderRadius: 3,
							borderColor: '#fff',
							borderWidth: 1
						},
						label: {
							show: false
						},
						emphasis: {
							label: {
								show: false,
							}
						},
						labelLine: {
							show: false
						},
						color : colorPalette,
						data: chartData
					}]
			}
			chart.setOption(option);
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
			var frm = document.fmApView;
			frm.action = url;
			frm.submit();
		}
		
		
		
		$(function() {
			$("#grid_div1").hide();
			$("#grid_div2").hide();
			setInterval(function(){
				fn_selCurrentClients();
			}, 30000)
			setTimeout("fn_client_traffic()", 1150);
			setTimeout("fn_selPreClients()", 1450);
			setTimeout("fn_ap_health()", 1600);
		});
			
		</script>
	</ax:div>

	<ax:div name="contents">
	<form id="fmApView" name="fmApView" method="post">
		<input type="hidden" id="t0" name="t0" value="1617602066.64">
		<input type="hidden" id="t1" name="t1" value="1617605666.648">
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
						<a class="btn active" href="#"  onclick="fn_replace('summery');" >요약</a>
						<a class="btn btn-default" href="#" onclick="fn_replace('event');">이벤트 로그</a>
						<a class="btn btn-default" href="#" onclick="fn_replace('connt');">연결</a>
					</div>
				</nav>
				<div class="box box-info">
					<div class="box-header with-border">
						<h4>실시간 데이터</h4>
					</div>
					<div  class="box-body box-profile" >
						<div id="Usage_Chart" style="width: 100%; height: 400px;"></div>
					</div>
					
					<div class="box-header with-border">
						<h4 id="current_clients">현재 클라이언트 0</h4>
					</div>
					<div  class="box-body box-profile" >
						<div id= "grid_div1">
							<ax:custom customid="grid_${config['mode.grid']}" id="Client_grid"  page="false" sortable="true"> 
							{key: "description", label: "설명" , align:"left", width:200, formatter: function() {return this.value? this.value :this.item.mac;}}		
							,{key: "ip", label: "IP 주소" , align:"left", width:120	}	
							,{key: "vlan", label: "VLAN" , align:"center", width:100 , formatter: function() {return this.value? this.value :'native';}}
							,{key: "mac", label: "MAC 주소" , align:"left", width:'*'}
							,{key: "usage", label: "사용량" , align:"center", width:100, formatter: function() {return this.value ?(this.value.sent + this.value.recv) + ' KB' : '0 KB';}}
							,{key: "user", label: "사용자" , align:"center", width:100}
							,{key: "switchport", label: "포트" , align:"center", width:'*', formatter: function() {return this.value? this.value :'-';}}
							</ax:custom>
						</div>
						<h5 id="_msg1">현재 연결된 클라이언트가 없습니다.</h5>
					</div>
					<!-- <div class="box-header with-border">
						<h4>반경 및 VLAN 요청 상태</h4>
					</div>
					<div  class="box-body box-profile" >
						<div class="form-group">
								<div class="RadiusAndVlanSummaryLiveSection__content"><div class="BigNumberSet BigNumberSet--compact"><div class="BigNumber BigNumber--compact"><div class="BigNumber__label">DNS</div><div class="BigNumber__container"><div class="BigNumber__statusIcon"><div class="StatusCircle statusGood" title="Good"></div></div><div class="BigNumber__valueWrapper notranslate"><div><div><div class="BigNumber__value BigNumber__value--clickable">OK</div></div></div></div></div></div><div class="BigNumber BigNumber--compact"><div class="BigNumber__label">ARP</div><div class="BigNumber__container"><div class="BigNumber__statusIcon"><div class="StatusCircle statusGood" title="Good"></div></div><div class="BigNumber__valueWrapper notranslate"><div><div><div class="BigNumber__value BigNumber__value--clickable">OK</div></div></div></div></div></div></div></div>
							</div>
					</div>
					 -->
					<div class="box-header with-border">
						<h4>무선 헬스 지난날</h4>
					</div>
					<div  class="box-body box-profile" >
						<div class="HistoricalWirelessHealthStats">
							<div class="ConnectionsSummary HealthSummary">
								<div class="ConnectionsSummaryPieChart__noData">
									<!-- <span><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">선택한 기간에 무선 연결 데이터가 없습니다.</font></font></span> -->
									<div id="ap_health_pie_chart" style="width:100%; height: 400px;"></div>
								</div>
								<div class="ConnectionSteps">
									<div class="MkiSimpleDropoffChart">
										<div class="MkiSimpleDropoffChart__header">
											<font style="vertical-align: inherit;"><font style="vertical-align: inherit;">연결 단계</font></font>
										</div>
										<div class="MkiSimpleDropoffChart__content">
											<div class="MkiSimpleDropoffChart__stages"> 
												<span><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">협회</font></font></span>
												<span><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">입증</font></font></span>
												<span><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">DHCP</font></font></span>
												<span><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">DNS</font></font></span> 
											</div>
											<div class="MkiSimpleDropoffChart__statuses"> 
												<div class="StatusCircles">
													<div class="StatusCircle statusGood" title="좋은"></div>
													<svg width="10" height="20"><line x1="5" y1="0" x2="5" y2="20" stroke="#EFEFEF"></line></svg>
													<div class="StatusCircle statusGood" title="좋은"></div>
													<svg width="10" height="20"><line x1="5" y1="0" x2="5" y2="20" stroke="#EFEFEF"></line></svg>
													<div class="StatusCircle statusGood" title="좋은"></div>
													<svg width="10" height="20"><line x1="5" y1="0" x2="5" y2="20" stroke="#EFEFEF"></line></svg>
													<div class="StatusCircle statusGood" title="좋은"></div>
												</div> 
											</div>
											<!-- success, assoc, auth, dhcp, dns -->
											<div class="MkiSimpleDropoffChart__descriptions"> 
												<span id="assoc_fail"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">0 % 실패</font></font></span>
												<span id="auth_fail"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">0 % 실패</font></font></span>
												<span id="dhcp_fail"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">0 % 실패</font></font></span>
												<span id="dns_fail"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">0 % 실패</font></font></span> 
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="box-header with-border">
						<h4>사용량</h4>
					</div>
					<div  class="box-body box-profile" >
						<div id="clients_traffic_chart" style="width: 100%; height: 400px;"></div>
					</div>
					
					<div class="box-header with-border">
						<h4>클라이언트</h4>
						<label id="client_info_label"></label>
					</div>
					<div  class="box-body box-profile" >
						<div id= "grid_div2">
							<ax:custom customid="grid_${config['mode.grid']}" id="History_client_grid"  page="false" sortable="true"> 
								{key: "description", label: "설명" , align:"left", width:300, formatter: function() {return this.value? this.value :this.item.mac;} }
								,{key: "usage", label: "사용량" , align:"center", width:150, formatter: function() {return this.value ? (this.value.sent + this.value.recv).toFixed(1) + ' KB' : '0 KB';}}
								,{key: "mac", label: "MAC 주소" , align:"left", width:200 }
								,{key: "ip", label: "IP 주소" , align:"left", width:'*'	}	
							</ax:custom>
						</div>
						<h5 id="_msg2"></h5>
					</div>
						</div>
				</div>
			</div>
		</form>
	</ax:div>
</ax:layout>