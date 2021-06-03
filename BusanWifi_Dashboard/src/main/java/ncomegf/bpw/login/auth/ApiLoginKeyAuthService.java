package ncomegf.bpw.login.auth;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ncom.core.util.StrUtil;
import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.UserInfo;
import com.ncom.egovweb.inf.AuthInterface;

import ncomegf.base.dao.Session1Dao;

@Service
public class ApiLoginKeyAuthService implements AuthInterface {

	@Resource
	private Session1Dao dao;
	

	@Override
	public boolean isAuth(HttpServletRequest request, String btn_id, boolean isRole) {
						
		return false;
	}



	@Override
	public boolean isAuthKey(HttpServletRequest request) {
		
//		String keyName="Authorization";
//		

		

		
		String user_id="guest";
		String user_nm="손님";
		String author_code="ROLE_GUEST";
		
		UserInfo user =  (UserInfo) request.getSession().getAttribute(UserInfo.SESSION_NAME);
		
		String key= request.getParameter("key");
		
		
		//인증로직.... dao.select.....
		
		
		if(user ==null ||  StrUtil.isEmpty(user.getString("RSV_W_USER_ID") )){
	

	
			DataRow userDataMap = new DataRow();
			userDataMap.put("RSV_W_USER_ID", user_id)	;
			userDataMap.put("RSV_W_USER_NM", user_nm)	;
			userDataMap.put("user_id", user_id)	;
			userDataMap.put("user_nm", user_nm)	;
			userDataMap.put("RSV_W_AUTHOR_CODE", author_code);
			UserInfo userInfo =new UserInfo( userDataMap);	
			request.getSession().setAttribute(userInfo.SESSION_NAME, userInfo);
			return true;
		}		

		return false;
	}


}
