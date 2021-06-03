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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ncomegf.bpw.ap.service.AccessPointConnectionLogService;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;
import ncomegf.bpw.util.DateUtilExt;

@RestController
@RequestMapping("/bpw/ap")
@Api(value = "엑세스 포인트 정보를 불러온다.")
public class AccessPointConnectionLogController {
	
	@Resource
	private AccessPointConnectionLogService service;

	@ApiOperation(value = "AP연결로그 - AP 연결실패 로그 목록을 불러온다.")
	@PostMapping(value = "/connectionLog")
	public ResponseEntity<ApiDto> connectionLog(HttpServletRequest request, DataSet parameter, @ApiParam(value = "네트워크 id")  @RequestParam String networkId ) throws Exception
	{
		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		
		DataTable loglist = service.connection_log(parameter);
		
		DataRow result = new DataRow();
		result.put("logList", loglist);
        
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(AccessPointConnectionLogController.class).connectionLog(request, parameter , networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}

}
