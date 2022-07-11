package com.mfp.song.service;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { SongServiceSteps.class })
@ActiveProfiles("test")
@Import(SongServiceClient.class)
public class CucumberBootstrap {
}
