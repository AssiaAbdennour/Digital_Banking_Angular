package com.assia.ebankingbackend.Services;

import com.assia.ebankingbackend.Dtos.*;
import com.assia.ebankingbackend.Entities.BankAccount;
import com.assia.ebankingbackend.Entities.CurrentAccount;
import com.assia.ebankingbackend.Entities.Customer;
import com.assia.ebankingbackend.Entities.SavingAccount;
import com.assia.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.assia.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.assia.ebankingbackend.exceptions.CustomerNotFountException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFountException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFountException;
    List<CustomerDTO> listCustomer();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount,String description ) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId, double amount,String description ) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination ,double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFountException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
