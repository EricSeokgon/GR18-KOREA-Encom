<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f371bd0769b75fc1f7adfd8c55f38f74"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f371bd0769b75fc1f7adfd8c55f38f74&libraries=services"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f371bd0769b75fc1f7adfd8c55f38f74&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript">
	

		// 디바이스 정보 조회
		function fn_selDevice() {
			var serial = $("#serial").val();
			
			var url = "<c:url value='/bpw/api/devices/"+serial+"'/>";
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
				
				$(".ap_nm").html('<div class="form-group"><div class="col-md-12"><div class="nameEtc"><h1><div class="StatusCircleGreen" title="green"></div><span class="nodeTitle">' + json.list.name + '</span></h1></div></div></div>');
				$(".ap_model").html('<div class="form-group"> <label class="col-md-2 control-label">' + json.list.model + '</label> '+ '    ' +'<div class="col-md-10"><label>'+ json.list.mac+'</label></div></div>');
				
				$("#ap_serial").text(json.list.serial);
				$("#ap_notes").text(json.list.notes);
				$("#ap_firmware").text(json.list.firmware);
				$("#ap_tags").text(json.list.tags);
				
			});
		}
		
		function fn_selAPStatus(){
			var serial = $("#serial").val();
			var networkId = $("#networkId").val();
			var url = "<c:url value='/bpw/ap/networks/"+ networkId +"/APStatus/"+ serial +"'/>";
			var data = null;
			
			$.get(url, data, function(json) {
				if(json.result.apStatus) {
					if(json.result.apStatus.status == 'offline') {
						$(".ap_nm").html('<div class="form-group"><div class="col-md-12"><div class="nameEtc"><h1><div class="StatusCircleRed" title="red"></div><span class="nodeTitle">' + json.result.apStatus.name + '</span></h1></div></div></div>');
					} else {
						$(".ap_nm").html('<div class="form-group"><div class="col-md-12"><div class="nameEtc"><h1><div class="StatusCircleGreen" title="green"></div><span class="nodeTitle">' + json.result.apStatus.name + '</span></h1></div></div></div>');
					} 
				} 
			});
		}
		
		// ssids
		function fn_selSsids() {
			var networkId = $("#networkId").val();
			
			var url = "<c:url value='/bpw/api/networks/"+networkId+"/events'/>";
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
				
				$("#ssid_nm").text(json.list.events[0].ssidName);
				$("#ssidNumber").val(String(json.list.events[0].ssidNumber));
			});
		}
		
		//RADIO SETTINGS
		function fn_selRadioSetting() {
			var serial = $("#serial").val();
			
			var url = "<c:url value='/bpw/api/devices/"+serial+"/wireless/radio/settings'/>";
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
				
				if(json.list.twoFourGhzSettings.channel && json.list.twoFourGhzSettings.targetPower) {
					$("#radio_set").show();
					$("#radio_sett1").text('2.4GHz: ' + json.list.twoFourGhzSettings.channel + ' (' + json.list.twoFourGhzSettings.targetPower + ' MHz; )');
				} else {
					$("#radio_sett1").text('');
					$("#radio_set").hide();
				}
				if(json.list.fiveGhzSettings.channel && json.list.fiveGhzSettings.targetPower && json.list.fiveGhzSettings.channelWidth) {
					$("#radio_set").show();
					$("#radio_sett2").text('5GHz: ' + json.list.fiveGhzSettings.channel + '(' + json.list.fiveGhzSettings.targetPower + ' MHz; ' + json.list.fiveGhzSettings.channelWidth + ' dBm)');
				} else {
					$("#radio_set").hide();
				}
			});
		}
		
		// Alternate Management Interface (LAN IP, PUBLIC IP, GATEWAY,DNS)
		function fn_selAltMng() {
			var organizationId = $("#organizationId").val();
			
			var url = "<c:url value='/bpw/api/organizations/"+organizationId+"/devices/statuses'/>";
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
				
				var serial = $("#serial").val();
				var networkId = $("#networkId").val();
				
				json.list.forEach(function(item){
					if(item.serial == serial && item.networkId == networkId) {
						$("#alr_mng1").text(item.lanIp? item.lanIp : '(None: AP is a repeater)');
						$("#alr_mng2").text(item.publicIp);
						$("#alr_mng3").text(item.gateway);
						$("#alr_mng4").text(item.primaryDns);
					}
				})
			});
		}
		
		//network wireless settings
		function fn_selApSetting() {
			var networkId = $("#networkId").val();
			
			var url = "<c:url value='/bpw/api/networks/"+networkId+"/wireless/settings'/>";
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
				
				$("#ap_set1").text(json.list.ipv6BridgeEnabled);
				
				
			});
		}
		
		$(function() {
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

			// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
			// marker.setMap(null);
			
			$("#loading_modal").show();
			setTimeout("fn_selDevice()", 0);
			setTimeout("fn_selSsids()", 150);
			setTimeout("fn_selRadioSetting()", 450);
			setTimeout("fn_selAltMng()", 600);
			setTimeout("fn_selApSetting()", 800);
			setTimeout("fn_selAPStatus()", 1000);
		});
			
		</script>

		
				<div class="box box-info">
					<div  class="box-body box-profile" >
						<div class="ap_nm"></div>
						<div class="ap_model"></div>
						
						<div class="form-group">
							<div class="col-md-12" id="map" style="height:215px;"></div>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">SSIDS</label>
							<h5 class="col-md-12" id="ssid_nm"></h5>
						</div>
						
						<div class="form-group">
							<div id="radio_set">
								<label class="col-md-12 control-label">라디오 설정</label>
								<h5 class="col-md-12" id="radio_sett1">2.4GHz:</h5>
								<h5 class="col-md-12" id="radio_sett2">5GHz:</h5>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">LAN IP</label>
							<h5 class="col-md-12" id="alr_mng1"></h5>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">공용 IP</label>
							<h5 class="col-md-12" id="alr_mng2"></h5>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">게이트웨이</label>
							<h5 class="col-md-12" id="alr_mng3"></h5>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">DNS</label>
							<h5 class="col-md-12" id="alr_mng4"></h5>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">LAN IPV6</label>
							<h5 class="col-md-12" id="ap_set1"></h5>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">일련 번호</label>
							<h5 class="col-md-12" id="ap_serial"></h5>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">태그</label>
							<h5 class="col-md-12" id="ap_tags"></h5>
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label">NOTES</label>
							<h5 class="col-md-12" id="ap_notes"></h5>
						</div>
						
						
						<div class="form-group">
							<label class="col-md-12 control-label">방화벽</label>
							<h5 class="col-md-12" id="ap_firmware"></h5>
						</div>
					</div>
				</div>
