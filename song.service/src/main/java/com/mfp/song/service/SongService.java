package com.mfp.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public Song getSongById(Long songId) {
        return songRepository.findById(songId).get();
    }

    public void saveSong(Song song) {
        songRepository.save(song);
    }

    public void deleteSongsByIds(List<Long> songIdsToDeleted) {
        songRepository.deleteAllById(songIdsToDeleted);
    }

}
