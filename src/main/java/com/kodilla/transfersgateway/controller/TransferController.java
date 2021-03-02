package com.kodilla.transfersgateway.controller;

import com.kodilla.commons.Transfer;
import com.kodilla.commons.UpdateBalances;
import com.kodilla.transfersgateway.conector.AccountsProvider;
import com.kodilla.transfersgateway.conector.response.AccountDto;
import com.kodilla.transfersgateway.controller.request.TransferRequest;
import com.kodilla.transfersgateway.controller.request.UpdateBalanceRequest;
import com.kodilla.transfersgateway.service.TransferProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/transfers")
@RequiredArgsConstructor
public class TransferController
{
    private final TransferProducer transferProducer;
    private final AccountsProvider accountsProvider;
    @PostMapping
    public void saveTransfer(@RequestBody TransferRequest request) throws LackOfFundsException {

        List<AccountDto> response = accountsProvider.getAccountByNrb(request.getSenderAccount());

        if(response.get(0).getAvailableFunds().compareTo(request.getAmount())>=0) {

            log.info("Received transfer request: {}", request);
            Transfer transfer = new Transfer();
            transfer.setAmount(request.getAmount());
            transfer.setRecipientAccount(request.getRecipientAccount());
            transfer.setTitle(request.getTitle());
            transfer.setSenderAccount(request.getSenderAccount());

            transferProducer.sendTransfer(transfer);
            saveNewBalance(request);
        } else throw new LackOfFundsException();
    }


    private void saveNewBalance(TransferRequest request)  {

        List<AccountDto> response = accountsProvider.getAccountByNrb(request.getSenderAccount());
        BigDecimal newBalance = response.get(0).getAvailableFunds().subtract(request.getAmount());

            UpdateBalances updateBalance = new UpdateBalances();
            updateBalance.setBalance(newBalance);
            updateBalance.setNrb(request.getSenderAccount());

        log.info("Received updateBalance: {}", updateBalance);

            transferProducer.sendUpdateBalance(updateBalance);

    }
}

