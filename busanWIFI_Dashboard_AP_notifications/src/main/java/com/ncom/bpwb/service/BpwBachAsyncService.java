package com.ncom.bpwb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ncom.bpwb.domain.entity.Bpwlocationclient;
import com.ncom.bpwb.domain.entity.Cmxdata;
import com.ncom.bpwb.domain.entity.Cmxdata_copy;
import com.ncom.bpwb.domain.entity.object.DataObject;
import com.ncom.bpwb.domain.entity.object.Observation;
import com.ncom.bpwb.domain.repository.maria.BpwlocationclientMariaRepo;
import com.ncom.bpwb.domain.repository.mongo.CmxdataMongoRepo;
import com.ncom.bpwb.domain.repository.mongo.Cmxdata_copyMongoRepo;
import com.ncom.bpwb.domain.util.ObjectIdTime;
import com.ncom.bpwb.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Service
public class BpwBachAsyncService {
	


	@Resource
	private BpwlocationclientMariaRepo bpwlocationclientMariaRepo;
	
	@Resource
	private Cmxdata_copyMongoRepo cmxdata_copyMongoRepo;
	
	@Resource
	private CmxdataMongoRepo cmxdataMongoRepo;
	

	@Async
	public Future<String> insertDBAsync(List<Cmxdata> cmxdatList) throws JsonMappingException, JsonProcessingException {
		
		int cnt = 0;
		for(Cmxdata cmxdata: cmxdatList) {			
			dataProcessing(cmxdata);
			cnt++;
		}	

		log.info("--------------------------- dataProcessing end : "+ cnt);
		
		return new AsyncResult<String>("");
	}
	
	

	private void dataProcessing(Cmxdata cmxdata) throws JsonMappingException, JsonProcessingException {
		ObjectIdTime objectIdTime= new ObjectIdTime(cmxdata.get_id().getTimestamp());		

		DataObject dataObject = JsonUtil.jsonStringToObject(cmxdata.getData(),  DataObject.class);
		dataObject.setObjectid_dt(objectIdTime.getDate_time());

//		log.info("--------------------------- 2-1. mariaDB save ");
		saveMariaDbBpwlocationClient(dataObject) ;

//		log.info("--------------------------- 2-2.cmxdat_copy move ");
		moveToCmxdata_copy(cmxdata);	
		
	}

	
	private void saveMariaDbBpwlocationClient(DataObject apMacDataObject) {
		
		String apMac =apMacDataObject.getApMac();
		String apFloors =String.join(",", apMacDataObject.getApFloors());
		String apTags = String.join(",", apMacDataObject.getApTags());
		Date objectid_dt =apMacDataObject.getObjectid_dt();
		
		List<Bpwlocationclient> bpwlocationclientList = new ArrayList<Bpwlocationclient>(); 
	
		for(Observation observation: apMacDataObject.getObservations()) {
			//log.info(observation.toString());
			
			Bpwlocationclient bpwlocationclient = Bpwlocationclient.builder()
																		.apMac(apMac)
																		.apFloors(apFloors)
																		.apTags(apTags)
																		.ipv4(observation.getIpv4())
																		.lat(observation.getLocation().getLat()+"")
																		.lng(observation.getLocation().getLng()+"")
																		.umc(observation.getLocation().getUnc()+"")
																		.x(String.join(",",observation.getLocation().getX()))
																		.y(String.join(",",observation.getLocation().getY()))
																		.seenTime(observation.getSeenTime())
																		.ssid(observation.getSsid())
																		.os(observation.getOs())
																		.clientMac(observation.getClientMac())
																		.seenEpoch(observation.getSeenEpoch()+"")
																		.rssi(observation.getRssi()+"")
																		.ipv6(observation.getIpv6())
																		.manufacturer(observation.getManufacturer())
																		.epoch_dt(new Date(observation.getSeenEpoch()*1000L))
																		.objectid_dt(objectid_dt)
																		.build();
			bpwlocationclientList.add(bpwlocationclient);
						
		}

		bpwlocationclientMariaRepo.saveAll(bpwlocationclientList);
		
	}
	
	
	private void moveToCmxdata_copy(Cmxdata cmxdata) {

		Cmxdata_copy  cmxdata_copy = new Cmxdata_copy();
		BeanUtils.copyProperties(cmxdata, cmxdata_copy);
		
		cmxdata_copyMongoRepo.save( cmxdata_copy);
		cmxdataMongoRepo.delete(cmxdata);
		
	}	
	
}
