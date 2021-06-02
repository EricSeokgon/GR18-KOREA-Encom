package com.ncom.bpwb.domain.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




@Getter
@Setter
@ToString
public class SuperCmxdata {
	
	@Id
    private ObjectId _id;
    private String version ;
    private String secret;
    private String type;
    
   // private DataObject data;
    
    private String data;
    
    
 

}
