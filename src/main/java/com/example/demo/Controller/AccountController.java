package com.example.demo.Controller;

import com.example.demo.Model.Account;
import com.example.demo.Repository.AccRepositoryCusImpl;
import com.example.demo.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    AccRepositoryCusImpl accRepositoryCus;

    @GetMapping("/all")
    public String getAllAccount(Model model) throws InterruptedException {
            Flux<Account> listAccount = accountRepository.findAll();
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
        return new RedirectView("/account/all", HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping(value = "/delete/{id}")
    public RedirectView deleteAccount(@PathVariable(value = "id") String id) {
        accountRepository.deleteById(id).subscribe();
        return new RedirectView("/account/all", HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping(value = "/edit/{id}")
    public String getAccountById(@PathVariable(value = "id") String id, Model model) {
        Mono<Account> account = accountRepository.findById(id);
        model.addAttribute("account", account);
        return "edit-account";
    }

    @PostMapping(value = "/edit/{id}")
    public RedirectView updateAccountById(@ModelAttribute Account account) {

//        Mono<Account> accountMono = accountRepository.save(account);
//        accountMono.subscribe(
//                i -> System.out.println(i.getUsername())
//        );
//        Mono<Account> accountMono = accountRepository.save(account);
//        accountMono.subscribe(i-> System.out.println(i.getUsername() + " " + i.getPassword()+ " "+i.getAge()));
//         this.accountRepository
//                .findById(account.getId())
//                .map(p -> account)
//                .flatMap(this.accountRepository::save)
//                .subscribe(i-> System.out.println(account.toString()));
//        accountRepository.updateAccount(account);
//        ServerAddress address = new ServerAddress("127.0.0.1", 6767);
//        MongoCredential credential = MongoCredential.createCredential("mdbUser", "tungmgsharding", "cp".toCharArray());
//        MongoClientOptions options = new MongoClientOptions.Builder().build();
//
//        MongoClient client = new MongoClient(address, credential, options);
//
//        Query query = new Query().addCriteria(where("_id").is(account.getId()));
//        Update update = new Update();
//        update.set("username",account.getUsername());
//        update.set("password",account.getPassword());
//        update.set("age",account.getAge());
//        mongoTemplate.update(Account.class).matching(query).apply(update).first();
//        accountRepository.updateAccount(account.getId(),account.getUsername(),account.getPassword(),account.getAge());
        //AccRepositoryCusImpl accRepositoryCus = new AccRepositoryCusImpl();
        accRepositoryCus.updateAccount(account);
        return new RedirectView("/account/all", HttpStatus.MOVED_PERMANENTLY);
    }
}
