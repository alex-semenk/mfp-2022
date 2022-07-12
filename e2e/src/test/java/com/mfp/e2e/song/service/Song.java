package com.mfp.e2e.song.service;

import lombok.Data;

@Data
public class Song {

    private Long id;
    private String name;
    private String artist;
    private String album;
    /**
     * Song length in seconds
     */
    private Integer length;
    private Integer year;
    private Long resourceId;
}
