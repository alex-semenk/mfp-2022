package com.mfp.e2e;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootTest
@EnableFeignClients
@CucumberContextConfiguration
public class CucumberConfiguration {
}
