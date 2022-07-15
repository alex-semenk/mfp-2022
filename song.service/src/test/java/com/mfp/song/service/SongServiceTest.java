package com.mfp.song.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    @InjectMocks
    private SongService songService;

    @Mock
    private SongRepository songRepository;

    @Test
    void getSongById_givenSongId_shouldReturnSongAsIsFromRepository() {
        // given
        Long songId = 1L;
        Song originalSong = new Song();
        when(songRepository.findById(songId)).thenReturn(Optional.of(originalSong));

        // when
        Song actualSong = songService.getSongById(songId);

        // then
        assertEquals(originalSong, actualSong);
    }

    @Test
    void saveSong_givenSong_shouldCallSaveInRepositoryWithSongProvided() {
        // given
        Song song = new Song();
        // when
        songService.saveSong(song);
        // then
        verify(songRepository).save(song);
    }

    @Test
    void deleteSongsByIds_givenSongIds_shouldCallRepositoryToDeleteSongsByIdsProvided() {
        // given
        List<Long> songIdsToDelete = Arrays.asList(1L, 2L, 3L);
        // when
        songService.deleteSongsByIds(songIdsToDelete);
        // then
        verify(songRepository).deleteAllById(songIdsToDelete);
    }
}