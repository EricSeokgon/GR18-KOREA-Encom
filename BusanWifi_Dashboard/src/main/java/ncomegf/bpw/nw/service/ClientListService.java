package ncomegf.bpw.nw.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import ncomegf.base.dao.Session1Dao;

@Service
public class ClientListService
{
	@Resource
	private Session1Dao dao;

	public DataTable client_traffic(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		String timespan = dataMap.getStrDefault("timespan", "");
		if(!timespan.equals(""))
		{
			dataMap.put("timespan", Integer.parseInt(timespan));
		}
		
		return dao.selectDataTable("/bpw/nw/ClientList.client_traffic", dataMap);
	}
	
	

	public DataTable traffic_usage(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		String timespan = dataMap.getStrDefault("timespan", "");
		if(!timespan.equals(""))
		{
			dataMap.put("timespan", Integer.parseInt(timespan));
		}
	
		return dao.selectDataTable("/bpw/nw/ClientList.traffic_usage", dataMap);
	}
	
	public DataTable clients_vw_grid(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		String timespan = dataMap.getStrDefault("timespan", "");
		if(!timespan.equals(""))
		{
			dataMap.put("timespan", Integer.parseInt(timespan));
		}
	
		return dao.selectDataTable("/bpw/nw/ClientList.clients_vw_grid", dataMap);
	}
	
	public DataTable wireless_vw(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		String timespan = dataMap.getStrDefault("timespan", "");
		if(!timespan.equals(""))
		{
			dataMap.put("timespan", Integer.parseInt(timespan));
		}
	
		return dao.selectDataTable("/bpw/nw/ClientList.wireless_vw", dataMap);
	}
	
}
