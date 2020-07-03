package com.example.demo.Controller;

import com.example.demo.Model.Account;
import com.example.demo.Model.Customer;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/allAccount")
    public String getAllAccount(Model model){
        Flux<Account> listAccount = accountRepository.findAll();
        listAccount.subscribe();
        model.addAttribute("accounts", listAccount);
        return "accounts";
    }

    @GetMapping("/all/{id}")
    public Mono<Account> getAccountById(@PathVariable("id") String id){
        return accountRepository.findById(id);
    }

    @GetMapping("/add")
    public String getInsertAccount(){
        return "add-account";
    }

    @PostMapping("/add")
    public RedirectView postInsertAccount(@ModelAttribute Account account, Model model){
        accountRepository.insert(account)
                .map(account1 -> ResponseEntity.ok(account1))
                .subscribe(i -> System.out.println(i));
        return new RedirectView("/account/allAccount", HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable(value = "id") String id) {
        return accountRepository.findById(id)
                .flatMap(i ->
                        accountRepository.delete(i)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/edit/{id}")
    public String getAccountById(@PathVariable(value = "id") String id, Model model) {
        Mono<Account> account = accountRepository.findById(id);
        model.addAttribute("account", account);
        return "edit-account";
    }

    @PostMapping(value = "/edit/{id}")
    public RedirectView updateAccountById(@ModelAttribute Account account) {
        System.out.println(account.getUsername());
        System.out.println(account.getId());
        Mono<Account> accountMono = accountRepository.save(account);
        accountMono.subscribe(
                i -> System.out.println(i.getUsername())
        );
        return new RedirectView("/account/allAccount", HttpStatus.MOVED_PERMANENTLY);
    }
}
