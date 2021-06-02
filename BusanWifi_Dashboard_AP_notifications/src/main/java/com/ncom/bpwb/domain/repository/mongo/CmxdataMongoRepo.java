package com.ncom.bpwb.domain.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ncom.bpwb.domain.entity.Cmxdata;

public interface CmxdataMongoRepo extends MongoRepository<Cmxdata, String> {
	
	 
}
