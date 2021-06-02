package com.ncom.bpwb.init;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ncom.bpwb.service.SummaryBatchService;
import com.ncom.bpwb.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SummaryBpwJob {

	@Resource
	private JobBuilderFactory jobBuilderFactory;
	@Resource
	private StepBuilderFactory stepBuilderFactory;

	@Resource
	private SummaryBatchService smmaryService;

	private final String JobName = "SummaryBpwJob";
	private final String step1Name = "- 1/2 total1";
	private final String step2Name = "- 2/2 total2";

	@Bean
	public Job excuteSummaryBpw() {
		return jobBuilderFactory.get(JobName)

				.start(step1())

				.next(step2())

				.build();

	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get(JobName + step1Name).tasklet((stepContribution, chunkContext) -> {

			log.info("-------------" + JobName + step1Name + " start : " + DateUtil.getCurDatetimeSeoul());
			smmaryService.summaryBpw();

			return RepeatStatus.FINISHED;
		}).build();
	}

	
	  @Bean public Step step2() { 
		  return stepBuilderFactory.get(JobName + step2Name).tasklet((stepContribution, chunkContext) -> {
	  
			  log.info("-------------" + JobName + step2Name + " start : " + DateUtil.getCurDatetimeSeoul()); 
			  smmaryService.summaryBpw2();
	  
			  return RepeatStatus.FINISHED; 
	  }).build(); }
	 

}