<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<ax:layout name="${_layout}">
	<ax:div name="user_css_script">
		<script src="<c:url value='/plug-in/echarts/dist/echarts.min.js'/>"></script>
		<script type="text/javascript">
			var columns = clients_vw_grid.columns;
			
			$(function() {
				$("#timespan").val("86400");
				$("#networkId").val("N_602356450160904382");
				fn_clients_healthy();
				fn_client_traffic();
				
				$("#networkId").on("change", function() {
					fn_clients_healthy();
					fn_client_traffic();
					fn_clients_vw_grid()
				});
				$("#timespan").on("change", function() {
					fn_clients_healthy();
					fn_clients_vw_grid();
					fn_client_traffic();
				});
			});
			
			// 조회
			function fn_clients_healthy() {
				var url = "<c:url value='/bpw/nw/wireless_vw'/>";
				var data = $("#fmClient").serializeArray();
				$.post(url, data, function(json) {
					var wireless = json.result.wireless;
					var fail = wireless[0].cnt;
					var success = wireless[1].cnt;
					var total = parseInt(success)+parseInt(fail);
					var str = wireless != null ? "<span class='text-green'>" + success + "</span> / " + total : "";
					$("#count").html(str);
				});
			}
			
			function fn_clients_vw_grid() {
				var url = "<c:url value='/bpw/nw/clients_vw_grid'/>";
				$.ajax({
					url: url,
					async: true,
					type: "post",
					data: $("#fmClient").serializeArray(),
					success: function(json) {
						var clients = json.result.clients;
						var list = {"list":clients};
						kjs.grid.setList(clients_vw_grid, list);
						if(clients.length < 1)
						{
							alert("클라이언트가 존재하지 않습니다.");
						}
					}
				});
				/* $.post(url, data, function(json) {
					var clients = json.result.clients;
					var list = {"list":clients};
					kjs.grid.setList(clients_vw_grid, list);
					if(clients.length < 1)
					{
						alert("클라이언트가 존재하지 않습니다.");
					}
					$('#modal-info').modal('hide');
				}); */
				/* var url = "<c:url value='/bpw/api/networks/N_667095694804404023/clients'/>?timespan="+timespan;
				$.get(url, function(json) {
					var clients = "";
					
					var list = json.apiDataList[0];
					$.each(list, function(index, item) {
						var sum = (item.usage.sent + item.usage.recv)/1024;
						item.sum = sum.toFixed(1) + " MB";
						
						clients += item.id + (index+1 != list.length ? "," : "");
					});
					var data = {"list":list};
					kjs.grid.setList(clients_vw_grid, data);
				}); */
			}
			
			function fn_client_traffic() {
				$('#modal-info').modal('show');
				$.ajax({
					url: "<c:url value='/bpw/nw/clientTraffic'/>",
					async: true,
					type: "post",
					data: $("#fmClient").serializeArray(),
					success: function(json) {
						var list = json.result.traficList;
						var chartData = {"ts":[], "usage":[], "json":[]};
						$.each(list, function(index, item) {
							chartData.ts.push(item.showtstime);
							var sum = (item.received + item.sent)/1024;
							sum = sum/1000;
							chartData.usage.push(sum.toFixed(5));
						});
						
						if(chartData.ts.length > 0 && chartData.usage.length > 0)
						{
							clients_chart(chartData);
						}
						
						var usageData = json.result.usage;
						var pieData = [];
						var sumUsage = 0;
						if(usageData.length > 0)
						{
							$.each(usageData, function(index, item) {
								if(index >= 15)
								{
									sumUsage += (item.received + item.sent)/1024;
									if(index+1 == usageData.length)
									{
										sumUsage = sumUsage/1000;
										var pie = {"name":"others", "value":sumUsage.toFixed(5)};
										pieData.push(pie);
									}
								}
								else
								{
									var usage = (item.received + item.sent)/1024;
									usage = usage/1000;
									var pie = {"name":item.application, "value":usage.toFixed(5)};
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
						alert(json.responseText);
					}
				})
				<%-- $.post(url, data, function(json) {
					var list = json.result.traficList;
					var chartData = {"ts":[], "usage":[], "json":[]};
					$.each(list, function(index, item) {
						chartData.ts.push(item.alltstime);//chartData.ts.push(item.tstime);
						var sum = (item.received + item.sent)/1024;
						sum = sum/1000;
						chartData.usage.push(sum);//toFixed(1)
					});
					
					if(chartData.ts.length > 0 && chartData.usage.length > 0)
					{
						clients_chart(chartData);
					}
					
					var usageData = json.result.usage;
					var pieData = [];
					var sumUsage = 0;
					if(usageData.length > 0)
					{
						$.each(usageData, function(index, item) {
							if(index >= 15)
							{
								sumUsage += (item.received + item.sent)/1024;
								if(index+1 == usageData.length)
								{
									var pie = {"name":"others", "value":sumUsage};//.toFixed(1)
									pieData.push(pie);
								}
							}
							else
							{
								var usage = (item.received + item.sent)/1024;
								var pie = {"name":item.application, "value":usage};//.toFixed(1)
								pieData.push(pie);
							}
						});
					}
					else
					{
						pieData.push({"name":"no data", "value":0});
					}
					
					clients_pie_chart(pieData);
				}); --%>
			}
			
			function fn_clientDetail(client) {
				var item = client.item;
				$("#clientId").val(item.id);
				$("#clientMac").val(item.mac);
				$("#mac").val(item.recentDeviceMac);
				$("#ip").val(item.ip);
				$("#ip6").val(item.ip6Local);
				$("#serial").val(item.recentDeviceSerial);
				var url = "<c:url value='/bpw/nw/clientDetail.do?_idxn=244'/>"//&client="+clientId;
				
				var frm = document.fmClient;
				frm.action = url;
				frm.submit();
			}
			
			function clients_chart(chartData) {
				var chart = echarts.init(document.getElementById("clients_chart"));
				
				var option = {
					title: {
						text:"시간별 사용량",
						left:"center"
					},
					responsive: false,
					tooltip: {
						trigger: "item",
						formatter: function(param) {
							return param.name + "&nbsp;&nbsp;&nbsp;&nbsp;<b>" + param.data + "</b> Gb";
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
							formatter: "{value} Gb"
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
						responsive: false,
						tooltip: {
							trigger: "item",
							formatter: function(param) {
								return param.name + "&nbsp;&nbsp;&nbsp;&nbsp;<b>" + param.value + "</b> Gb";
							}
						},
						series: [{
							name: "",
							type: "pie",
							avoidLabelOverlap: false,
							label: {
				                show: false//true
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
	<div class="box box-info">
		
		<form id="fmClient" name="fmClient" method="POST">
			<input type="hidden" id="t0" name="t0" value="${data.t0 }">
			<input type="hidden" id="t1" name="t1" value="${data.t1 }">
			<input type="hidden" id="clientId" name="clientId" value="${data.clientId }">
			<input type="hidden" id="clientMac" name="clientMac" value="${data.clientMac }">
			<input type="hidden" id="mac" name="mac" value="${data.mac }">
			<input type="hidden" id="ip" name="ip" value="${data.ip }">
			<input type="hidden" id="ip6" name="ip6" value="${data.ip6 }">
			<input type="hidden" id="serial" name="serial" value="${data.serial }">
			
			<div class="box-header with-border">
				<div class="form-group">
					<div class="col-md-8">
						<h3>AP 상태 : <span id="count"></span></h3>
					</div>
					<div class="col-md-2" style="padding-top:10px;">
						<select class="form-control" id="networkId" name="networkId" >
							<option value="N_602356450160904382">부산광역시 공공WIFI</option>
							<option value="N_667095694804404023">지하도상가 7개소</option>
						</select>
					</div>
					<div class="col-md-2" style="padding-top:10px;">
						<select id="timespan" name="timespan" class="form-control">
							<option value="7200">지난 2시간</option>
							<option value="86400">지난 1일</option>
							<option value="604800">지난 1주</option>
							<option value="2592000">지난 30일</option>
						</select>
					</div>
				</div>
			</div>
		</form>
		
		<div class="box-body" style="width:100%;display:inline-block;">
			<!-- 차트 -->
			<div class="col-md-12">
				<div id="clients_chart" class="col-md-10" style="height:250px;"></div><!-- width:85%;float:left; -->
				<div id="clients_pie_chart" class="col-md-2" style="height:250px;"></div><!-- width:15%;float:right; -->
			</div>
			
			<ax:custom customid="grid_${config['mode.grid']}" id="clients_vw_grid"  page="true" pageonchange="fn_clients_vw_grid"
			showRowSelector="true" onclickrow="javascript:fn_clientDetail(this)">
				 {key: "status", label: "상태" , align:"center", width:130, sortable:true, 
				 	formatter: function() {
				 		var url = '/images/network/clients/';
				 		var src = this.value == 'Offline' ? url+'cli-wireless-off.png' : url+'cli-wireless-on.png';
				 		return "<img src='"+ src +"' width='15px'/>";
				 	}
				 }
				,{key: "description", label: "기기" , align:"center", width:200, formatter: function() {
						var str = this.value;
						if(this.value == null || this.value == '')
						{
							str = this.item.mac;
						}
						return str;
					}
				 }
				,{key: "mac", label: "mac" , align:"center", width:200 }
				,{key: "tstime", label: "최종 확인" , align:"center", width:200}
				,{key: "os", label: "OS" , align:"center", width:150    }
				,{key: "ip", label: "IPv4 주소" , align:"center", width:170    }
				,{key: "recentDeviceName", label: "AP" , align:"center", width:300    }
				,{key: "ssid", label: "SSID" , align:"center", width:300    }
				<!-- ,{key: "groupPolicy8021x", label: "정책" , align:"center", width:150    } -->
						 
			</ax:custom>
		</div>
	</div>
	
</ax:div>

<%-- <ax:div name="contents">
	<div id="row">
		<div class="box">
			<!-- /.box-body -->
			<div class="box-header with-border">
				<div class="form-group">
					<div class="col-md-9">
						<h2 id="count"></h2>
					</div>
					<div class="col-md-3" style="padding-top:10px;">
						<select class="form-control" id="timespan" name="timespan" onchange="fn_clients_vw_grid()">
							<option value="7200">지난 2시간</option>
							<option value="86400">지난 1일</option>
							<option value="604800">지난 1주</option>
							<option value="2678400">지난 30일</option>
						</select>
					</div>
				</div>
			</div>
			<div class="box-body box-profile">
				<div>
					<div id="clients_chart" style="height:250px;width:85%;float:left;">
						
					</div>
					<div id="clients_pie_chart" style="height:250px;width:15%;float:right;">
					
					</div>
				</div>
				<ax:custom customid="grid_${config['mode.grid']}" id="clients_vw_grid"  page="false" pageonchange="fn_clients_vw_grid"
				onclickrow="" showRowSelector="true">
					 {key: "status", label: "상태" , align:"center", width:130 }
					,{key: "description", label: "설명" , align:"center", width:150 }
					,{key: "lastSeen", label: "최종 확인" , align:"center", width:150, sortable: true       }
					,{key: "usage", label: "사용량" , align:"center", width:150    }
					,{key: "os", label: "OS" , align:"center", width:130    }
					,{key: "ip", label: "IPv4 주소" , align:"center", width:130    }
					,{key: "groupPolicy8021x", label: "정책" , align:"center", width:150    }
							 
				</ax:custom>
			</div>
		</div>
	</div>
</ax:div> --%>
</ax:layout>