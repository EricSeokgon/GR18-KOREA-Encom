package ncomegf.bpw.ap.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.egovweb.annotation.Auth;

import ncomegf.bpw.ap.service.AccessPointViewService;

@Controller
@RequestMapping("/bpw/ap")
public class AccessPointViewController {
	

	@RequestMapping(value = "/AccessPointView.do")
	public String AccessPointView(DataSet param, ModelMap model) throws Exception {
		model.addAttribute("data", param.getRow());
		return "bpw/ap/AccessPointView";
	}
	
	@RequestMapping(value = "/AccessPointViewEventLog.do")
	public String AccessPointViewEventLog(DataSet param, ModelMap model) throws Exception {
		model.addAttribute("data", param.getRow());
		return "bpw/ap/AccessPointViewEventLog";
	}
	

	@RequestMapping(value = "/AccessPointViewConnection.do")
	public String AccessPointViewConnection(DataSet param, ModelMap model) throws Exception {
		model.addAttribute("data", param.getRow());
		return "bpw/ap/AccessPointViewConnection";
	}
	
}
