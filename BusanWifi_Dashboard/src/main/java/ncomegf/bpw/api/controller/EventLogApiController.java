package ncomegf.bpw.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncom.core.vo.DataTable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;
import ncomegf.bpw.nw.service.EventLogService;

@RestController
@RequestMapping("/bpw/nw")
@Api(value = "이벤트 로그를 조회 한다.")
public class EventLogApiController {

	@Resource
	private EventLogService service;

	@ApiOperation(value = "이벤트 로그")
	@GetMapping(value = "/eventLogList")
	public ResponseEntity<ApiDto> list(HttpServletRequest request, @ApiParam(value = "네트워크 id") @RequestParam String networkId) throws Exception {

		HashMap map = new HashMap<>(request.getParameterMap());
		ReqDto reqDto = ReqDto.builder().reqUrl(request.getRequestURI()).queryParam(map).build();
		
		int pageSize = 20;
		try {
			pageSize = Integer.parseInt((String)((String[])map.get("pageSize"))[0]);
			
		} catch (Exception e) {
			pageSize = 20;
		}
		
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt((String)((String[])map.get("pageNo"))[0]);
			
		} catch (Exception e) {
			pageNo = 1;
		}
		map.put("networkId", (String)((String[])map.get("networkId"))[0]);
		map.put("clientId", (String)((String[])map.get("clientId"))[0]);
		map.put("deviceName", (String)((String[])map.get("deviceName"))[0]);
		map.put("deviceSerial", (String)((String[])map.get("deviceSerial"))[0]);
		map.put("type", (String)((String[])map.get("type"))[0]);
		map.put("startDt", (String)((String[])map.get("startDt"))[0]);
		map.put("endDt", (String)((String[])map.get("endDt"))[0]);
		
		map.put("pageSize", new Integer(pageSize));
		map.put("pageNo", new Integer(pageNo));
		map.put("pageNoMysql", new Integer((pageNo - 1) * pageSize));
		
		int listCount = service.selectByPagingCnt(map);

		map.put("page", this.setPagingToMap("bs_ax", pageNo, pageSize, listCount));
		DataTable list = service.list(map);
		map.put("list", list);

		ApiDto apiDto = ApiDto.builder().map(map).param(reqDto.getParam()).build();

		Link self = linkTo(methodOn(EventLogApiController.class).list(request, networkId)).withSelfRel();
		apiDto.add(self);

		return new ResponseEntity<ApiDto>(apiDto, HttpStatus.OK);

	}

	public  Map setPagingToMap(String gridKind, int pageNo, int pageSize, int listCount){
		Map pageInfo = new HashMap<String, Object>();
	
		BigDecimal big_listCount = new BigDecimal(listCount);
        BigDecimal big_pageSize = new BigDecimal(pageSize);
        BigDecimal tatalPage = big_listCount.divide(big_pageSize, 0, BigDecimal.ROUND_UP);
        
		if ( "ax".equalsIgnoreCase(gridKind) ) {
			pageInfo.put("pageNo", pageNo);
			pageInfo.put("listCount", listCount);
			pageInfo.put("pageCount", tatalPage);
		} else if ( "bs_ax".equalsIgnoreCase(gridKind) ) {
			pageInfo.put("currentPage", pageNo-1);  //bs_ax에서는 페이지 번호가 0부터 시작.
			pageInfo.put("pageSize", pageSize);
			pageInfo.put("totalElements", listCount);
			pageInfo.put("totalPages", tatalPage);
		}
		
		return pageInfo;
	}
	
}
