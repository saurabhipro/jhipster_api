package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionHistoryMapperTest {

    private TransactionHistoryMapper transactionHistoryMapper;

    @BeforeEach
    public void setUp() {
        transactionHistoryMapper = new TransactionHistoryMapperImpl();
    }
}
