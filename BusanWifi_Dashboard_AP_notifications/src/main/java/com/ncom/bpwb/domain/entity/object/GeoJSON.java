package com.ncom.bpwb.domain.entity.object;

import java.util.ArrayList;

import lombok.Data;

@Data
public class GeoJSON {

	private String type;
	private Geometry geometry;
	private Properties properties;
	
}
