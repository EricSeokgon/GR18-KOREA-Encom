package com.ncom.bpwb.domain.entity.object;

import lombok.Data;

@Data
public class Observation {

	private String ipv4;
	private Location location;
	private String seenTime;
	private String ssid;
	private String os;
	private String clientMac;
	private int seenEpoch;
	private int rssi;
	private String ipv6;
	private String manufacturer;
	
	
}
