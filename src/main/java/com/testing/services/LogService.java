package com.testing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.testing.entity.Log;
import com.testing.entity.Sequence;

import reactor.core.publisher.Mono;

@Service
public class LogService {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Long> getNextSequenceId(String key) {
    Query query = new Query(Criteria.where("_id").is(key));
    Update update = new Update().inc("seq", 1);
    return reactiveMongoTemplate
            .findAndModify(query, update, FindAndModifyOptions.options().returnNew(true).upsert(true), Sequence.class)
            .map(Sequence::getSeq);
}

    public Mono<Log> save(Log log){
        return getNextSequenceId("log")
        .map(seq -> {
            log.setId(seq);
            return log;
        })
        .flatMap(reactiveMongoTemplate::save);
    }
    
}
