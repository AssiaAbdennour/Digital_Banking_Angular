package com.assia.ebankingbackend;

import com.assia.ebankingbackend.Dtos.BankAccountDTO;
import com.assia.ebankingbackend.Dtos.CurrentBankAccountDTO;
import com.assia.ebankingbackend.Dtos.CustomerDTO;
import com.assia.ebankingbackend.Dtos.SavingBankAccountDTO;
import com.assia.ebankingbackend.Entities.*;
import com.assia.ebankingbackend.Repositories.AccountOperationRepository;
import com.assia.ebankingbackend.Repositories.BankAccountRepository;
import com.assia.ebankingbackend.Repositories.CustomerRepository;
import com.assia.ebankingbackend.Services.BankAccountService;
import com.assia.ebankingbackend.Services.BankService;
import com.assia.ebankingbackend.enums.AccountStatus;
import com.assia.ebankingbackend.enums.OperationType;
import com.assia.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.assia.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.assia.ebankingbackend.exceptions.CustomerNotFountException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Assia","Salma","Imane").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomer().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

                } catch (CustomerNotFountException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts= bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i=0;i<10;i++){
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    }else {
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    try {
                        bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                    } catch (BalanceNotSufficentException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
   // @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Assia","Imane","Salma").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
             bankAccountRepository.findAll().forEach(acc ->{
                 for (int i =0; i<10 ; i++){
                     AccountOperation accountOperation = new AccountOperation();
                     accountOperation.setOperationDate(new Date());
                     accountOperation.setAmount(Math.random()*12000);
                     accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                     accountOperation.setBankAccount(acc);
                     accountOperationRepository.save(accountOperation);
                 }

             });

        };
    }

}
