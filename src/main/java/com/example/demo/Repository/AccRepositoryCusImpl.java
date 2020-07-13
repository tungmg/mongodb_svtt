package com.example.demo.Repository;

import com.example.demo.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class AccRepositoryCusImpl implements AccountRepositoryCus {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void updateAccount(@ModelAttribute Account acc) {
        Query query = new Query().addCriteria(where("_id").is(acc.getId()));
//        query.addCriteria(where("username").is(acc.getUsername()));
        System.out.println(acc.getId());
        Update update = new Update();
        update.set("username",acc.getUsername());
        update.set("password",acc.getPassword());
        update.set("age",acc.getAge());
        System.out.println(this.mongoTemplate);
        this.mongoTemplate.update(Account.class).matching(query).apply(update).first();
    }
}
