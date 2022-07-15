package com.mfp.resource.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

@ExtendWith(MockitoExtension.class)
public class ResourceTopicListenerTest {

    @Mock
    private ResourceServiceClient resourceServiceClient;

    @Mock
    private SongServiceClient songServiceClient;

    @Mock
    private ResourceProcessorService resourceProcessorService;

    @InjectMocks
    private ResourceTopicListener resourceTopicListener;

    @Test
    public void test() {
        // given
        ResourceId resourceId = new ResourceId();
        resourceId.setId(1L);
        byte[] resourceData = new byte[0];
        SongMetadata songMetadata = SongMetadata.builder().name("test").build();
        when(resourceServiceClient.getResource(resourceId.getId())).thenReturn(resourceData);
        when(resourceProcessorService.readSongMetadata(new ByteArrayResource(resourceData))).thenReturn(songMetadata);
        when(songServiceClient.saveSong(songMetadata)).thenReturn(songMetadata);

        // when
        resourceTopicListener.resourceUploaded(resourceId);

        // then
        Mockito.verify(songServiceClient).saveSong(songMetadata);
        assertEquals(songMetadata.getResourceId(), resourceId.getId());
    }

}
