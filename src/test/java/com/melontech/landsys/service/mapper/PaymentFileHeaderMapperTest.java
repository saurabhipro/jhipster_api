package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentFileHeaderMapperTest {

    private PaymentFileHeaderMapper paymentFileHeaderMapper;

    @BeforeEach
    public void setUp() {
        paymentFileHeaderMapper = new PaymentFileHeaderMapperImpl();
    }
}
