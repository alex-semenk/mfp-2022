package com.mfp.resource.processor;

import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> attrs = new HashMap<>();
        for (String name: tikaMetadata.names()) {
            attrs.put(name, tikaMetadata.get(name));
        }
        System.out.println(attrs);

        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setName(tikaMetadata.get(DublinCore.TITLE));
        songMetadata.setArtist(tikaMetadata.get(XMPDM.ARTIST));
        songMetadata.setAlbum(tikaMetadata.get(XMPDM.ALBUM));
        songMetadata.setLength(getDuration(tikaMetadata));
        songMetadata.setYear(getYear(tikaMetadata));

        return songMetadata;
    }

    private Integer getYear(Metadata metadata) {
        String value = metadata.get(XMPDM.RELEASE_DATE);
        try {
            return value == null ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    private Integer getDuration(Metadata metadata) {
        String value = metadata.get(XMPDM.DURATION);
        if (value == null) {
            return null;
        }
        try {
            return (int) Math.ceil(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
