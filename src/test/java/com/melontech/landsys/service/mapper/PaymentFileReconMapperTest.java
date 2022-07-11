package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentFileReconMapperTest {

    private PaymentFileReconMapper paymentFileReconMapper;

    @BeforeEach
    public void setUp() {
        paymentFileReconMapper = new PaymentFileReconMapperImpl();
    }
}
