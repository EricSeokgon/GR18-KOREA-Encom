package ncomegf.bpw.ap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ncomegf.base.dao.Session1Dao;
import ncomegf.bpw.util.DateUtilExt;

@Service
public class AccessPointHealthService {

	@Resource
	private Session1Dao dao;
	
	public DataTable ssid_connection_issue(DataSet param) throws Exception {
		DataRow dataMap = param.getRow();
		
		String sch_health_Overview = dataMap.getStrDefault("sch_health_Overview", "");
		dataMap.put("occurredAt", DateUtilExt.toDateString(DateUtilExt.getJobUnixTimeUTC(Integer.parseInt(sch_health_Overview))));
		
		return dao.selectDataTable("/bpw/ap/AccessPointHealth.ssid_connection_issue", dataMap);
	}
}