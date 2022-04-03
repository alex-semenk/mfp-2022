package com.mfp.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository bookRepository;

    public Song getSongById(Long songId) {
        return bookRepository.findById(songId).get();
    }

    public void saveSong(Song song) {
        bookRepository.save(song);
    }

    public void deleteSongsByIds(List<Long> songIdsToDeleted) {
        bookRepository.deleteAllById(songIdsToDeleted);
    }

}
