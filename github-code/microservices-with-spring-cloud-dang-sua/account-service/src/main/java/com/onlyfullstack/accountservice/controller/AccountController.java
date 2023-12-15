package com.onlyfullstack.accountservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.onlyfullstack.accountservice.bean.Account;
import com.onlyfullstack.accountservice.bean.Customer;
import com.onlyfullstack.accountservice.service.CustomerServiceProxy;

@RestController
@RequestMapping("/api")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);
    private List<Account> listOfAccounts = new ArrayList<>(5);

    @Autowired
    CustomerServiceProxy customerServiceProxy;

    public AccountController() {
        Account account1 = new Account(1L, null, 1111.50);
        Account account2 = new Account(2L, null, 1112.50);
        Account account3 = new Account(3L, null, 1113.50);
        this.listOfAccounts.add(account1);
        this.listOfAccounts.add(account2);
        this.listOfAccounts.add(account3);
    }

    @GetMapping("/accounts/get-all")
    public List<Account> getAllAccounts() {
        return this.listOfAccounts;
    }

    @GetMapping("/accounts/get-alll")
    public Object getAllCustomer() {
        Object object = customerServiceProxy.getAllCustomers();
        return object;
    }

    @GetMapping("/accounts/{account_id}")
    @HystrixCommand(fallbackMethod = "getAccountDetails_fallback")
    public Account getAccountDetails(@PathVariable Long account_id) {
        logger.debug("*** Entered in getAccountDetails with account_id: {}", account_id);
        Account account = this.listOfAccounts.stream().filter(acc -> acc.getId().equals(account_id)).findAny().orElse(null);
        Customer customer = customerServiceProxy.getCustomerDetails(account_id); // Part 3 Changes
        account.setCustomer(customer);
        logger.debug("*** Exiting from getAccountDetails with account: {}", account);
        return account;
    }

    public Account getAccountDetails_fallback(@PathVariable Long account_id) {
        logger.debug("*** Entered in getAccountDetails_fallback with account_id: {}", account_id);
        Account account = getDefaultAccount();
        return account;
    }

    private Account getDefaultAccount() {
        return new Account(100L, null, (double) 0);
    }
}
