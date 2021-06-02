package com.ncom.bpwb.domain.iot;

import lombok.Data;

@Data
public class ConData {
	

	int count;
	Object data;

	public ConData(int count, String data) {
		this.count = count;
		this.data =data;
	}
}
