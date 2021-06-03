package ncomegf.bpw.sample.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncom.core.vo.DataRow;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import ncomegf.base.dao.Session1Dao;
import ncomegf.bpw.sample.domain.ArticleVO;

@Service
public class SampleService extends EgovAbstractServiceImpl {


	
	
	@Resource
	private Session1Dao dao;

	public DataRow selectUser(String user_id) {

		DataRow row = dao.selectOne("/bpw/sample/Sample.selectUser", user_id);
		
		return row;
	}
	


	public DataRow select(ArticleVO  articleVO ) throws IllegalArgumentException, IllegalAccessException {

		
		DataRow row = dao.selectOne("/bpw/sample/Sample.selectUser", new DataRow(articleVO));
		
		return row;
	}
	


}