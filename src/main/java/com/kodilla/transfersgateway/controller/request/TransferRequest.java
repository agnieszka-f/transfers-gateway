package com.kodilla.transfersgateway.controller.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String senderAccount;
    private String recipientAccount;
    private String title;
    private BigDecimal amount;
}
