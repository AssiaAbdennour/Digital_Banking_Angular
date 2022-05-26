package com.assia.ebankingbackend.Services;

import com.assia.ebankingbackend.Entities.BankAccount;
import com.assia.ebankingbackend.Entities.CurrentAccount;
import com.assia.ebankingbackend.Entities.SavingAccount;
import com.assia.ebankingbackend.Repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public  void consulter(){
        BankAccount bankAccount=
                bankAccountRepository.findById("289cc6eb-3ad3-456b-b4a4-2971a7a68ef1").orElse(null);
        if (bankAccount!=null) {
            System.out.println("***************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Over Draft => " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate => " + ((SavingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println(op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());

            });
        }
    }
}
