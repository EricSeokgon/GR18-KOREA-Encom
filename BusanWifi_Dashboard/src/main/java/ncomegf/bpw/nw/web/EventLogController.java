package ncomegf.bpw.nw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncom.core.vo.DataSet;
import com.ncom.egovweb.annotation.Auth;

@Controller
@RequestMapping("/bpw/nw")
public class EventLogController {

	
	@Auth(isLogin = true)
	@RequestMapping(value = "/eventLog.do")
	public String Login(DataSet param, ModelMap model) throws Exception {

		model.addAttribute("data", param.getRow());
		return "bpw/nw/eventLog";
	}

}
