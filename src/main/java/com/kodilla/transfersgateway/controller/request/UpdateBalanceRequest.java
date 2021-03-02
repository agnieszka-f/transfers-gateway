package com.kodilla.transfersgateway.controller.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateBalanceRequest {
    private String nrb;
    private BigDecimal balance;
}
