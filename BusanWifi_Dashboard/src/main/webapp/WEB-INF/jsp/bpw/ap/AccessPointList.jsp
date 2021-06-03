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
			$(function() {
				fn_AccessPointList(1);
				$("#Health").hide();
				$("#Connection_log").hide();
				fn_NetworkSelect('fmAccessPointList','list');
				
				fn_GetConnection_Log(1);
			});
			
			
			function fn_AccessPointList(pageNo) {
				var url = "<c:url value='/bpw/ap/accessPointList'/>";
				var data = $("#fmAccessPointList").serializeArray();
				$.post(url, data, function(json){
					var list = json.result.list;
					var online = 0;
					var offline = 0;
					var alerting = 0;
					var unreachable = 0;
					for(var i in list){
						switch(list[i].status){
						case "online" : online = online+1;
							break;
						case "offline" : offline = offline+1;
							break;
						case "alerting" : alerting = alerting+1;
							break;
						case "unreachable" : unreachable = unreachable+1;
							break;
						}
					}
					$(".ONLINE").html(online);
					$(".Offline").html(offline);
					$(".ALERTING").html(alerting);
					$(".UNREACHABLE").html(unreachable);
					
					json.list = json.result.list;
					delete json.result.list;
					
					kjs.grid.setList(AccessPointList_grid, json);
				});
			}
			
			function moveTab(num){
				switch(num){
				case '1':
					$("#List").show();
					$("#Health").hide();
					$("#Connection_log").hide();
					break;
				case '2':
					$("#List").hide();
					$("#Health").show();
					$("#Connection_log").hide();
					fn_NetworkSelect('fmAccessPointHealth','health');
					fn_HealthClientsConnectionStat();
					break;
				case '3':
					$("#List").hide();
					$("#Health").hide();
					$("#Connection_log").show();
					fn_NetworkSelect('fmConnectionLog','log');
					break;
				}
				
			}
			
			function fn_Status_Click(status){
				$("#sch_Status").val(status);
				var url = "<c:url value='/bpw/ap/accessPointList'/>";
				var data = $("#fmAccessPointList").serializeArray();
				$.post(url, data, function(json){
					
					json.list = json.result.list;
					delete json.result.list;
					
					kjs.grid.setList(AccessPointList_grid, json);
				});
			}
			
			//networkid 셀렉트박스 이벤트
			function fn_NetworkSelect(fm, type){
				var data = $("#"+fm+"").serializeArray();
				var url = "<c:url value='/bpw/ap/selectAP.json'/>";
				$.post(url, data, function(json){
					var list = json.apList;
					var ap1=[];
					var ap2=[];
					for(var i in list){
						const arr = list[i].tag.split(",");
						ap1[i] = arr[0];
						ap2[i] = arr[1];
					}
					const set1 = new Set(ap1);
					const uniqueArr1 = [...set1];
					const set2 = new Set(ap2);
					const uniqueArr2 = [...set2];
					
					$("#sch_"+type+"_AP_TAG1").empty();
					$("#sch_"+type+"_AP_TAG1").attr("disabled", false);
					$("#sch_"+type+"_AP_TAG2").empty();
					$("#sch_"+type+"_AP_TAG2").attr("disabled", false);
					
					$("#sch_"+type+"_AP_TAG1").append("<option value=''>전체</option>");
					for(var j in uniqueArr1){
						if(uniqueArr1[j] != ''){
							if(uniqueArr1[j].indexOf("1.") != -1){
								var option = $("<option value='"+uniqueArr1[j]+"'>"+uniqueArr1[j].substring(2,uniqueArr1[j].length)+"</option>");
			                	$("#sch_"+type+"_AP_TAG1").append(option);
							}else{
			                	var option = $("<option value='"+uniqueArr1[j]+"'>"+uniqueArr1[j]+"</option>");
			                	$("#sch_"+type+"_AP_TAG1").append(option);
							}
						}
					}
					$("#sch_"+type+"_AP_TAG2").append("<option value=''>전체</option>");
					for(var k in uniqueArr2){
						if(uniqueArr2[k] != ''){
							if(uniqueArr2[k].indexOf("2.") != -1){
								var option = $("<option value='"+uniqueArr2[k]+"'>"+uniqueArr2[k].substring(2,uniqueArr2[k].length)+"</option>");
			                	$("#sch_"+type+"_AP_TAG2").append(option);
							}else{
								var option = $("<option value='"+uniqueArr2[k]+"'>"+uniqueArr2[k]+"</option>");
				                $("#sch_"+type+"_AP_TAG2").append(option);
							}
		                
						}
					}
		    	});
			}
			
			function fn_AccessPointList_NetworkSelect(fm, type){
				fn_NetworkSelect(fm, type);
				fn_AccessPointList(1);
			}
			
			function fn_AccessPointList_js_row_click(item){
				if( $("#list_networkId").val() == "N_602356450160904382") {
					$("#organizationId").val("602356450160806888");
				} else {
					$("#organizationId").val("896077");
				}
				
				location.href="/bpw/ap/AccessPointView.do?serial="+item.serial+"&networkId="+$("#list_networkId").val()+"&organizationId="+$("#organizationId").val()+"&lat="+item.lat+"&lng="+item.lng+"&_idxn=212";
			}
			
		</script>
	</ax:div>

	<ax:div name="contents">
		<div class="row">
			<div class="col-md-12">
				<!-- Tab 영역 태그는 ul이고 클래스는 nav와 nav-tabs를 설정한다. -->
				<ul class="nav nav-tabs">
					<!-- Tab 아이템이다. 태그는 li과 li > a이다. li태그에 active는 현재 선택되어 있는 탭 메뉴이다. -->
					<li class="active" onclick="moveTab('1');"><a href="#" data-toggle="tab">목록</a></li>
					<!-- a 태그의 href는 아래의 tab-content 영역의 id를 설정하고 data-toggle 속성을 tab으로 설정한다. -->
					<li onclick="moveTab('2');"><a href="#" data-toggle="tab">헬스</a></li>
					<li onclick="moveTab('3');"><a href="#" data-toggle="tab">연결로그</a></li>
				</ul>
				<!-- Tab이 선택되면 내용이 보여지는 영역이다. -->
				<!-- 태그는 div이고 class는 tab-content로 설정한다. -->
				<div class="tab-content">
					<!-- 각 탭이 선택되면 보여지는 내용이다. 태그는 div이고 클래스는 tab-pane이다. -->
					<!-- active 클래스는 현재 선택되어 있는 탭 영역이다. -->
					<div class=" fade in active" id="List">
						<form id="fmAccessPointList" name="fmAccessPointList" method="POST">
						<input type="hidden" id="organizationId" name="organizationId" value="896077">
						<div class="box box-primary">
							<!-- /.box-body -->
							<div class="box-header with-border">
								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse"> <i class="fa fa-minus"></i></button>
								</div>
							</div>
			
							<div class="box-body box-profile">
								<h4>검색</h4>
								<div class="form-group">
									<label class="col-md-2 control-label">검색필터</label>
									<div class="col-md-2">
										<select id="list_networkId" name="networkId" class="form-control networkId" onchange="fn_AccessPointList_NetworkSelect('fmAccessPointList','list')">
											<option value="N_602356450160904382" selected>부산광역시공공WIFI</option>
											<option value="N_667095694804404023">지하도상가7개소</option>
											
										</select>
									</div>
									<div class="col-md-2"> 
										<select id="sch_list_Overview" name="sch_list_Overview" class="form-control" onchange="fn_AccessPointList(1)">
											<option value="2">2시간 전</option>
											<option value="12">12시간 전</option>
											<option value="24">하루 전</option>
											<option value="48" selected>이틀 전</option>
											<option value="168">일주일 전</option>
										</select>
									</div>
									<div class="col-md-2">
										<select id="sch_Status" name="sch_Status" class="form-control" onchange="fn_Status_Click(this.value)">
											<option value="" selected>전체상태</option>
											<option value="alerting">경고</option>
											<option value="unreachable">신호없음</option>
											<option value="offline">오프라인</option>
											<option value="online">온라인</option>
										</select>
									</div>
									<div class="col-md-2">
										<select id="sch_list_AP_TAG1" name="tag1" class="form-control tag1" onchange="fn_AccessPointList(1)" disabled="disabled">
											<option value="" disabled selected hidden>AP 장소1</option>
										</select>
									</div>
									<div class="col-md-2">
										<select id="sch_list_AP_TAG2" name="tag2" class="form-control tag2" onchange="fn_AccessPointList(1)" disabled="disabled">
											<option value="" disabled selected hidden>AP 장소2</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-3">
										<div class="BigNumber">
											<div class="BigNumber__label">오프라인</div>
											<div class="BigNumber__container BigNumber__container--clickable" onclick="fn_Status_Click('offline');">
												<div class="StatusCircleRed" title="red"></div>
												<div class="BigNumber__value Offline">0</div>
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="BigNumber">
											<div class="BigNumber__label">경고</div>
											<div class="BigNumber__container BigNumber__container--clickable" onclick="fn_Status_Click('alerting');">
												<div class="StatusCircleYellow" title="yellow"></div>
												<div class="BigNumber__value ALERTING" >0</div>
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="BigNumber">
											<div class="BigNumber__label">온라인</div>
											<div class="BigNumber__container BigNumber__container--clickable" onclick="fn_Status_Click('online');">
												<div class="StatusCircleGreen" title="green"></div>
												<div class="BigNumber__value ONLINE">0</div>
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="BigNumber">
											<div class="BigNumber__label">신호없음</div>
											<div class="BigNumber__container BigNumber__container--clickable" onclick="fn_Status_Click('unreachable');">
												<div class="StatusCircleGrey" title="grey"></div>
												<div class="BigNumber__value UNREACHABLE">0</div>
											</div>
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
								<ax:custom customid="grid_${config['mode.grid']}" id="AccessPointList_grid"  page="true" sortable="true" showRowSelector="true" onclickrow="fn_AccessPointList_js_row_click"> 
									{key: "status", label: "상태", align:"center", width:80, 
										formatter: function() {
				 							var tst;
				 							if(this.value=='online'){tst='StatusCircleGreen'}
				 							else if(this.value=='offline'){tst='StatusCircleRed'}
				 							else if(this.value=='alerting'){tst='StatusCircleYellow'}
				 							else if(this.value=='unreachable'){tst='StatusCircleGrey'};
				 							return "<div class='" + tst + "' title='status'></div>";
				 						}
				 					}
									,{key: "name", label: "이름" , align:"center", width:150 }	    
									,{key: "mac", label: "MAC주소" , align:"center", width:'*' }	
									,{key: "model", label: "모델" , align:"center", width:150}
									,{key: "serial", label: "시리얼번호" , align:"center", width:'*'}
									,{key: "tags", label: "AP장소" , align:"center", width:'*'}
								</ax:custom>
							</div>
						</div>
						</form>
					</div>
					<!-- id는 고유한 이름으로 설정하고 tab의 href와 연결되어야 한다. -->
					<div class=" fade in" id="Health">
						<%@ include file="/WEB-INF/jsp/bpw/ap/AccessPointHealth.jsp" %>
					</div>
					<!-- fade 클래스는 선택적인 사항으로 트랜지션(transition)효과가 있다.
					<!-- in 클래스는 fade 클래스를 선언하여 트랜지션효과를 사용할 때 in은 active와 선택되어 있는 탭 영역의 설정이다. -->
					<div class=" fade in" id="Connection_log"> 
						<%@ include file="/WEB-INF/jsp/bpw/ap/AccessPointConnectionLog.jsp" %>
					</div>
				</div>
			</div>
		</div>
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
	</ax:div>
</ax:layout>