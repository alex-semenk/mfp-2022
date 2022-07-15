package com.mfp.song.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SongServiceSteps {

    private Song songResponse;

    private List<Long> songIdsResponse;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    protected SongServiceClient songServiceClient;

    @Before
    public void beforeScenario() {
        log.info(">>> Before scenario!");
        // songRepository.deleteAll();
    }

    @BeforeStep
    public void beforeStep() {
        log.info(">>> BeforeStep!");
    }

    @After
    public void afterScenario() {
        log.info(">>> cleaning up after scenario!");
    }

    @AfterStep
    public void afterStep() {
        log.info(">>> AfterStep!");
    }

    @Given("the collection of songs:")
    public void givenTheCollectionOfSongs(List<Song> songs) {
        songRepository.saveAll(songs);
    }

    @When("song id {long} is passed to get the song metadata")
    public void whenSongIdIsPassedToGetTheSongMetadata(Long songId) {
        songResponse = songServiceClient.getSong(songId);
    }

    @Then("song metadata is returned:")
    public void thenSongMetadataIsReturned(Song song) {
        assertThat(songResponse).isEqualTo(song);
    }

    @Then("song metadata is persisted:")
    public void thenSongMetadataIsPersisted(List<Song> songs) {
        songs.forEach(song -> {
            Optional<Song> songOptional = songRepository.findById(song.getId());
            assertThat(songOptional).isPresent();
            assertThat(song).isEqualTo(songOptional.get());
        });
    }

    @When("song metadata is saved:")
    public void whenSongMetadataIsSaved(Song song) {
        songResponse = songServiceClient.addSong(song);
    }

    @When("song id list is passed to delete song metadata:")
    public void whenSongIdsArePassedToDeleteSongMetadata(@Transpose List<Long> songIds) {
        songIdsResponse = songServiceClient.deleteSongs(songIds).getIds();
    }

    @Then("song metadata is not persisted for song id list:")
    public void thenSongMetadataIsNotPersisted(@Transpose List<Long> songIds) {
        songIds.forEach(songId -> {
            Optional<Song> songOptional = songRepository.findById(songId);
            assertThat(songOptional).isEmpty();
        });
    }

    @Then("song id list is returned:")
    public void thenSongIdListIsReturned(@Transpose List<Long> songIds) {
        assertThat(songIdsResponse).isEqualTo(songIds);
    }

}