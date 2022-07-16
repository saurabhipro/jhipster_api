package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatePaymentFileMapperTest {

    private CreatePaymentFileMapper createPaymentFileMapper;

    @BeforeEach
    public void setUp() {
        createPaymentFileMapper = new CreatePaymentFileMapperImpl();
    }
}
