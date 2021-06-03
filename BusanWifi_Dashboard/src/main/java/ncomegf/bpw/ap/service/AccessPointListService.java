package ncomegf.bpw.ap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import ncomegf.base.dao.Session1Dao;
import ncomegf.bpw.util.DateUtilExt;

@Service
public class AccessPointListService
{
	@Resource
	private Session1Dao dao;
	
	public void selectAP(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		DataTable apList = dao.selectDataTable("/bpw/ap/AccessPointList.selectAP", dataMap);
		param.put("apList", apList);
	}
	
	public DataTable selectAccessPointList(DataSet param) throws Exception {
		DataRow dataMap = param.getRow();
		
		String sch_list_Overview = dataMap.getStrDefault("sch_list_Overview", "");
		dataMap.put("lastReportedAt", DateUtilExt.toDateString(DateUtilExt.getJobUnixTimeUTC(Integer.parseInt(sch_list_Overview))));
		
		return dao.selectDataTable("/bpw/ap/AccessPointList.selectAccessPointList", dataMap);
	}
}
