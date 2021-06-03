package ncomegf.bpw.ap.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ncomegf.bpw.ap.service.AccessPointHealthService;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;

@RestController
@RequestMapping("/bpw/ap")
@Api(value = "엑세스 포인트 정보를 불러온다.")
public class AccessPointHealthController {
	
	@Resource
	private AccessPointHealthService service;
	
	@ApiOperation(value = "AP헬스 - SSID에 따른 연결 이슈 목록을 불러온다.")
	@PostMapping(value = "/ssid_connection_issue")
	public ResponseEntity<ApiDto> ssid_connection_issue(HttpServletRequest request, DataSet parameter, @ApiParam(value = "네트워크 id")  @RequestParam String networkId ) throws Exception
	{
		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		
		DataTable loglist = service.ssid_connection_issue(parameter);
		
		DataRow result = new DataRow();
		result.put("logList", loglist);
        
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(AccessPointHealthController.class).ssid_connection_issue(request, parameter , networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}

}
