<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	
	function fn_GetConnection_Log(pageNo){
		var data = $("#fmConnectionLog").serializeArray();
		
       	var url = "<c:url value='/bpw/ap/connectionLog'/>";
       	
       	$.post(url, data, function(json){
			json.list= json.result.logList;
			delete json.result.logList;
       		kjs.grid.setList(Connection_Log_grid, json);
    	});
       	
       	
	}
	
	
	function fn_ConnectionLog_NetworkSelect(fm, type){
		fn_NetworkSelect(fm, type);
		fn_GetConnection_Log(1);
	}
	
</script>
<form id="fmConnectionLog" name="fmConnectionLog" method="POST">
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
						<select id="log_networkId" name="networkId" class="form-control networkId" onchange="fn_ConnectionLog_NetworkSelect('fmConnectionLog','log')">
							<option value="N_602356450160904382" selected>부산광역시공공WIFI</option>
							<option value="N_667095694804404023">지하도상가7개소</option>
						</select>
					</div>
					<div class="col-md-2">
						<select id="sch_log_Overview" name="sch_log_Overview" class="form-control" onchange="fn_GetConnection_Log(1)">
							<option value="1">1시간 전</option>
							<option value="2">2시간 전</option>
							<option value="2">6시간 전</option>
							<option value="12" selected>12시간 전</option>
							<option value="24">하루 전</option>
							<option value="48">이틀 전</option>
						</select>
					</div>
					<!-- <div class="col-md-2">
						<select id="sch_log_BAND" name="band" class="form-control" onchange="fn_GetConnection_Log(1)">
							<option value="" selected>2.4 & 5GHz</option>
							<option value="5">5 GHz</option>
							<option value="2.4">2.4 GHz</option>
						</select>
					</div> -->
					<div class="col-md-2">
						<select id="sch_log_AP_TAG1" name="tag1" class="form-control tag1" onchange="fn_GetConnection_Log(1)" disabled="disabled">
							<option value="" disabled selected hidden>AP 장소1</option>
						</select>
					</div>
					<div class="col-md-2">
						<select id="sch_log_AP_TAG2" name="tag2" class="form-control tag2" onchange="fn_GetConnection_Log(1)" disabled="disabled">
							<option value="" disabled selected hidden>AP 장소2</option>
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
		<h4>연결 실패로그</h4>
			<ax:custom customid="grid_${config['mode.grid']}" id="Connection_Log_grid"  page="true" sortable="true" showRowSelector="true"> 
				{key: "occurredAt", label: "연결 시작일시" , align:"center", width:'*' }	    
				,{key: "clientDescription", label: "클라이언트 장치" , align:"center", width:'*' }	    
				,{key: "deviceName", label: "액세스 포인트" , align:"center", width:'*'}	
				,{key: "ssidName", label: "SSID" , align:"center", width:'*'}
				,{key: "description", label: "실패 타입" , align:"center", width:'*'}
				<!-- ,{key: "ff", label: "Failure reason" , align:"center", width:'*'} -->
			</ax:custom>
		</div>
	</div>
</form>