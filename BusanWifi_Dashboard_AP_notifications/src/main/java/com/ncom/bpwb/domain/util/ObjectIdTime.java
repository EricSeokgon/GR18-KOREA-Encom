package com.ncom.bpwb.domain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.ncom.bpwb.util.StrUtil;

import lombok.Data;



@Data
public class ObjectIdTime {
	
	Date date_time;
	
	String str_time;
	
	int mm;
	
	int yyyyMMdd;
	
	
   public ObjectIdTime(int timestamp){

	    this.date_time = new java.util.Date(timestamp*1000L); 
	    SimpleDateFormat time_sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    this.str_time = time_sdf.format(this.date_time);
	    
	    SimpleDateFormat mm_sdf = new java.text.SimpleDateFormat("mm"); 
	    this.mm =  StrUtil.str2int(mm_sdf.format(this.date_time));
	   
	    SimpleDateFormat yyyyMMdd_sdf = new java.text.SimpleDateFormat("yyyyMMdd"); 
	    this.yyyyMMdd =  StrUtil.str2int(yyyyMMdd_sdf.format(this.date_time));
	}
}
