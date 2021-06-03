package ncomegf.bpw.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import ncomegf.bpw.nw.service.ClientListService;

@RestController
@RequestMapping("/bpw/nw")
@Api(value = "클라이언트 정보를 불러온다.")
public class ClientListApiController {

	@Resource
	private ClientListService service;
	
	@ApiOperation(value = "클라이언트 트래픽")
	@PostMapping(value="/clientTraffic")
	public ResponseEntity<ApiDto> clientTraffic(HttpServletRequest request,  @ApiParam(value = "네트워크 id")  @RequestParam String networkId , @ApiParam(value = "timespan")@RequestParam String timespan ) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("networkId",networkId);
		param.put("timespan",timespan);
		param.put("serial",request.getParameterMap().get("serial")[0]);
		DataTable trafic = service.client_traffic(param);
		DataTable usage = service.traffic_usage(param);

		DataRow result = new DataRow();
		result.put("traficList", trafic);
		result.put("usage", usage);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(ClientListApiController.class).clientTraffic(request, networkId, timespan)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "클라이언트 목록")
	@PostMapping(value="/clients_vw_grid")
	public ResponseEntity<ApiDto> clients_vw_grid(HttpServletRequest request, @ApiParam(value = "networkId")  @RequestParam String networkId, @ApiParam(value = "timespan")  @RequestParam String timespan) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("timespan", timespan);
		param.put("networkId", networkId);
		
		DataTable clients = service.clients_vw_grid(param);

		DataRow result = new DataRow();
		result.put("clients", clients);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(ClientListApiController.class).clients_vw_grid(request, networkId, timespan)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "AP목록")
	@PostMapping(value="/wireless_vw")
	public ResponseEntity<ApiDto> wireless_vw(HttpServletRequest request, @ApiParam(value = "networkId")  @RequestParam String networkId, @ApiParam(value = "timespan")  @RequestParam String timespan) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("timespan", timespan);
		param.put("networkId", networkId);
		
		DataTable wireless = service.wireless_vw(param);

		DataRow result = new DataRow();
		result.put("wireless", wireless);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(ClientListApiController.class).wireless_vw(request, networkId, timespan)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
}
