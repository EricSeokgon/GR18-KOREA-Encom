package ncomegf.bpw.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;
import com.ncom.egovweb.annotation.Auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;
import ncomegf.bpw.nw.service.ClientDetailService;
import ncomegf.bpw.nw.service.ClientListService;

@RestController
@RequestMapping("/bpw/nw")
@Api(value = "클라이언트 상세 조회")
public class ClientDetailApiController {

	@Resource
	private ClientDetailService service;
	
	@ApiOperation(value = "클라이언트 상세")
	@PostMapping(value="/client_vw_one")
	public ResponseEntity<ApiDto> client_vw_one(HttpServletRequest request, @ApiParam(value = "networkId")  @RequestParam String networkId, @ApiParam(value = "clientId")  @RequestParam String clientId, @ApiParam(value = "timespan")  @RequestParam String timespan) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("networkId", networkId);
		param.put("clientId", clientId);
		param.put("timespan", timespan);
		
		DataRow client = service.client_vw_one(param);

		DataRow result = new DataRow();
		result.put("client", client);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(ClientDetailApiController.class).client_vw_one(request, networkId, clientId, timespan)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}

	@ApiOperation(value = "클라이언트 상세 트래픽")
	@PostMapping(value="/client_traffic_one")
	public ResponseEntity<ApiDto> client_traffic_one(HttpServletRequest request,  @ApiParam(value = "네트워크 id")  @RequestParam String networkId , @ApiParam(value = "timespan")@RequestParam String timespan, @ApiParam(value = "clientId")@RequestParam String clientId, @ApiParam(value = "serial")  @RequestParam String serial ) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("networkId",networkId);
		param.put("timespan",timespan);
		param.put("clientId",clientId);
		param.put("serial",serial);
		
		DataTable traffic = service.client_traffic(param);
		DataTable usage = service.client_usage(param);

		DataRow result = new DataRow();
		result.put("traffic", traffic);
		result.put("usage", usage);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(ClientDetailApiController.class).client_traffic_one(request, networkId, timespan, clientId, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "네트워크 상세")
	@PostMapping(value="/network_wireless")
	public ResponseEntity<ApiDto> wireless_vw(HttpServletRequest request, @ApiParam(value = "networkId")  @RequestParam String networkId, @ApiParam(value = "serial")  @RequestParam String serial) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("networkId", networkId);
		param.put("serial", serial);
		
		DataRow wireless = service.network_wireless(param);

		DataRow result = new DataRow();
		result.put("wireless", wireless);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(ClientDetailApiController.class).wireless_vw(request, networkId, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
	
}
	
	
