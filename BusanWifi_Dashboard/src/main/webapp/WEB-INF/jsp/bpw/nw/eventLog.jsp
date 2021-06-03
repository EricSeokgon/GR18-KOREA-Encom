<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<ax:layout name="${_layout}">
	<ax:div name="user_css_script">
		<script type="text/javascript">
			function fReset(frm) {
				$('#' + frm).find('input[type=text],input[type=hidden],input[type=number], textarea').val('');
				$('#' + frm).find('select').find('option').attr('selected', false);
				$('#' + frm).find('input[type=checkbox]').attr('checked', false);

			}

			function fListA(pageNo) {

				if (pageNo == 0 && $("#searchFormA #pageNo").val() == "") {
					$("#searchFormA #pageNo").val(1);
				} else if (pageNo > 0) {
					$("#searchFormA #pageNo").val(pageNo);
				}

				var url = "<c:url value='/bpw/nw/eventLogList' />";
				var data = $("#searchFormA").serializeArray();
				$.get(url, data, function(json) {
					kjs.grid.setList(gridA, json.map);
				});

			}
		</script>
	</ax:div>

	<ax:div name="contents">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-primary">
					<form id="searchFormA" name="searchFormA" method="post" class="form-horizontal" onsubmit="">
						<input type="hidden" name="_xLayout" id="_xLayout" value="${_xLayout}">
						<input type="hidden" name="_idxn" id="_idxn" value="${data._idxn}">
						<input type="hidden" name="pageNo" id="pageNo" value="${data.pageNo}">
						<input type="hidden" name="pageSize" id="pageSize" value="20">

						<div class="box collapsed-box">
							<div class="box-header with-border">
								<h3 class="box-title">Search</h3>
								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
										<i class="fa fa-plus"></i>
									</button>
								</div>
							</div>
							<!-- /.box-body -->
							<div class="box-body box-profile">
								<div class="form-group col-sm-6">
									<label class="col-md-4 control-label">네트워크</label>
									<div class="col-md-8">

										<select id="networkId" name="networkId" class="form-control" >
											<option value="N_602356450160904382" selected>부산광역시공공WIFI</option>
											<option value="N_667095694804404023">지하도상가7개소</option>
										</select>
									</div>
								</div>
								<div class="form-group col-sm-6">
									<label class="col-md-4 control-label">클라이언트 ID</label>
									<div class="col-md-8">
										<input name="clientId" id="clientId" value='${data.clientId}' type="text" class="form-control" maxlength="50" />
									</div>
								</div>

								<div class="form-group col-sm-6">
									<label class="col-md-4 control-label">AP명</label>
									<div class="col-md-8">
										<input name="deviceName" id="deviceName" value='${data.deviceName}' type="text" class="form-control" maxlength="30" />
									</div>
								</div>

								<div class="form-group col-sm-6">
									<label class="col-md-4 control-label">AP Serial</label>
									<div class="col-md-8">
										<input name="deviceSerial" id="deviceSerial" value='${data.deviceSerial}' type="text" class="form-control" maxlength="50" />
									</div>
								</div>
								<div class="form-group col-sm-6">
									<label class="col-md-4 control-label">시작일</label>
									<div class="col-md-8">
										<input type="text" ncType="date" name="startDt" value="${result.startDt}" placeholder="YYYY-MM-DD" class="form-control" />
									</div>
								</div>
								<div class="form-group col-sm-6">
									<label class="col-md-4 control-label">종료일</label>
									<div class="col-md-8">
										<input type="text" ncType="date" name="endDt" value="${result.endDt}" placeholder="YYYY-MM-DD" class="form-control" />
									</div>
								</div>
								<div class="form-group col-sm-6">
									<label class="col-md-4 control-label">유형</label>
									<div class="col-md-8">
										<select id="type" name="type" class="form-control""> ﻿
											<option value="">:::: 전체 ::::</option>
											<option value="aps_association_reject">aps_association_reject</option>
											<option value="association">association</option>
											<option value="association_reject">association_reject</option>
											<option value="auto_rf_channel_change">auto_rf_channel_change</option>
											<option value="auto_tx_power_change">auto_tx_power_change</option>
											<option value="carrier_event">carrier_event</option>
											<option value="device_packet_flood">device_packet_flood</option>
											<option value="dfs_event">dfs_event</option>
											<option value="disassociation">disassociation</option>
											<option value="events_dropped2">events_dropped2</option>
											<option value="failsafe_config_suppresseds">failsafe_config_suppresseds</option>

										</select>
									</div>
								</div>
							</div>
							<!-- /.box-footer -->
							<div class="box-footer clearfix">
								<div class="pull-right">
									<ax:button id="vw_search_init" value="초기화" css="btn btn-default" onclick="fReset('searchFormA');"></ax:button>
									<ax:button id="vw_search" value="검색" css="btn btn-info" onclick="fListA(1);"></ax:button>
								</div>
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="box box-info">
					<div class="box-body" style="width: 100%; display: inline-block;">
						<ax:custom customid="grid_${config['mode.grid']}" id="gridA" page="true" pageonchange="fListA" showRowSelector="true" height="650">
				{key: "occurredAt", label: "시간" , align:"center", width:200    }
				,{key: "clientDescription", label: "클라이언트상세" , align:"center", width:200}
				,{key: "clientId", label: "클라이언트ID" , align:"center", width:120    }
				,{key: "description", label: "이벤트유형" , align:"center", width:150    }
				,{key: "deviceName", label: "엑세스포인트명" , align:"center", width:200    }
				,{key: "deviceSerial", label: "Serial" , align:"center", width:150    }
				,{key: "networkId", label: "네트워크ID" , align:"center", width:150    }
				,{key: "ssidName", label: "SSID" , align:"center", width:200    }
<!-- 				,{key: "ssidNumber", label: "SSID No." , align:"center", width:150    } -->
				,{key: "type", label: "유형" , align:"center", width:150    }
			</ax:custom>
					</div>
				</div>
			</div>
		</div>
	</ax:div>

</ax:layout>