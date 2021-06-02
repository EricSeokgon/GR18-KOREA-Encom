/*=====================================================================
 * 작 업 명  : 공통 DAO
 * 파 일 명  : Dao.java  
 * 작 업 자  : 황동용
 * 작 업 일  : 2016-10-26
 * 변경이력 
=======================================================================*/

package com.ncom.bpwb.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class Session1Dao extends EgovAbstractMapper
{
	@Resource(name = "sqlSession1")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) 
	{
    	super.setSqlSessionFactory(sqlSession);
    }
}


