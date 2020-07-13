package com.example.demo.Repository;

import com.example.demo.Model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface AccountRepository extends ReactiveMongoRepository<Account, String>{
    //    void updateAccount(String id, String username,String password, int age);
}
