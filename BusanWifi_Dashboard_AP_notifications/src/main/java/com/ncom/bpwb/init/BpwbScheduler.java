package com.ncom.bpwb.init;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ncom.bpwb.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
@EnableBatchProcessing
@RequiredArgsConstructor
public class BpwbScheduler  {

	private final JobLauncher		jobLauncher;
	private final MongoToMariaJob	mongoToMariaJob;
	private final SummaryBpwJob	summaryBpwJob;
	
		
		
		  @Scheduled(fixedDelay= 1000*60*30) //every 30 minutes 
		  public void mongoToMariaScheduler() {
		  
		  log.info("=========== 1.mongoToMariaScheduler start("+DateUtil.getCurDatetimeSeoul()+")=============== ");
		  
		  Map<String, JobParameter> confMap = new HashMap<>(); confMap.put("time", new
		  JobParameter(System.currentTimeMillis())); JobParameters jobParameters = new
		  JobParameters(confMap);
		  
		  try {
		  
		  jobLauncher.run(mongoToMariaJob.excuteMongoToMaria(), jobParameters);
		  
		  } catch (JobExecutionAlreadyRunningException |
		  JobInstanceAlreadyCompleteException | JobParametersInvalidException |
		  org.springframework.batch.core.repository.JobRestartException e) {
		  
		  log.error(e.getMessage()); }
		  
		  }
		 
		 
	  
	  
	 
	

	//@Scheduled(fixedDelay= 1000*60*10) //10분마다 한번 -실서버용
	@Scheduled(cron = "${schedule.summaryBpw.string}")//매일 새벽 2시
	public void summaryBpwScheduler() {

		log.info("=========== 2.summaryBpwScheduler 시작("+DateUtil.getCurDatetimeSeoul()+")=============== ");

		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {

			jobLauncher.run(summaryBpwJob.excuteSummaryBpw(), jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

			log.error(e.getMessage());
		}

	}
}
