package ncomegf.bpw.stats.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncom.core.util.Config;
import com.ncom.core.vo.DataSet;
import com.ncom.egovweb.annotation.Auth;

import ncomegf.bpw.analytics.web.TrustedTicket;

@Controller
@RequestMapping("/bpw/stats")
public class ClientStatsController extends TrustedTicket {

	@Auth(isLogin = false)
	@RequestMapping(value = "/clientStats.do")
	public String clientStats(DataSet param, ModelMap model) throws Exception {
		
		String ticketId ="";

		try {
			ticketId = getTrustedTicket(Config.props.getString("bpw.tableau.scheme"), Config.props.getString("bpw.tableau.host"), Config.props.getInt("bpw.tableau.port"), Config.props.getString("bpw.tableau.user"), Config.props.getString("bpw.tableau.client_ip"));
			
		} catch (Exception e) {
			System.out.println("Exception caught: " + e + "\n" + stackTraceToString(e));
		}		
		
		model.addAttribute("ticketId", ticketId);		
		model.addAttribute("host", Config.props.getString("bpw.tableau.ip"));
		model.addAttribute("scheme", Config.props.getString("bpw.tableau.scheme"));
		model.addAttribute("type", Config.props.getString("bpw.tableau.type"));
		
		return "bpw/stats/clientStats";
	}
	
}
