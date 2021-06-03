package ncomegf.bpw.nw.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import ncomegf.bpw.api.controller.ClientDetailApiController;
import ncomegf.bpw.api.controller.NetworksApiController;
import ncomegf.bpw.nw.service.ClientDetailService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/bpw/nw")
public class ClientDetailController
{
	@Resource
	private ClientDetailService service;
	
	@Resource
	private NetworksApiController network;
	
	@Resource
	private ClientDetailApiController detailApi;
	
	@RequestMapping(value = "/clientDetail.do")
	public String clientDetail(DataSet param, ModelMap model) throws Exception {
		
		model.addAttribute("data", param.getRow());
		return "bpw/nw/clientDetail";
	}

}
