<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function Unix_timestampConv()
	{
	    return Math.floor(new Date().getTime() / 1000);
	}
	
	// 타임스탬프 값을 년월일로 변환
	function Unix_timestamp(t){
	    var date = new Date(t*1000);
	    var year = date.getFullYear();
	    var month = "0" + (date.getMonth()+1);
	    var day = "0" + date.getDate();
	    var hour = "0" + date.getHours();
	    var minute = "0" + date.getMinutes();
	    var second = "0" + date.getSeconds();
	    return year + "-" + month.substr(-2) + "-" + day.substr(-2) + " " + hour.substr(-2) + ":" + minute.substr(-2) + ":" + second.substr(-2);
	}
	
	function fn_AccessPointHealth_NetworkSelect(fm, type){
		fn_NetworkSelect(fm, type);
		fn_HealthClientsConnectionStat();
	}
	
	//ConnectionStat
	function fn_HealthClientsConnectionStat() {
		$('#modal-info').modal('show');
		var timespan = $("#sch_health_Overview").val() * 3600;
		var networkId = $("#health_networkId").val();
		var band = $("#sch_health_BAND").val();
		var apTag = $("#sch_health_AP_TAG1").val();
		
		var a = "/bpw/api/networks/"+networkId+"/clients/connectionStats?timespan="+timespan+"&band="+band+"&apTag="+apTag+"&v0=true";
		var url = "<c:url value='"+a+"'/>";
		$.get(url, function(json) {
			var tempList = json.apiDataList[0];
			var auth=0,success=0,dns=0,assoc=0,dhcp=0,total=0,fail=0;
			var arr=[];
			var ary =[];
			for (var i in tempList){
				assoc = assoc + tempList[i].connectionStats.assoc;
				auth = auth + tempList[i].connectionStats.auth;
				dhcp = dhcp + tempList[i].connectionStats.dhcp;
				dns = dns + tempList[i].connectionStats.dns;
				success = success + tempList[i].connectionStats.success;
				var sum = tempList[i].connectionStats.assoc+tempList[i].connectionStats.auth+tempList[i].connectionStats.dhcp+tempList[i].connectionStats.dns;
				
				if(tempList[i].connectionStats.assoc != '0'|| tempList[i].connectionStats.auth != '0' ||
				   tempList[i].connectionStats.dhcp != '0'|| tempList[i].connectionStats.dns != '0'){
					fail++;
				}
				var percent = sum/tempList[i].connectionStats.success*100;
				if(percent < 100 && percent > 0){
					percent = percent.toFixed(1)+'%';
				}else if(percent > 100 ){
					percent = '100%';
				}else{
					percent = '0%';
				}
				
				var failure_stage='';
				var maxValue = Math.max(tempList[i].connectionStats.assoc, tempList[i].connectionStats.auth, tempList[i].connectionStats.dhcp, tempList[i].connectionStats.dns);
				if(maxValue > 0){
					if(tempList[i].connectionStats.assoc == maxValue){
						failure_stage = 'Association';
					}else if(tempList[i].connectionStats.auth == maxValue){
						failure_stage = 'Authentication';
					}else if(tempList[i].connectionStats.dhcp == maxValue){
						failure_stage = 'DHCP';
					}else{
						failure_stage = 'DNS';
					}
				}
				var data = new Object();
				data.mac = tempList[i].mac;
				data.failed_percent = percent;
				data.failed_connection = sum +"/"+ tempList[i].connectionStats.success;
				data.failure_stage = failure_stage;
				ary.push(data);
			}
			json.list= ary;
			kjs.grid.setList(gridB, json);
			
			total = assoc+auth+dhcp+dns+success;
			
			arr.push(((total-assoc)/total*100).toFixed(1));
			arr.push(((total-auth)/total*100).toFixed(1));
			arr.push(((total-dhcp)/total*100).toFixed(1));
			arr.push(((total-dns)/total*100).toFixed(1));
			arr.push((success/total*100).toFixed(1));
			
			$("#total_connection_devices").html(tempList.length);
			$("#fail_connection_devices").html(fail);
			
			
			fn_Connection_Steps_Chart(arr);
			fn_HealthClientsLatencyStat();
			fn_GetHealthSsidIssue(fail,tempList.length);
			fn_HealthDevicesLatencyStat();
			fn_HealthDevicesConnectionStat();
		});
	}
	
	function fn_HealthDevicesConnectionStat() {
		var timespan = $("#sch_health_Overview").val() * 3600;
		var networkId = $("#health_networkId").val();
		var band = $("#sch_health_BAND").val();
		var apTag = $("#sch_health_AP_TAG1").val();
		
		var a = "/bpw/api/networks/"+networkId+"/devices/connectionStats?timespan="+timespan+"&band="+band+"&apTag="+apTag+"&v0=true";
		var url = "<c:url value='"+a+"'/>";
		$.get(url, function(json) {
			
			var tempList = json.apiDataList[0];
			var auth=0,success=0,dns=0,assoc=0,dhcp=0,total=0,fail=0;
			var ary =[];
			for (var i in json.apiDataList[0]){
				assoc = assoc + tempList[i].connectionStats.assoc;
				auth = auth + tempList[i].connectionStats.auth;
				dhcp = dhcp + tempList[i].connectionStats.dhcp;
				dns = dns + tempList[i].connectionStats.dns;
				success = success + tempList[i].connectionStats.success;
				var sum = tempList[i].connectionStats.assoc+tempList[i].connectionStats.auth+tempList[i].connectionStats.dhcp+tempList[i].connectionStats.dns;
				
				if(tempList[i].connectionStats.assoc != '0'|| tempList[i].connectionStats.auth != '0' ||
				   tempList[i].connectionStats.dhcp != '0'|| tempList[i].connectionStats.dns != '0'){
					fail++;
				}
				var percent = sum/tempList[i].connectionStats.success*100;
				if(percent < 100 && percent > 0){
					percent = percent.toFixed(1)+'%';
				}else if(percent > 100 ){
					percent = '100%';
				}else{
					percent = '0%';
				}
				
				var failure_stage='';
				var maxValue = Math.max(tempList[i].connectionStats.assoc, tempList[i].connectionStats.auth, tempList[i].connectionStats.dhcp, tempList[i].connectionStats.dns);
				if(maxValue > 0){
					if(tempList[i].connectionStats.assoc == maxValue){
						failure_stage = 'Association';
					}else if(tempList[i].connectionStats.auth == maxValue){
						failure_stage = 'Authentication';
					}else if(tempList[i].connectionStats.dhcp == maxValue){
						failure_stage = 'DHCP';
					}else{
						failure_stage = 'DNS';
					}
				}
				var data = new Object();
				data.serial = tempList[i].serial;
				data.failed_percent = percent;
				data.failed_connection = sum +"/"+ tempList[i].connectionStats.success;
				data.failure_stage = failure_stage;
				ary.push(data);
			}
			json.list= ary;
			kjs.grid.setList(gridD, json);

			$('#modal-info').modal('hide');
		});
	}
	
	function fn_HealthClientsLatencyStat(){
		var timespan = $("#sch_health_Overview").val() * 3600;
		var networkId = $("#health_networkId").val();
		var band = $("#sch_health_BAND").val();
		var apTag = $("#sch_health_AP_TAG1").val();
		var ary =[];
		var high = 0;
		var tempList = [];
		var a = "/bpw/api/networks/"+networkId+"/clients/latencyStats?timespan="+timespan+"&band="+band+"&apTag="+apTag+"&v0=true";
		var url = "<c:url value='"+a+"'/>";
		$.get(url, function(json) {
			tempList = json.apiDataList[0];
			for (var i in json.apiDataList[0]){
				var data = new Object();
				
				if(tempList[i].latencyStats.bestEffortTraffic.avg > 200){
					high++;
				}
				
				data.mac = tempList[i].mac;
				data.lateny_avg = tempList[i].latencyStats.bestEffortTraffic.avg;
				ary.push(data);
			}
			
			$("#total_latency_devices").html(tempList.length);
			$("#high_latency_devices").html(high);
			
			json.list= ary;
       		kjs.grid.setList(gridC, json);
    	});
	}
	
	function fn_HealthDevicesLatencyStat(){
		var timespan = $("#sch_health_Overview").val() * 3600;
		var networkId = $("#health_networkId").val();
		var band = $("#sch_health_BAND").val();
		var apTag = $("#sch_health_AP_TAG1").val();
		var ary =[];
		var high = 0;
		var a = "/bpw/api/networks/"+networkId+"/devices/latencyStats?timespan="+timespan+"&band="+band+"&apTag="+apTag+"&v0=true";
		var url = "<c:url value='"+a+"'/>";
		$.get(url, function(json) {
			var tempList = json.apiDataList[0];
			for (var i in json.apiDataList[0]){
				var data = new Object();
				
				data.serial = tempList[i].serial;
				data.lateny_avg = tempList[i].latencyStats.bestEffortTraffic.avg;
				ary.push(data);
			}
			json.list= ary;
       		kjs.grid.setList(gridE, json);
    	});
	}
	
	function fn_GetHealthSsidIssue(fail,total){
		var percent = fail/total*100;
		if(percent < 100 && percent > 0){
			percent = percent.toFixed(1)+'%';
		}else if(percent > 100 ){
			percent = '100%';
		}else{
			percent = '0%';
		}
		
		var data = $("#fmAccessPointHealth").serializeArray();
		
       	var url = "<c:url value='/bpw/ap/ssid_connection_issue'/>";
       	
       	$.post(url, data, function(json){
			json.list= json.result.logList;
			delete json.result.logList;
			json.list[0].fail_count = fail;
			json.list[0].failed_percent = percent;
       		kjs.grid.setList(gridA, json);
    	});
	}
	
	function fn_Connection_Steps_Chart(arr){
		var chartDom = document.getElementById('Connection_Steps_Chart');
		var myChart = echarts.init(chartDom);
		var option;

		option = {
				tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            label: {
			            	backgroundColor: '#bf8f9c'
			            }
			        }
			    },
			    xAxis: {
			        type: 'category',
		            boundaryGap: false,
			        data: ['Association', 'Authentication', 'DHCP', 'DNS', 'Success']
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series: [
			    	{
		            name: '성공률(%)',
		            type: 'line',
		            stack: '总量',
		            areaStyle: {},
		            emphasis: {
		                focus: 'series'
		            },
			        data: arr
			    	}
			    ]
			};

		option && myChart.setOption(option);

	}
</script>

<form id="fmAccessPointHealth" name="fmAccessPointHealth" method="post"  class="form-horizontal"    onsubmit="">
	<div class="box box-primary">
		<!-- /.box-body -->
		<div class="box-header with-border">
			<div class="box-tools pull-right">
				<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse"> <i class="fa fa-minus"></i></button>
			</div>
		</div>

		<div class="box-body box-profile">
			<h4>검색</h4>
			<div>
				<div class="form-group">
					<label class="col-md-2 control-label">검색필터</label>
					
					<div class="col-md-2">
						<select id="health_networkId" name="networkId" class="form-control networkId" onchange="fn_AccessPointHealth_NetworkSelect('fmAccessPointHealth','health')">
							<option value="N_602356450160904382" selected>부산광역시공공WIFI</option>
							<option value="N_667095694804404023">지하도상가7개소</option>
						</select>
					</div>
					<div class="col-md-2"> 
						<select id="sch_health_Overview" name="sch_health_Overview" class="form-control" onchange="fn_HealthClientsConnectionStat()">
							<option value="1" selected>1시간 전</option>
							<option value="2">2시간 전</option>
							<option value="6">6시간 전</option>
							<option value="12">12시간 전</option>
							<option value="24">하루 전</option>
							<option value="48">이틀 전</option>
							<option value="168">일주일 전</option>
						</select>
					</div>
					<div class="col-md-2">
						<select id="sch_health_AP_TAG1" name="tag1" class="form-control tag1" onchange="fn_HealthClientsConnectionStat()" disabled="disabled">
							<option value="" disabled selected hidden>AP 장소1</option>
						</select>
					</div>
					<div class="col-md-2">
						<select id="sch_health_BAND" name="band" class="form-control" onchange="fn_HealthClientsConnectionStat()">
							<option value="" selected>2.4 & 5GHz</option>
							<option value="5">5 GHz</option>
							<option value="2.4">2.4 GHz</option>
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="box box-primary">
		<div class="box-header with-border">
			<div class="box-tools pull-right">
				<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse"> <i class="fa fa-minus"></i></button>
			</div>		 
		</div>
		<div class="box-body box-profile" >
			<div class="col-md-6">
				<div class="BigNumber__label">연결 문제의 영향을 받는 클라이언트 장치</div>
				<div class="BigNumber__container">
					<div class="BigNumber__valueWrapper notranslate">
						<span class="HoverHelp">
						<div class="BigNumber__value">
							<span id="fail_connection_devices"></span>&nbsp;
							<span class="BigNumberValueOverTotal__Total">/ 
							<span><var id="total_connection_devices" data-var="total"></var> <span>devices</span></span>
							</span>
						</div>
						</span>
						<!-- <div class="BigNumber__trend">
							<span class="BigNumber__negativeTrend">↑ 100%</span>
							<span class="BigNumber__trendRange"> from 2 days ago</span>
						</div> -->
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="BigNumber__label">지연율이 높은 클라이언트 장치</div>
				<div class="BigNumber__container">
					<div class="BigNumber__valueWrapper notranslate">
						<span class="HoverHelp">
						<div class="BigNumber__value">
							<span id="high_latency_devices"></span>&nbsp;
							<span class="BigNumberValueOverTotal__Total">/ 
							<span><var id="total_latency_devices" data-var="total"></var> <span>devices</span></span>
							</span>
						</div>
						</span>
						<!-- <div class="BigNumber__trend">
							<span class="BigNumber__positiveTrend">↓ 15%</span>
							<span class="BigNumber__trendRange"> from 5 days ago</span>
						</div> -->
					</div>
				</div>
			</div>
		</div>
		<div class="box-header with-border">
			<h4>실시간 데이터</h4>
		</div>
		<div class="box-body box-profile">
			<div id="Connection_Steps_Chart" style="width: 100%; height: 200px;"></div>
		</div>
		<div class="box-header with-border">
			<h4>SSID에 의한 연결 이슈</h4>
		</div>
		<div  class="box-body box-profile" >
			<ax:custom customid="grid_${config['mode.grid']}" id="gridA"  page="true" sortable="true" showRowSelector="true" height="200"> 
				{key: "ssid", label: "SSID이름" , align:"center", width:'*'}	    
				,{key: "fail_count", label: "연결 문제에 따른 횟수" , align:"center", width:'*' }	    
				,{key: "failed_percent", label: "연결 문제에 따른 백분율" , align:"center", width:'*'}
			</ax:custom>
		</div>
		<div class="box-header with-border">
				<h4>AP에 의한 연결 이슈</h4>
		</div>
		<div  class="box-body box-profile" >
			<ax:custom customid="grid_${config['mode.grid']}" id="gridB"  page="true" sortable="true" showRowSelector="true"> 
				{key: "mac", label: "Mac주소" , align:"center", width:'*'}	    
				,{key: "failed_percent", label: "클라이언트 연결 문제 백분율 " , align:"center", width:'*' }	    
				,{key: "failed_connection", label: "클라이언트 연결 문제  실패/성공" , align:"center", width:'*'}	
				,{key: "failure_stage", label: "주된 실패사유" , align:"center", width:'*'}
			</ax:custom>
		</div>
		<div class="box-header with-border">
			<h4>AP에 따른 높은 AP → 클라이언트 지연율 </h4>
		</div>
		<div  class="box-body box-profile" >
			<ax:custom customid="grid_${config['mode.grid']}" id="gridC"  page="true" sortable="true" showRowSelector="true"> 
				{key: "mac", label: "Mac주소" , align:"center", width:'*'}	    
				,{key: "lateny_avg", label: "평균 지연율(BestEffortTraffic)" , align:"center", width:'*',
					formatter: function() {
						var tst;
						if(this.value < 150){tst='StatusCircleGreen'}
						else if(this.value < 400){tst='StatusCircleYellow'}
						else {tst='StatusCircleRed'};
						return "<div class='" + tst + "' title='status'></div> "+this.value+"ms";
					}
				}
			</ax:custom>
		</div>
		<div class="box-header with-border">
			<h4>클라이언트에 의한 연결 이슈</h4>
		</div>
		<div  class="box-body box-profile" >
			<ax:custom customid="grid_${config['mode.grid']}" id="gridD"  page="true" sortable="true" showRowSelector="true"> 
				{key: "serial", label: "장치시리얼" , align:"center", width:'*'}	    
				,{key: "failed_percent", label: "클라이언트 연결 문제 백분율 " , align:"center", width:'*' }	    
				,{key: "failed_connection", label: "클라이언트 연결 문제  실패/성공" , align:"center", width:'*'}	
				,{key: "failure_stage", label: "주된 실패사유" , align:"center", width:'*'}
			</ax:custom>
		</div>
		<div class="box-header with-border">
			<h4>클라이언트 타입에 의한 높은 AP → 클라이언트 지연율</h4>
		</div>
		<div  class="box-body box-profile" >
			<ax:custom customid="grid_${config['mode.grid']}" id="gridE"  page="true" sortable="true" showRowSelector="true"> 
				{key: "serial", label: "장치시리얼" , align:"center", width:'*'}	    
				,{key: "lateny_avg", label: "평균 지연율(BestEffortTraffic)" , align:"center", width:'*',
					formatter: function() {
						var tst;
						if(this.value < 150){tst='StatusCircleGreen'}
						else if(this.value < 400){tst='StatusCircleYellow'}
						else {tst='StatusCircleRed'};
						return "<div class='" + tst + "' title='status'></div> "+this.value+"ms";
					}
				}
			</ax:custom>
		</div>
	</div>
</form>
