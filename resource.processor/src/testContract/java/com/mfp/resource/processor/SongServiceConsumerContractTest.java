package com.mfp.resource.processor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "song.service", hostInterface="localhost", port = "8082")
public class SongServiceConsumerContractTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SongServiceClient songServiceClient;

    @Pact(consumer = "resource.processor")
    public RequestResponsePact saveSongPact(PactDslWithProvider builder) {
        SongMetadata savedSongMetadata = saveSongPactSavedSongMetadata();
        return builder
            .given("test save song")
            .uponReceiving("POST request to save new song metadata")
            .method("POST")
            .headers(Map.of("Content-Type", "application/json"))
            .body(toJson(saveSongPactSongMetadata()))
            .path("/songs")
            .willRespondWith()
            .status(201)
            .headers(Map.of("Content-Type", "application/json"))
            .body(new PactDslJsonBody()
                .id("id", savedSongMetadata.getId())
                .stringValue("name", savedSongMetadata.getName())
                .stringValue("artist", savedSongMetadata.getArtist())
                .stringValue("album", savedSongMetadata.getAlbum())
                .numberValue("length", savedSongMetadata.getLength())
                .numberValue("year", savedSongMetadata.getYear())
                .numberValue("resourceId", savedSongMetadata.getResourceId()))
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "saveSongPact")
    public void givenSongMetadata_whenSendCreateRequest_shouldReturnSavedSong() {
        // given
        var sourceSong = saveSongPactSongMetadata();
        var expectedSavedSong = saveSongPactSavedSongMetadata();
        // when
        var savedSong = songServiceClient.saveSong(sourceSong);
        //then
        assertThat(savedSong).isEqualTo(expectedSavedSong);
    }

    private SongMetadata saveSongPactSongMetadata() {
        return SongMetadata.builder()
            .name("Better Days")
            .artist("Lakey Inspired")
            .album("Better Days")
            .length(208)
            .year(2018)
            .resourceId(42L)
            .build();
    }

    private SongMetadata saveSongPactSavedSongMetadata() {
        var savedSong = saveSongPactSongMetadata();
        savedSong.setId(24L);
        return savedSong;
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to write object to json: " + object, e);
        }
    }

}
