package com.kodilla.transfersgateway.conector;

import com.kodilla.transfersgateway.conector.response.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountsProvider {

    private final AccountsConnector accountsConnector;


    public List<AccountDto> getAccountByNrb(String nrb){

        return accountsConnector.getAccountByNrb(nrb).getAccounts()
                .stream()
                .map(x -> new AccountDto(x.getNrb(),x.getCurrency(),x.getAvailableFunds()))
                .collect(Collectors.toList());
    }
}
