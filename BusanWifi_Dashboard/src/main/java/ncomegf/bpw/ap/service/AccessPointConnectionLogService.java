package ncomegf.bpw.ap.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import ncomegf.base.dao.Session1Dao;
import ncomegf.bpw.util.DateUtilExt;

@Service
public class AccessPointConnectionLogService {

	@Resource
	private Session1Dao dao;

	/**
	 * 
	 * @param param
	 * @throws Exception
	 */
	public DataTable connection_log(DataSet param) throws Exception {
		DataRow dataMap = param.getRow();
		
		String sch_log_Overview = dataMap.getStrDefault("sch_log_Overview", "");
		dataMap.put("occurredAt", DateUtilExt.toDateString(DateUtilExt.getJobUnixTimeUTC(Integer.parseInt(sch_log_Overview))));
		
		DataTable dataTable = dao.selectDataTable("/bpw/ap/AccessPointConnectionLog.connection_log", dataMap);
		
		for (int i = 0 ; i < dataTable.size() ; i++) {
			Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX").parse(dataTable.get(i).getString("occurredAt"));
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
			dataTable.get(i).put("occurredAt",sdf.format(date));
		}
		
		return dataTable;
	}
}