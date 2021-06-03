package ncomegf.bpw.nw.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;
import com.ncom.egovweb.annotation.Auth;

import io.swagger.annotations.ApiOperation;
import ncomegf.bpw.api.controller.ClientListApiController;
import ncomegf.bpw.api.dto.ApiDto;
import ncomegf.bpw.api.dto.ReqDto;
import ncomegf.bpw.nw.service.ClientListService;

@Controller
@RequestMapping("/bpw/nw")
public class ClientListController {

	@Resource
	private ClientListService service;
	
	@Auth(isLogin = true)
	@RequestMapping(value = "/clientList.do")
	public String Login(DataSet param, ModelMap model) throws Exception {

		model.addAttribute("data", param.getRow());
		return "bpw/nw/clientList";
	}
	
}
