package com.ncom.bpwb.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




@Builder // builder를 사용할수 있게 합니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@Setter // user 필드값의 setter를 자동으로 생성합니다.
@ToString
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Entity // jpa entity임을 알립니다.
@Table(name = "bpw_location_client")
public class Bpwlocationclient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long clnt_seq;
	
	private Date input_dt;
	
	@Column(name="apMac")
	private String apMac;
	
	@Column(name="apFloors")
	private String apFloors;
	
	@Column(name="apTags")
	private String apTags; 
	
	private String ipv4;             
	private String lat;               
	private String lng;              
	private String umc;             
	private String x;                 
	private String y; 
	
	@Column(name="seenTime")
	private String seenTime;      
	private String ssid;              
	private String os; 
	
	@Column(name="clientMac")
	private String clientMac;   
	
	@Column(name="seenEpoch")
	private String seenEpoch;  
	
	private String rssi;              
	private String ipv6;             
	private String manufacturer; 
	
	private Date epoch_dt;
	private Date objectid_dt;	
	
    
 

}
