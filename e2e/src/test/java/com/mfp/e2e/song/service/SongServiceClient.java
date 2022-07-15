package com.mfp.e2e.song.service;

import java.util.List;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@TestComponent
@FeignClient(name = "song.service", url = "${song-service.url}", path= "/songs")
public interface SongServiceClient {
    
    @PostMapping
    Song addSong(Song song);

    @GetMapping("/{id}")
    Song getSong(@PathVariable("id") Long songId);

    @DeleteMapping
    SongIds deleteSongs(@RequestParam("id") List<Long> songIdsToDelete);

    @GetMapping
    List<Song> searchSongs(@RequestParam("resourceId") Long resourceId);

}
