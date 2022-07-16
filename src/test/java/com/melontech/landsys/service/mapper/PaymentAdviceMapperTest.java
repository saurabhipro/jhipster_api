package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentAdviceMapperTest {

    private PaymentAdviceMapper paymentAdviceMapper;

    @BeforeEach
    public void setUp() {
        paymentAdviceMapper = new PaymentAdviceMapperImpl();
    }
}
