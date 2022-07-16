package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentAdviceDetailsMapperTest {

    private PaymentAdviceDetailsMapper paymentAdviceDetailsMapper;

    @BeforeEach
    public void setUp() {
        paymentAdviceDetailsMapper = new PaymentAdviceDetailsMapperImpl();
    }
}
