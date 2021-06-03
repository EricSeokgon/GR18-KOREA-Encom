package ncomegf.bpw.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;
import ncomegf.bpw.api.service.CommonApiService;

@RestController
@RequestMapping("/bpw/api")
@Api(value = "APIs 글자....")
public class NetworksApiController {

	@Resource
	private CommonApiService apiService;

	@ApiOperation(value = "클라이언트 상태")
	@GetMapping(value = "/networks/{networkId}/wireless/health/summary")
	public ResponseEntity<ApiDto> wirelessHealthSummary(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_wireless_health_summary";
		
		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("connStats", request.getParameterMap().get("v0")[0]);
		}

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).wirelessHealthSummary(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "클라이언트 목록")
	@GetMapping(value = "/networks/{networkId}/clients")
	public ResponseEntity<ApiDto> clients(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_clients";
		
		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).clients(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "클라이언트 상세")
	@GetMapping(value = "/networks/{networkId}/clients/{clientId}")
	public ResponseEntity<ApiDto> client(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId, @ApiParam(value = "클라이언트 id", required = true) @PathVariable String clientId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_clients_clientid";

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).client(request, networkId, clientId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	/*@ApiOperation(value = "클라이언트 애플리케이션 사용량")
	@GetMapping(value = "/networks/{networkId}/clients/applicationUsage")
	public ResponseEntity<ApiDto> applicationUsage(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_clients_applicationusage";

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).applicationUsage(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "클라이언트 네트워크 사용내역")
	@GetMapping(value = "/networks/{networkId}/clients/usageHistories")
	public ResponseEntity<ApiDto> usageHistory(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_clients_clientid_usagehistory";

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).usageHistory(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}*/
	
	@ApiOperation(value = "AP 목록")
	@GetMapping(value = "/networks/{networkId}/devices")
	public ResponseEntity<ApiDto> devices(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_devices";

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).wirelessHealthSummary(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	

	@ApiOperation(value = "AP 상세")
	@GetMapping(value = "/devices/{serial}")
	public ResponseEntity<ApiDto> device(HttpServletRequest request, @ApiParam(value = "시리얼", required = true) @PathVariable String serial) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_devices";

		ReqDto reqDto = ReqDto.builder().serial(serial).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).wirelessHealthSummary(request, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "ssids 정보")
	@GetMapping(value = "/networks/{networkId}/wireless/ssids")
	public ResponseEntity<ApiDto> ssids(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_ssids";

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).ssids(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "ssid 정보")
	@GetMapping(value = "/networks/{networkId}/wireless/ssids/{number}")
	public ResponseEntity<ApiDto> ssid(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId, @ApiParam(value = "ssid number", required = true) @PathVariable String number) throws ParseException {
		
		String apiKey = "bpwapi_networks_ssid";

		ReqDto reqDto = ReqDto.builder().networkId(networkId) .apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).ssid(request, networkId, number)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "radio 설정 정보")
	@GetMapping(value = "/devices/{serial}/wireless/radio/settings")
	public ResponseEntity<ApiDto> radio(HttpServletRequest request, @ApiParam(value = "시리얼", required = true) @PathVariable String serial) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_devices";

		ReqDto reqDto = ReqDto.builder().serial(serial).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).wirelessHealthSummary(request, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Organization Devices Statuses 정보")
	@GetMapping(value = "/organizations/{organizationId}/devices/statuses")
	public ResponseEntity<ApiDto> alternateManagementInterface(HttpServletRequest request, @ApiParam(value = "조직Id", required = true) @PathVariable String organizationId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_devices";

		ReqDto reqDto = ReqDto.builder().organizationId(organizationId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).wirelessHealthSummary(request, organizationId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Network Wireless Settings 정보")
	@GetMapping(value = "/networks/{networkId}/wireless/settings")
	public ResponseEntity<ApiDto> networkWirelessSettings(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_devices";
		
		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		
		ApiDto apiDto = apiService.select(reqDto);
		
		Link self = linkTo(methodOn(NetworksApiController.class).wirelessHealthSummary(request, networkId)).withSelfRel();
		apiDto.add(self);
		
		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "devices clients")
	@GetMapping(value = "/devices/{serial}/clients")
	public ResponseEntity<ApiDto> deviceClients(HttpServletRequest request, @ApiParam(value = "시리얼", required = true) @PathVariable String serial) throws ParseException {
		
		String apiKey = "bpwapi_device_clients";
		
		HashMap<String, String> map = new HashMap<>();
		
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("t0") != null)
		{
			map.put("t0", request.getParameterMap().get("t0")[0]);
		}

		ReqDto reqDto = ReqDto.builder().serial(serial).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).deviceClients(request, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "장치 연결 동계")
	@GetMapping(value = "/networks/{networkId}/wireless/clients/connectionStats")
	public ResponseEntity<ApiDto> connStaus(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_wireless_connectionstats";
		
		HashMap<String, String> map = new HashMap<>();
		
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).connStaus(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "네트워크 연결 동계")
	@GetMapping(value = "/networks/{networkId}/connectionStats")
	public ResponseEntity<ApiDto> netStaus(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_connectionstats";

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).netStaus(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}

		
	@ApiOperation(value = "클라이언트 연결 상태 통계")
	@GetMapping(value = "/networks/{networkId}/wireless/clients/{clientId}/connectionStats")
	public ResponseEntity<ApiDto> clientConnectionStats(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId, @ApiParam(value = "클라이언트 id", required = true) @PathVariable String clientId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_wireless_clients_connectionStats";

		HashMap<String, String> map = new HashMap<>();
		map.put("timespan", request.getParameterMap().get("timespan")[0]);
		
		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).clientConnectionStats(request, networkId, clientId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	

	@ApiOperation(value = "연결 로그")
	@GetMapping(value = "/networks/{networkId}/events")
	public ResponseEntity<ApiDto> events(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_events";

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).events(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "클라이언트 연결 통계(v0)")
	@GetMapping(value = "/networks/{networkId}/clients/{clientId}/connectionStats")
	public ResponseEntity<ApiDto> connectionStatsV0(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId,@ApiParam(value = "클라이언트 id", required = true) @PathVariable String clientId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkId_wireless_clients_clientId_connStats";

		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("v0", request.getParameterMap().get("v0")[0]);
		}

		if(request.getParameterMap().get("ssid") != null)
		{
			map.put("ssid", request.getParameterMap().get("ssid")[0]);
		}

		
		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).connectionStatsV0(request, networkId, clientId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "장치 무선 연결 통계 얻기")
	@GetMapping(value = "/networks/{networkId}/devices/{serial}/connectionStats")
	public ResponseEntity<ApiDto> deviceHealth(HttpServletRequest request, @ApiParam(value = "시리얼", required = true) @PathVariable String serial, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_devices_health";

		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("v0", request.getParameterMap().get("v0")[0]);
		}
		
		if(request.getParameterMap().get("ssid") != null)
		{
			map.put("ssid", request.getParameterMap().get("ssid")[0]);
		}
		

		ReqDto reqDto = ReqDto.builder().networkId(networkId).serial(serial).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).deviceHealth(request, serial, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	

	@ApiOperation(value = "장치 무선 연결 통계 가져 오기")
	@GetMapping(value = "/devices/{serial}/wireless/connectionStats")
	public ResponseEntity<ApiDto> networkHealth(HttpServletRequest request, @ApiParam(value = "시리얼", required = true) @PathVariable String serial) throws ParseException {
		
		String apiKey = "bpwapi_devices_health";
		
		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("ssid") != null)
		{
			map.put("ssid", request.getParameterMap().get("ssid")[0]);
		}
		

		ReqDto reqDto = ReqDto.builder().serial(serial).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).networkHealth(request, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Network Appliance Vlan 받기")
	@GetMapping(value = "/networks/{networkId}/appliance/vlans")
	public ResponseEntity<ApiDto> vlanStatus(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_network_vlan";
		
		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		
		ApiDto apiDto = apiService.select(reqDto);
		
		Link self = linkTo(methodOn(NetworksApiController.class).vlanStatus(request, networkId)).withSelfRel();
		apiDto.add(self);
		
		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	

	@ApiOperation(value = "Network 연결 실패 받기")
	@GetMapping(value = "/networks/{networkId}/failedConnections")
	public ResponseEntity<ApiDto> nwFailed(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_network_failed";
		
		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("serial") != null)
		{
			map.put("serial", request.getParameterMap().get("serial")[0]);
		}
		
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("v0", request.getParameterMap().get("v0")[0]);
		}
		
		if(request.getParameterMap().get("clientId") != null)
		{
			map.put("clientId", request.getParameterMap().get("clientId")[0]);
		}
		
		if(request.getParameterMap().get("ssid") != null)
		{
			map.put("ssid", request.getParameterMap().get("ssid")[0]);
		}
		
		if(request.getParameterMap().get("band") != null)
		{
			map.put("band", request.getParameterMap().get("band")[0]);
		}
		

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();
		
		ApiDto apiDto = apiService.select(reqDto);
		
		Link self = linkTo(methodOn(NetworksApiController.class).nwFailed(request, networkId)).withSelfRel();
		apiDto.add(self);
		
		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}

	@ApiOperation(value = "클라이언트 연결 실패")
	@GetMapping(value = "/networks/{networkId}/wireless/failedConnections")
	public ResponseEntity<ApiDto> failedConnectionStats(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_wireless_failedConnections";

		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("clientId") != null)
		{
			map.put("clientId", request.getParameterMap().get("clientId")[0]);
		}
		
		if(request.getParameterMap().get("serial") != null)
		{
			map.put("serial", request.getParameterMap().get("serial")[0]);
		}

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).failedConnectionStats(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "디바이스 연결 통계(v0)")
	@GetMapping(value = "/networks/{networkId}/devices/connectionStats")
	public ResponseEntity<ApiDto> devicesConnectionStats(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_devices_connectionStats";

		HashMap<String, String> map = new HashMap<>();
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("v0", request.getParameterMap().get("v0")[0]);
		}
		
		if(request.getParameterMap().get("apTag") != null)
		{
			map.put("apTag", request.getParameterMap().get("apTag")[0]);
		}
		
		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).devicesConnectionStats(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "장치 연결 동계(v0)")
	@GetMapping(value = "/networks/{networkId}/clients/connectionStats")
	public ResponseEntity<ApiDto> connectionStats(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_clients_connectionstats";
		
		HashMap<String, String> map = new HashMap<>();
		
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("apTag") != null)
		{
			map.put("apTag", request.getParameterMap().get("apTag")[0]);
		}
		
		if(request.getParameterMap().get("band") != null)
		{
			map.put("band", request.getParameterMap().get("band")[0]);
		}
		
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("v0", request.getParameterMap().get("v0")[0]);
		}

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).connectionStats(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "클라이언트 지연율 동계(v0)")
	@GetMapping(value = "/networks/{networkId}/clients/latencyStats")
	public ResponseEntity<ApiDto> clientsLatencyStat(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_clients_latencyStats";
		
		HashMap<String, String> map = new HashMap<>();
		
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("apTag") != null)
		{
			map.put("apTag", request.getParameterMap().get("apTag")[0]);
		}
		
		if(request.getParameterMap().get("band") != null)
		{
			map.put("band", request.getParameterMap().get("band")[0]);
		}
		
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("v0", request.getParameterMap().get("v0")[0]);
		}

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).clientsLatencyStat(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}
	
	@ApiOperation(value = "디바이스 지연율 동계(v0)")
	@GetMapping(value = "/networks/{networkId}/devices/latencyStats")
	public ResponseEntity<ApiDto> devicesLatencyStat(HttpServletRequest request, @ApiParam(value = "네트워크 id", required = true) @PathVariable String networkId) throws ParseException {
		
		String apiKey = "bpwapi_networks_networkid_devices_latencyStats";
		
		HashMap<String, String> map = new HashMap<>();
		
		if(request.getParameterMap().get("timespan") != null)
		{
			map.put("timespan", request.getParameterMap().get("timespan")[0]);
		}
		
		if(request.getParameterMap().get("apTag") != null)
		{
			map.put("apTag", request.getParameterMap().get("apTag")[0]);
		}
		
		if(request.getParameterMap().get("band") != null)
		{
			map.put("band", request.getParameterMap().get("band")[0]);
		}
		
		if(request.getParameterMap().get("v0") != null)
		{
			map.put("v0", request.getParameterMap().get("v0")[0]);
		}

		ReqDto reqDto = ReqDto.builder().networkId(networkId).apiKey(apiKey).reqUrl(request.getRequestURI()).queryParam(map).build();

		ApiDto apiDto = apiService.select(reqDto);

		Link self = linkTo(methodOn(NetworksApiController.class).devicesLatencyStat(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}

}
