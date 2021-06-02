package com.ncom.bpwb.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ncom.bpwb.domain.entity.object.DataObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Document(collection="cmxdata")
public class Cmxdata extends SuperCmxdata {

}
