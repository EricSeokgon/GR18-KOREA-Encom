package ncomegf.bpw.nw.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataTable;

import ncomegf.base.dao.Session1Dao;

@Service
public class EventLogService
{
	@Resource
	private Session1Dao dao;


	public int selectByPagingCnt(Map map) {
		return dao.selectOne("/bpw/nw/EventLog.selectByPagingCnt", map);
	}
	
	public DataTable list(Map map) throws Exception
	{
		
//		String timespan = datama.getStrDefault("timespan", "");
//		if(!timespan.equals(""))
//		{
//			dataMap.put("timespan", Integer.parseInt(timespan));
//		}
		
		return dao.selectDataTable("/bpw/nw/EventLog.selectByPaging", map);
	}
	
	

	
	
	
}
