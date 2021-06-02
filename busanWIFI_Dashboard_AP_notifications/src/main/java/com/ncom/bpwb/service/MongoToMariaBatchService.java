package com.ncom.bpwb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ncom.bpwb.dao.Session1Dao;
import com.ncom.bpwb.domain.entity.Cmxdata;
import com.ncom.bpwb.domain.repository.mongo.CmxdataMongoRepo;
import com.ncom.bpwb.util.AsyncUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MongoToMariaBatchService {
	

	@Resource
	private Session1Dao dao;
	

	
	@Resource
	private CmxdataMongoRepo cmxdataMongoRepo;
	
	

	
	@Resource
	private BpwBachAsyncService bpwBachAsyncService;
	
	
	@Value("${thread.cnt}")
	int ThreadCnt;
	
	@Resource
	private MqttService mqttService;

	

	
	public void mongoToMaria() throws JsonMappingException, JsonProcessingException, InterruptedException {
		
		log.info("---------------------------1. Location data inquiry ");
		
		int pageNumber = 0;
		int pageSize = 100000;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Cmxdata> page = cmxdataMongoRepo.findAll(pageable);
		List<Cmxdata> cmxdatList = page.getContent();
		
		log.info("cmxdatList.size():"+cmxdatList.size());
		
		
		List<List<Cmxdata>> partitionListList = AsyncUtil.partition(cmxdatList, ThreadCnt);
		
		List<Future<String>> futureList  = new ArrayList<Future<String>>() ;

		for (List<Cmxdata> prationList : partitionListList) {
			
		    Future<String> future = bpwBachAsyncService.insertDBAsync(  prationList);
		    futureList.add(future);

		}  //end for(DataObject itemMap: itemListMap)
		
		
		int cnt=0;
		//thread wait
		for(Future<String> future : futureList ){
		    while (true) {
		        if (future.isDone()) {
		        	log.info( "---------:thrread "+(cnt++)+": complete");
		            break;
		        }
		        Thread.sleep(500); //0.5 sec check
		    }		
		}
		
	//	 Thread.sleep(1000*30); //30 sec wait
		log.info( "---------: insetRecoToDB complete");
		
		
		log.info("oneM2M  sensor status start!!");
		String sensor_nm="STATUS";

		 try {
			mqttService.statusToIot(sensor_nm);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		} catch (MqttException e) {			
			e.printStackTrace();
		}
		
		log.info("oneM2M  sensor status complete!!");
				
		
	}







	
	
	
}
