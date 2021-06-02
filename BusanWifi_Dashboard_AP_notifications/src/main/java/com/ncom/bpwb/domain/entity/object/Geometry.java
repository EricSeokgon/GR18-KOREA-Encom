package com.ncom.bpwb.domain.entity.object;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Geometry {
	private String type;
	private ArrayList<Double> coordinates;

}
