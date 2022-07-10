package com.mfp.song.service;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberBootstrap {
    @Autowired
    private SongServiceClient songServiceClient;
}
