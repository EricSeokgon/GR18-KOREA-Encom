package com.ncom.bpwb.init;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ncom.bpwb.service.MongoToMariaBatchService;
import com.ncom.bpwb.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MongoToMariaJob {

	@Resource
	private JobBuilderFactory	jobBuilderFactory;
	@Resource
	private StepBuilderFactory	stepBuilderFactory;

	@Resource
	private MongoToMariaBatchService		service;

	private final String		JobName		= "MongoToMariaJob";
	private final String		step1Name	= "- 1/2 MongoToMaria";
	
	
	  @Bean public Job excuteMongoToMaria() { return jobBuilderFactory.get(JobName)
	  
	  .start(mongoToMariaStep())
	  
	  .build();
	  
	  }
	  
	  @Bean public Step mongoToMariaStep() { return stepBuilderFactory.get(JobName
	  + step1Name).tasklet((stepContribution, chunkContext) -> {
	  
	  log.info("-------------" + JobName + step1Name + " start : " +
	  DateUtil.getCurDatetimeSeoul()); service.mongoToMaria();
	  
	  return RepeatStatus.FINISHED; }).build(); }
	 
	  
	 

}