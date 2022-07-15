package com.mfp.e2e;

import com.mfp.e2e.song.service.SongServiceClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@ActiveProfiles("test")
@Import(SongServiceClient.class)
public class CucumberBootstrap {
}
