package com.mfp.song.service;

import java.util.List;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@TestComponent
@FeignClient(name = "song.service", url = "http://localhost:${server.port}/")
public interface SongServiceClient {
    
    @PostMapping(path = "/songs")
    Song addSong(Song song);

    @GetMapping("/songs/{id}")
    Song getSong(@PathVariable("id") Long songId);

    @DeleteMapping("/songs")
    SongIds deleteSongs(@RequestParam("id") List<Long> songIdsToDelete);

}
