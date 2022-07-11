package com.mfp.song.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SongControllerTest {

    @InjectMocks
    SongController songController;

    @Mock
    private SongService songService;

    @Test
    void addSong_whenSongHasId_songShouldBeSavedWithoutId() {
        // given
        Song song = new Song();
        song.setId(1L);
        // when
        songController.addSong(song);
        // then
        verify(songService).saveSong(song);
        assertNull(song.getId());
    }

    @Test
    void getSong_givenSongId_shouldReturnSongAsIsFromService() {
        // given
        Long songId = 1L;
        Song originalSong = new Song();
        when(songService.getSongById(songId)).thenReturn(originalSong);
        // when
        Song actualSong = songController.getSong(songId);
        // then
        assertEquals(originalSong, actualSong);
    }

    @Test
    void deleteSongs_givenIdsList_shouldCallServiceToDeleteSongs() {
        // given
        List<Long> songIdsToDelete = Arrays.asList(1L, 2L, 3L);
        // when
        SongIds deletedSongIds = songController.deleteSongs(songIdsToDelete);
        // then
        verify(songService).deleteSongsByIds(songIdsToDelete);
        assertEquals(songIdsToDelete, deletedSongIds.getIds());
    }
}