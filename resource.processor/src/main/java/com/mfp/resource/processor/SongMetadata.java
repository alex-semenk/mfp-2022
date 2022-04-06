package com.mfp.resource.processor;

import lombok.Data;

@Data
public class SongMetadata {

    private String name;
    private String artist;
    private String album;
    private Integer length;
    private Integer year;

}
