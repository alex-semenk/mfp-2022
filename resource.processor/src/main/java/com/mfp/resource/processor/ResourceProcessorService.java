package com.mfp.resource.processor;

import static java.util.stream.Collectors.toMap;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResourceProcessorService {

    public SongMetadata readSongMetadata(InputStreamSource source) {
        Metadata tikaMetadata = new Metadata();

        try (InputStream inputStream = source.getInputStream()) {
            Mp3Parser parser = new Mp3Parser();
            parser.parse(inputStream, new BodyContentHandler(), tikaMetadata, new ParseContext());
        } catch (Exception e) {
            throw new ResourceProcessorException("Could not read song metadata", e);
        }

        log.debug("Song metadata: {}", toString(tikaMetadata));

        return SongMetadata.builder()
            .name(tikaMetadata.get(DublinCore.TITLE))
            .artist(tikaMetadata.get(XMPDM.ARTIST))
            .album(tikaMetadata.get(XMPDM.ALBUM))
            .length(parseDouble(tikaMetadata.get(XMPDM.DURATION)).map(Math::ceil).map(Double::intValue).orElse(null))
            .year(parseInteger(tikaMetadata.get(XMPDM.RELEASE_DATE)).orElse(null))
            .build();
    }

    private String toString(Metadata tikaMetadata) {
        return Arrays.stream(tikaMetadata.names())
            .filter(name -> !tikaMetadata.get(name).isBlank())
            .collect(toMap(name -> name, tikaMetadata::get))
            .toString();
    }

    private Optional<Integer> parseInteger(String str) {
        try {
            return str == null ? Optional.empty() : Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            log.error("Failed to parse Integer", e);
            return Optional.empty();
        }
    }

    private Optional<Double> parseDouble(String str) {
        try {
            return str == null ? Optional.empty() : Optional.of(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            log.error("Failed to parse Double", e);
            return Optional.empty();
        }
    }

}
