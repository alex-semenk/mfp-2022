package com.mfp.resource.processor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongMetadata {

    @JsonInclude(Include.NON_NULL)
    private Long id;
    private String name;
    private String artist;
    private String album;
    private Integer length;
    private Integer year;
    private Long resourceId;

}
