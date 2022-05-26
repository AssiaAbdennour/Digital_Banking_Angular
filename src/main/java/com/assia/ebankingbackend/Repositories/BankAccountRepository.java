package com.assia.ebankingbackend.Repositories;

import com.assia.ebankingbackend.Entities.BankAccount;
import com.assia.ebankingbackend.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
