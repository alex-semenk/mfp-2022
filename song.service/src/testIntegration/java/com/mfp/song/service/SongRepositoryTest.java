package com.mfp.song.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
public class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Test
    public void findAll_shouldReturnAllInitialSongs() {
        assertEquals(4, songRepository.findAll().size());
    }

    @Test
    public void findById_shouldReturnSong() {
        // given
        Long songId = 1L;
        // when
        Song song = songRepository.findById(songId).orElse(null);
        // then
        assertNotNull(song);
        assertEquals(songId, song.getId());
        assertEquals("Believer", song.getName());
        assertEquals("Imagine Dragons", song.getArtist());
        assertEquals("Evolve", song.getAlbum());
        assertEquals(2017, song.getYear());
        assertEquals(225, song.getLength());
        assertEquals(1, song.getResourceId());
    }

    @Test
    @DirtiesContext
    public void save_shouldSaveNewSong() {
        // given
        Song song = new Song();
        song.setName("Monday");
        song.setArtist("Imagine Dragons");
        song.setAlbum("Mercury – Act 1");
        song.setYear(2021);
        song.setLength(188);
        song.setResourceId(5L);
        // when
        Song savedSong = songRepository.save(song);
        // then
        assertEquals(5, songRepository.findAll().size());
        assertNotNull(savedSong);
        assertEquals(5, savedSong.getId());
        assertEquals(song.getName(), savedSong.getName());
        assertEquals(song.getArtist(), savedSong.getArtist());
        assertEquals(song.getAlbum(), savedSong.getAlbum());
        assertEquals(song.getYear(), savedSong.getYear());
        assertEquals(song.getLength(), savedSong.getLength());
        assertEquals(song.getResourceId(), savedSong.getResourceId());
    }

    @Test
    @DirtiesContext
    public void save_shouldUpdateExistingSong() {
        // given
        Song song = new Song();
        song.setId(1L);
        song.setName("Monday");
        song.setArtist("Imagine Dragons");
        song.setAlbum("Mercury – Act 1");
        song.setYear(2021);
        song.setLength(188);
        song.setResourceId(5L);
        // when
        Song savedSong = songRepository.save(song);
        // then
        assertEquals(4, songRepository.findAll().size());
        assertNotNull(savedSong);
        assertEquals(song.getId(), savedSong.getId());
        assertEquals(song.getName(), savedSong.getName());
        assertEquals(song.getArtist(), savedSong.getArtist());
        assertEquals(song.getAlbum(), savedSong.getAlbum());
        assertEquals(song.getYear(), savedSong.getYear());
        assertEquals(song.getLength(), savedSong.getLength());
        assertEquals(song.getResourceId(), savedSong.getResourceId());
    }

    @Test
    @DirtiesContext
    public void deleteAllById_shouldDeleteSongs() {
        // given
        List<Long> songIdsToDelete = Arrays.asList(1L, 2L, 3L);
        // when
        songRepository.deleteAllById(songIdsToDelete);
        // then
        List<Song> songsRemained = songRepository.findAll();
        assertEquals(1, songsRemained.size());
        assertEquals(4, songsRemained.get(0).getId());
    }

    @Test
    @DirtiesContext
    public void deleteAllById_shouldThrowExceptionWhenSongDoesNotExist() {
        // given
        List<Long> songIdsToDelete = List.of(111L);
        assertThrows(EmptyResultDataAccessException.class, () -> songRepository.deleteAllById(songIdsToDelete));
        assertEquals(4, songRepository.findAll().size());
    }

}
