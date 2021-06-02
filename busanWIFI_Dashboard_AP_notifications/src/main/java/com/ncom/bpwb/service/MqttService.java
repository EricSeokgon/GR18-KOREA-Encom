package com.ncom.bpwb.service;

import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ncom.bpwb.domain.iot.M2m;
import com.ncom.bpwb.util.DateUtil;
import com.ncom.bpwb.util.MqttUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MqttService {
	

	@Value("${iot.broker}")
	String broker;
	
	@Value("${iot.device_id}")
	String device_id;	
	
	@Value("${iot.device_key}")
	String device_key;		
	
	public void publishToIot(String sensor_nm, Object publishData) throws MqttException, JsonProcessingException {
		
        int qos             = 2;
		String client_id ="mqttClient_"+device_id;
        String username=device_id;

		String passwd="D|"+device_key;
		
		String req_topic        = "/oneM2M/req/S"+device_id+"/Mobius/json";
		
		MqttUtil mqttUtil = new MqttUtil(broker, client_id, username, passwd);

        //String table_nm ="stats";
		String msg      = getMsgJosnString(device_id, sensor_nm, publishData);
		

		mqttUtil.publish(req_topic, msg, qos);
		
		mqttUtil.disconnect();
		
	}
	
	
	public void statusToIot(String sensor_nm) throws MqttException, JsonProcessingException {
	  
		int qos = 2;
		String client_id ="mqttClient_"+device_id;
        String username=device_id;

		String passwd="D|"+device_key;
		
		String req_topic = "/oneM2M/req/S"+device_id+"/Mobius/json";
		
		MqttUtil mqttUtil = new MqttUtil(broker, client_id, username, passwd);
        
		String msg = getMsgJosnStatus(device_id, sensor_nm);
		

		mqttUtil.publish(req_topic, msg, qos);
		
		mqttUtil.disconnect();
	  
	  }
	 
	

	
	
	public String getMsgJosnString(String device_id, String sensor_nm, Object publishData) throws JsonProcessingException  {
		
		
		String to="/Mobius/S"+device_id+"/"+sensor_nm;
		String rqi="rqi_"+device_id+"_"+DateUtil.getCurDatetime();
		String fr="/S"+device_id;
		
		M2m m2m= new M2m(to, rqi, fr);
		
		m2m.setCon(publishData);

		log.info(m2m.toJsonString());
		
		return m2m.toJsonString();
	}	
	
	
	  public String getMsgJosnStatus(String device_id, String sensor_nm) throws JsonProcessingException {
	  
	  
	  String to="/Mobius/S"+device_id+"/"+sensor_nm; String
	  rqi="rqi_"+device_id+"_"+DateUtil.getCurDatetime(); String fr="/S"+device_id;
	  
	  M2m m2m= new M2m(to, rqi, fr);
	  
	  
	  HashedMap<String, String> statusData= new HashedMap<String, String>();

	  statusData.put("device_status", "0");
	  m2m.setCon(statusData);
	  
	  log.info(m2m.toJsonString());
	  
	  return m2m.toJsonString(); }
	 
	
	
}


