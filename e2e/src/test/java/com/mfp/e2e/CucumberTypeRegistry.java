package com.mfp.e2e;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfp.e2e.song.service.Song;
import io.cucumber.java.DataTableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


/**
 * Class used to convert DataTable to Java Object using Jackson.
 * It assumes that you use field names in Java POJO as Cucumber DataTable column headers.
 */
@Configurable
public class CucumberTypeRegistry {

    @Autowired
    private ObjectMapper objectMapper;

    @DataTableType
    public Song defineSong(Map<String, String> entry) {
        return objectMapper.convertValue(entry, Song.class);
    }

//    @DefaultParameterTransformer
//    @DefaultDataTableEntryTransformer
//    @DefaultDataTableCellTransformer
//    public Object transformer(Object fromValue, Type toValueType) {
//        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
//    }

}