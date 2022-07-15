package com.mfp.e2e;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.nio.file.Path;
import java.util.List;

import com.mfp.e2e.resource.service.ResourceServiceClient;
import com.mfp.e2e.song.service.Song;
import com.mfp.e2e.song.service.SongServiceClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

public class CucumberSteps {

    private Long uploadedResourceId;

    private List<Song> songsResponse;

    @Autowired
    private ResourceServiceClient resourceServiceClient;

    @Autowired
    private SongServiceClient songServiceClient;

    @Given("resource {string} is uploaded")
    public void given(String resourceLocation) throws Exception {
        var resource = getClass().getResourceAsStream(resourceLocation);
        var fileName = Path.of(resourceLocation).getFileName().toString();
        var multipartFile = new MockMultipartFile(fileName, fileName, "audio/mpeg", resource);
        uploadedResourceId = resourceServiceClient.uploadResource(multipartFile).getId();
    }

    @When("request to get song metadata by uploaded resource id returns not empty results")
    public void when() {
        await().atMost(10, SECONDS).until(this::getSongMetadataByUploadedResourceIdReturnsNotEmptyResults);
    }

    private boolean getSongMetadataByUploadedResourceIdReturnsNotEmptyResults() {
        songsResponse = songServiceClient.searchSongs(uploadedResourceId);
        return !songsResponse.isEmpty();
    }

    @Then("verify song metadata response body with uploaded resource id:")
    public void then(Song expectedSong) {
        expectedSong.setResourceId(uploadedResourceId);
        assertThat(songsResponse).hasSize(1);
        Song actualSong = songsResponse.get(0);
        assertThat(actualSong)
            .usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(expectedSong);
    }

}
