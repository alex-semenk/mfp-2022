package com.mfp.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/songs")
    public Song addSong(@RequestBody Song song) {
        song.setId(null); // generate new id on save
        songService.saveSong(song);
        return song;
    }

    @GetMapping("/songs/{id}")
    public Song getSong(@PathVariable("id") Long songId) {
        return songService.getSongById(songId);
    }

    @DeleteMapping("/songs")
    public SongIds deleteSongs(@RequestParam("id") List<Long> songIdsToDelete) {
        songService.deleteSongsByIds(songIdsToDelete);
        return new SongIds(songIdsToDelete);
    }

}
