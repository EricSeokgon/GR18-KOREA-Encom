package com.ncom.bpwb.service;

import java.awt.PageAttributes.MediaType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class SummaryBatchService {
	

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

		} 
		
		
		int cnt=0;
		for(Future<String> future : futureList ){
		    while (true) {
		        if (future.isDone()) {
		        	log.info( "---------:thrread "+(cnt++)+": complete");
		            break;
		        }
		        Thread.sleep(500); //0.5 sec check
		    }		
		}
		
	//	 Thread.sleep(1000*30); //30 sec sleep
		log.info( "---------: insetRecoToDB complete");
				
		
	}




	/**
	 * Daily aggregation logic
	 */
	public void summaryBpw() throws Exception {

				 
		 log.info("summaryBpw  start!!");
		 
		 dao.update("/bpwb/analytics.insertLocation");
		 dao.update("/bpwb/analytics.insertproximity");
		 dao.update("/bpwb/analytics.insertengagement");
		 dao.update("/bpwb/analytics.insertloyalty");
		 
		 log.info("summaryBpw complete!!");
		 
		
	}
	/**
	 * Daily aggregation logic
	 */
	public void summaryBpw2() throws Exception{

		log.info("summaryBpw2  start!!");
		dao.delete("/bpwb/analytics.delete");
		dao.delete("/bpwb/analytics.deleteanalytics");
		log.info("summaryBpw2  complete!!");
		
		
		log.info("oneM2M  start!!");
		String sensor_nm="stats";
		//  Read aggregate data from db and send
		List<Map<String, String>>  publishDataList  = dao.selectList("/bpwb/analytics.selectproximity");
		
		 Map<String, List<Map<String, String>>> publishData= new HashedMap<String, List<Map<String, String>>>();

		 publishData.put("data", publishDataList);

		 mqttService.publishToIot(sensor_nm, publishData);
		
		 log.info("oneM2M  complete!!");
		 
		 botPost1();
		 log.info("webex message post complete!!");
	}




	/**
	 * Daily aggregation
	 * @param objectid_day
	 */
	private void summaryByObjectid_day(String objectid_day) {
		 	log.info(objectid_day);
		
	}

	
	/**
	 * cisco webex - ncom@webex.bot rest-api post
	 * test room id : Y2lzY29zcGFyazovL3VzL1JPT00vNzllNjNjODAtYjNiMC0xMWViLTg1NDEtM2JlY2FjMDk0MDRh
	 * aggregate notification
	 */
	public void botPost1() {
//		Current status of 'Cisco Meraki' in Busan, Korea.
//
//		Time(KST) : 2021-05-21 13:22
//		Online : 242
//		Offline : 1
//		Nosiganal : 0	
		
		Date dt = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = transFormat.format(dt);

		String online = "0";	//json-text type;
		String offline = "0";
		String nosignal = "0";
		
		List<Map<String, String>>  statusList  = dao.selectList("/bpwb/WebexBotPost.selectStatus");
		
		for(Map<String, String> stMap : statusList) {
			String stat = stMap.get("status");
			String cnt = stMap.get("count");
			
			if(stat.equals("online")) online = cnt;
			if(stat.equals("offline")) offline = cnt;
			if(stat.equals("nosignal")) nosignal = cnt;
		}
		
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, 
				"{\r\n  "
				+ "\"roomId\": \"Y2lzY29zcGFyazovL3VzL1JPT00vYmFlZWFmZjAtYjllYi0xMWViLTgwOGYtODMzMjRhOTUwMjc1\",\r\n  "
				+ "\"markdown\": \"[ncom bot](http://210.114.174.137:8080/bpw/bpw/login/Login.do). Current status of 'Cisco Meraki' in Busan, Korea.\",\r\n  "
				+ "\"attachments\": [\r\n    {\r\n      \"contentType\": \"application/vnd.microsoft.card.adaptive\",\r\n      "
				+ "\"content\": {\r\n        \"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\r\n        "
				+ "\"type\": \"AdaptiveCard\",\r\n        \"version\": \"1.0\",\r\n        \"body\": [\r\n          {\r\n            "
				+ "\"type\": \"ColumnSet\",\r\n            \"columns\": [\r\n              {\r\n                \"type\": \"Column\",\r\n                "
				+ "\"width\": 1,\r\n                \"items\": [\r\n                  {\r\n                    \"type\": \"TextBlock\",\r\n                    "
				+ "\"text\": \"Current status of 'Cisco Meraki' in Busan, Korea.\",\r\n                    \"wrap\": true\r\n                  },\r\n                  {\r\n"
				+ "\"type\": \"TextBlock\",\r\n                    \"text\": \"Time(KST) : "+ time +"\\r\\nOnline : " + online + "\\r\\nOffline : " + offline + "\\r\\nnosiganal : " + nosignal+ "\",\r\n "
				+ "\"wrap\": true\r\n                  }\r\n                ]\r\n              }\r\n            ]\r\n          }\r\n        ]\r\n      }\r\n    }\r\n  ]\r\n}");
		Request request = new Request.Builder()
		  .url("https://webexapis.com/v1/messages")
		  .method("POST", body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Bearer NmU4MGMxNTAtNGFlNy00NWFmLTgzMjctNzJjYWFkMzQyOTIyYjZjZmM0ZjUtYmFk_PF84_consumer")
		  .build();
		Response response = client.newCall(request).execute();
	}
	
	
	/**********************************************************************************************
	 * bot info
	 * id : Y2lzY29zcGFyazovL3VzL0FQUExJQ0FUSU9OLzQyN2YxNDQ0LTUyOGUtNGRmYy1hZTYxLTA4MmUzYmVlZjM2NQ
	 * token(bearer) : NmU4MGMxNTAtNGFlNy00NWFmLTgzMjctNzJjYWFkMzQyOTIyYjZjZmM0ZjUtYmFk_PF84_consumer
	 * add email : ncom@webex.bot
	 **********************************************************************************************/
	
	/**
	 * Issue notification
	 */
	public void botPost2() {
//		!Notification
//
//		Current issues. Please check the information.
//
//		status : offline 
//		mac(serial) : 68:3a:1e:22:c7:ee(Q2LD-2Y4R5-5Q66) 
//		model : MR52 
//		name : SMM-1-2 
//		tags : 서면몰 
//		time : 2021-05-21 13:23:07
		
		String _json = "";
		List<Map<String, String>>  statusList  = dao.selectList("/bpwb/WebexBotPost.selectStatusDetail");
		
		for(Map<String, String> status : statusList) {
			_json += "{\r\n\"type\": \"TextBlock\",\r\n\"text\": \"\\r\\nstatus : "+status.get("status")+"\\r\\nmac(serial) : "
					+status.get("mac")+"("+status.get("serial")+")"+"\\r\\nmodel : "+status.get("model")+"\\r\\nname : "+status.get("name")+"\\r\\ntags : "
					+status.get("tags")+"\\r\\ntime : "+status.get("updateDt")+"\\r\\n\",\r\n\"wrap\": true\r\n}"; 
		}
		
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, 
				"{\r\n  \"roomId\": \"Y2lzY29zcGFyazovL3VzL1JPT00vYmFlZWFmZjAtYjllYi0xMWViLTgwOGYtODMzMjRhOTUwMjc1\",\r\n  "
				+ "\"markdown\": \"[ncom bot](http://210.114.174.137:8080/bpw/bpw/login/Login.do). Current status of 'Cisco Meraki' in Busan, Korea.\",\r\n  "
				+ "\"attachments\": [\r\n    {\r\n      \"contentType\": \"application/vnd.microsoft.card.adaptive\",\r\n      \"content\": {\r\n        "
				+ "\"$schema\": \"http://adaptivecards.io/schemas/adaptive-card.json\",\r\n        \"type\": \"AdaptiveCard\",\r\n        "
				+ "\"version\": \"1.0\",\r\n        \"body\": [\r\n          {\r\n            \"type\": \"ColumnSet\",\r\n            "
				+ "\"columns\": [\r\n              {\r\n                \"type\": \"Column\",\r\n                \"width\": 1,\r\n                "
				+ "\"items\": [\r\n                  {\r\n                    \"type\": \"TextBlock\",\r\n                    "
				+ "\"text\": \"!Notification\",\r\n                    \"wrap\": true\r\n                  },\r\n                  {\r\n                    "
				+ "\"type\": \"TextBlock\",\r\n                    \"text\": \"Current issues.\\r\\nPlease check the information.\",\r\n                    "
				+ "\"wrap\": true\r\n                  },\r\n                  "
				+ _json 
				+ "\r\n                ]\r\n              }\r\n            ]\r\n          }\r\n        ]\r\n      }\r\n    }\r\n  ]\r\n}");
		Request request = new Request.Builder()
		  .url("https://webexapis.com/v1/messages")
		  .method("POST", body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Bearer NmU4MGMxNTAtNGFlNy00NWFmLTgzMjctNzJjYWFkMzQyOTIyYjZjZmM0ZjUtYmFk_PF84_consumer")
		  .build();
		Response response = client.newCall(request).execute();
	}
	
}
