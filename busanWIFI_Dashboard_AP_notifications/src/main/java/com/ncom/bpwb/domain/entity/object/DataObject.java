package com.ncom.bpwb.domain.entity.object;

import java.util.ArrayList;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다
@Getter
@Setter
@ToString
public class DataObject {
	
	private String apMac;
	private ArrayList< String> apFloors;
	private ArrayList< String> apTags;
	private ArrayList<Observation> observations;
	private String secret;
	private GeoJSON geoJSON;
	
	private Date objectid_dt;

}
