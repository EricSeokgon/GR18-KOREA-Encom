package com.ncom.bpwb.domain.iot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ncom.bpwb.util.JsonUtil;

import lombok.Data;

@Data
public class M2m {

	M2mrqp m2m__rqp = new M2mrqp();
	
	
	public M2m(String to, String rqi, String fr) {
		this.m2m__rqp.setTo(to);
		this.m2m__rqp.setRqi(rqi);
		this.m2m__rqp.setFr(fr);
	}


	public void setCon(Object con) {
		this.m2m__rqp.getPc().getM2m__cin().setCon(con);	
	}

	public String toJsonString() throws JsonProcessingException {
		return JsonUtil.ObjectToJsonString(this).replace("__", ":");
	}
}
