package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentFileMapperTest {

    private PaymentFileMapper paymentFileMapper;

    @BeforeEach
    public void setUp() {
        paymentFileMapper = new PaymentFileMapperImpl();
    }
}
