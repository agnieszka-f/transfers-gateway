package com.kodilla.transfersgateway.service;

import com.kodilla.commons.Transfer;
import com.kodilla.commons.TransferMessage;
import com.kodilla.commons.UpdateBalances;
import com.kodilla.commons.UpdateBalancesMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferProducer {
    private static final String TRANSFER_TOPIC = "transfers";
    private static final String UPDATEBALANCE_TOPIC = "updateBalance";

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendTransfer(final Transfer transfer){
        TransferMessage transferMessage = new TransferMessage(transfer);

        log.info("Sending message to Kafka, transferMessage: {}", transferMessage );

        kafkaTemplate.send(TRANSFER_TOPIC,transferMessage);

        log.info("Message was send");
    }

    public void sendUpdateBalance(final UpdateBalances updateBalances){
        UpdateBalancesMessage updateBalancesMessage = new UpdateBalancesMessage(updateBalances);

        log.info("Sending message to Kafka, updateBalanceMessage: {}", updateBalancesMessage );

        kafkaTemplate.send(UPDATEBALANCE_TOPIC,updateBalancesMessage);

        log.info("Message was send");
    }
}
