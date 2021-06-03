package ncomegf.bpw.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ncomegf.bpw.ap.service.AccessPointViewService;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;

@RestController
@RequestMapping("/bpw/ap")
@Api(value = "엑세스 포인트 정보를 불러온다.")
public class AccessPointViewApiController {

	@Resource
	private AccessPointViewService service;
	
	@ApiOperation(value = "ap상세 - 해당AP에 대한 모든 이벤트 로그 목록")
	@PostMapping(value="/APEventLog")
	public ResponseEntity<ApiDto> APEventLog(HttpServletRequest request,  @ApiParam(value = "네트워크 id")  @RequestParam String networkId , @ApiParam(value = "시리얼")@RequestParam String serial ) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("networkId",networkId);
		param.put("serial",serial);
		
		DataTable list = service.select_event_log(param);

		DataRow result = new DataRow();
		result.put("list", list);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(AccessPointViewApiController.class).APEventLog(request, networkId, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "ap상세 - AP 현재 상태")
	@GetMapping(value="/networks/{networkId}/APStatus/{serial}")
	public ResponseEntity<ApiDto> APStatus(HttpServletRequest request,  @ApiParam(value = "네트워크 id")  @PathVariable String networkId , @ApiParam(value = "시리얼")@PathVariable String serial ) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("networkId",networkId);
		param.put("serial",serial);
		
		DataRow apStatus = service.select_ap_status(param);

		DataRow result = new DataRow();
		result.put("apStatus", apStatus);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(AccessPointViewApiController.class).APStatus(request, networkId, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "ap상세 - AP의 과거에 접속한 모든 클라이언트 목록")
	@GetMapping(value="/preClients/{serial}")
	public ResponseEntity<ApiDto> preClients(HttpServletRequest request, @ApiParam(value = "시리얼")@PathVariable String serial ) throws Exception
	{

		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		DataSet param = new DataSet();
		param.put("serial",serial);
		
		DataTable list = service.select_pre_clients(param);

		DataRow result = new DataRow();
		result.put("list", list);
		 
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(AccessPointViewApiController.class).preClients(request, serial)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
		
	}
}
