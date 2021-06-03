package ncomegf.bpw.ap.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;
import com.ncom.egovweb.annotation.Auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ncomegf.bpw.ap.service.AccessPointListService;
import ncomegf.bpw.api.controller.ClientListApiController;
import ncomegf.bpw.api.controller.NetworksApiController;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;
import ncomegf.bpw.api.service.CommonApiService;

@Controller
@RequestMapping("/bpw/ap")
@Api(value = "엑세스 포인트 정보를 불러온다.")
public class AccessPointListController {
	
	@Resource
	private AccessPointListService service;
	
	@Resource
	private CommonApiService apiService;

	@Auth(isLogin = false)
	@RequestMapping(value = "/AccessPointList.do")
	public String AccessPointList(DataSet param, ModelMap model) throws Exception {
		
		model.addAttribute("data", param.getRow());
		return "bpw/ap/AccessPointList";
	}
	
	@RequestMapping(value="/selectAP.json")
	public @ResponseBody String selectAP(DataSet param) throws Exception
	{
		service.selectAP(param);
		
		return param.dataRowToJson();
	}
	
	@ApiOperation(value = "AP목록 - AP목록을 출력한다.")
	@PostMapping(value = "/accessPointList")
	public ResponseEntity<ApiDto> accessPointList(HttpServletRequest request, DataSet parameter, @ApiParam(value = "네트워크 id")  @RequestParam String networkId ) throws Exception
	{
		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(request.getParameterMap()).build();
		
		DataTable list = service.selectAccessPointList(parameter);
		
		DataRow result = new DataRow();
		result.put("list", list);
        
		ApiDto apiDto = ApiDto.builder().result(result).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(AccessPointConnectionLogController.class).connectionLog(request, parameter , networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);
	}

}
