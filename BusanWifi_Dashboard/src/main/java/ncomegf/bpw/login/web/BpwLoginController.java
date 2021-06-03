package ncomegf.bpw.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ncom.core.vo.DataSet;
import com.ncom.egovweb.annotation.Auth;

@Controller
@RequestMapping(value = "/bpw/login")
public class BpwLoginController {


	@Auth(isLogin=false)
	@RequestMapping(value = "/Login.do")
	public String Login(DataSet param, ModelMap model) throws Exception {

		model.addAttribute("data", param.getRow());
		return "/bpw/login/Login";
	}	

}