package com.mfp.song.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
