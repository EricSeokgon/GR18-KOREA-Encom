package com.ncom.bpwb.domain.entity.object;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Location {
	private double lat;
	private double lng;
	private double unc;
	private ArrayList<String> x;
	private ArrayList<String> y;

	
	
}
