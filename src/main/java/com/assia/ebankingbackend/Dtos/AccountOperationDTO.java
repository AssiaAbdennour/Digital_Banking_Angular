package com.assia.ebankingbackend.Dtos;

import com.assia.ebankingbackend.enums.OperationType;
import lombok.Data;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType Type;
    private  String description;
}
