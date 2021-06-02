package com.ncom.bpwb._config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;



@Configuration
public class ContextMapper {

	/**
	 * SqlSession 설정
	 * @param dataSource DataSource
	 * @return SqlSessionFactoryBean
	 * @throws IOException 파일 읽기 예외
	 */
	@Bean(name="sqlSession1")
	public SqlSessionFactoryBean sqlSession1(DataSource dataSource) throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(pmrpr.getResource("classpath:/sql/mapper-config/sql-mapper-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(pmrpr.getResources("classpath:/sql/mapper/**/*.xml"));
		return sqlSessionFactoryBean;
	}

	/**
	 * Mapper 적용 범위 설정
	 * @return MapperConfigurer
	 */
	@Bean
	public MapperScannerConfigurer mapperConfigurer() {
		MapperScannerConfigurer mapperConfigurer = new MapperScannerConfigurer();
		mapperConfigurer.setBasePackage("com.ncom.bpwb");
		mapperConfigurer.setSqlSessionFactoryBeanName("sqlSession1");
		return mapperConfigurer;
	}
	
}
