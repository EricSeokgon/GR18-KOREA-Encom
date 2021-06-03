package ncomegf.bpw.ap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import ncomegf.base.dao.Session1Dao;

@Service
public class AccessPointViewService
{
	@Resource
	private Session1Dao dao;
	
	public DataTable select_event_log(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		return dao.selectDataTable("/bpw/ap/AccessPointView.select_event_log", dataMap);
	}
	
	
	public DataRow select_ap_status(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		return dao.selectOne("/bpw/ap/AccessPointView.select_ap_status", dataMap);
	}
	
	public DataTable select_pre_clients(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		return dao.selectDataTable("/bpw/ap/AccessPointView.select_pre_clients", dataMap);
	}
}
