package com.ncom.bpwb.domain.iot;

import com.ncom.bpwb.domain.entity.object.GeoJSON;
import com.ncom.bpwb.domain.entity.object.Geometry;
import com.ncom.bpwb.domain.entity.object.Properties;

import lombok.Data;

@Data
public class M2mrqp {
	
	int op=1;
	Pc pc = new Pc();
	int ty=4;
	String to;
	String rqi;
	String fr;
	

}
