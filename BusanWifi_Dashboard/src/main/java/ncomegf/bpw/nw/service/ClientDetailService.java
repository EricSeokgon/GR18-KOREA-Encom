package ncomegf.bpw.nw.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;
import com.ncom.core.vo.DataSet;
import com.ncom.core.vo.DataTable;

import ncomegf.base.dao.Session1Dao;

@Service
public class ClientDetailService
{
	@Resource
	private Session1Dao dao;
	
	/**
	 * 
	 * @param param
	 * @throws Exception
	 */
	public DataRow client_vw_one(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		DataRow client = dao.selectOne("/bpw/nw/ClientDetail.client_vw_one", dataMap);
		return client;
	}
	
	/**
	 * 
	 * @param param
	 * @throws Exception
	 */
	public DataTable client_traffic(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		DataTable traffic = dao.selectDataTable("/bpw/nw/ClientDetail.client_traffic", dataMap);
		return traffic;
	}
	
	/**
	 * 
	 * @param param
	 * @throws Exception
	 */
	public DataTable client_usage(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		DataTable usage = dao.selectDataTable("/bpw/nw/ClientDetail.traffic_usage", dataMap);
		return usage;
	}
	
	/**
	 * 
	 * @param param
	 * @throws Exception
	 */
	public DataRow network_wireless(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		DataRow wireless = dao.selectOne("/bpw/nw/ClientDetail.network_wireless", dataMap);
		return wireless;
	}
	
	/**
	 * 
	 * @param param
	 * @throws Exception
	 */
	public DataTable client_conn_stats(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		DataTable connStats = dao.selectDataTable("/bpw/nw/ClientDetail.client_conn_stats", dataMap);
		return connStats;
	}
	
	/**
	 * 
	 * @param param
	 * @throws Exception
	 */
	public DataRow client_conn_cnt(DataSet param) throws Exception
	{
		DataRow dataMap = param.getRow();
		
		DataRow connCnt = dao.selectOne("/bpw/nw/ClientDetail.client_conn_cnt", dataMap);
		return connCnt;
	}
}
