package com.mfp.resource.processor;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamSource;

import static org.junit.jupiter.api.Assertions.*;

class ResourceProcessorServiceTest {

    private final ResourceProcessorService service = new ResourceProcessorService();

    @Test
    public void testReadSongMetadata() {
        InputStreamSource source = () -> getClass().getResourceAsStream("/Lakey Inspired - Better Days.mp3");
        SongMetadata metadata = service.readSongMetadata(source);

        assertEquals("Better Days", metadata.getName());
        assertEquals("Lakey Inspired", metadata.getArtist());
        assertEquals("Better Days", metadata.getAlbum());
        assertEquals(208, metadata.getLength());
        assertEquals(2018, metadata.getYear());
    }

}