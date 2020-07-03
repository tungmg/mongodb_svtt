package com.example.demo.Repository;

import com.example.demo.Model.Account;
import com.example.demo.Model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
}
