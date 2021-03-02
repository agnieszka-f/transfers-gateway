package com.kodilla.transfersgateway.conector.response;

import lombok.Data;

import java.util.List;

@Data
public class GetAccountsResponse {

    private List<AccountDto> accounts;
}
