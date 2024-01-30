package com.fiap.techchallenge.streamingtech.usecase;

import com.fiap.techchallenge.streamingtech.model.Video;
import com.fiap.techchallenge.streamingtech.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoServiceImpl videoService;

    @Test
    void createVideo_Success() {
        Video testVideo = new Video();
        when(videoRepository.save(any((Video.class)))).thenReturn(Mono.just(testVideo));

        Mono<Video> result = videoService.createVideo(testVideo);

        assertNotNull(result.block());
        assertEquals(testVideo, result.block());
        verify(videoRepository, times(1)).save(testVideo);
    }

    @Test
    void updateVideo_Success() {
        Video updatedVideo = new Video();
        updatedVideo.setId("id");
        updatedVideo.setTitle("Novo Título");
        updatedVideo.setDescription("Nova Descrição");
        updatedVideo.setUrl("https://www.updated-url.com");
        updatedVideo.setPublicationDate(LocalDate.now());
        updatedVideo.setCategories(List.of("Nova Categoria"));
        updatedVideo.setFavorite(true);

        when(videoRepository.findById(anyString())).thenReturn(Mono.just(updatedVideo));
        when(videoRepository.save(any(Video.class))).thenReturn(Mono.just(updatedVideo));

        Mono<Video> result = videoService.updateVideo("id", updatedVideo);

        StepVerifier.create(result)
                .expectNext(updatedVideo)
                .verifyComplete();

        verify(videoRepository, times(1)).findById(anyString());
        verify(videoRepository, times(1)).save(updatedVideo);
    }

    @Test
    void updateVideo_VideoNotFound() {
        when(videoRepository.findById(anyString())).thenReturn(Mono.empty());

        Video updatedVideo = new Video();
        updatedVideo.setId(UUID.randomUUID().toString());

        Mono<Video> result = videoService.updateVideo(updatedVideo.getId(), updatedVideo);

        assertNull(result.block());

        verify(videoRepository, times(1)).findById(updatedVideo.getId());
        verify(videoRepository, never()).save(any(Video.class));
    }

    @Test
    void getAllVideos_Success() {
        Video video1 = new Video("id1", "Video 1", "Descrição 1", "https://www.example.com/1", LocalDate.now(), Arrays.asList("Categoria 1", "Categoria 2"), false);
        Video video2 = new Video("id2", "Video 2", "Descrição 2", "https://www.example.com/2", LocalDate.now().minusDays(1), Arrays.asList("Categoria 1"), true);
        Video video3 = new Video("id3", "Video 3", "Descrição 3", "https://www.example.com/3", LocalDate.now().minusDays(2), Arrays.asList("Categoria 2"), false);

        when(videoRepository.findAll()).thenReturn(Flux.just(video1, video2, video3));

        Flux<Video> result = videoService.getAllVideos(0, 2, "title");

        StepVerifier.create(result)
                .expectNext(video1, video2)
                .verifyComplete();

        verify(videoRepository, times(1)).findAll();
    }

    @Test
    void deleteVideo_Success() {
        String videoId = "id123";

        when(videoRepository.deleteById(videoId)).thenReturn(Mono.empty());

        Mono<Void> result = videoService.deleteVideo(videoId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(videoRepository, times(1)).deleteById(videoId);  // Verifica se deleteById foi chamado exatamente uma vez com o ID correto
    }

    @Test
    void getVideosByTitleAndDate_Success() throws ParseException {
        String title = "exampleTitle";
        String date = "2022-01-01";
        Video video1 = new Video("id1", title, "description1", "url1", LocalDate.parse(date), Arrays.asList("category1"), false);
        Video video2 = new Video("id2", title, "description2", "url2", LocalDate.parse(date).plusDays(1), Arrays.asList("category2"), true);

        when(videoRepository.findByTitleContainingIgnoreCaseAndPublicationDateAfter(eq(title), any())).thenReturn(Flux.just(video1, video2));

        Flux<Video> result = videoService.getVideosByTitleAndDate(title, date);

        StepVerifier.create(result)
                .expectNext(video1, video2)
                .verifyComplete();

        verify(videoRepository, times(1)).findByTitleContainingIgnoreCaseAndPublicationDateAfter(eq(title), any());
    }

    @Test
    void getVideosByCategory_Success() {
        String category = "exampleCategory";
        Video video1 = new Video("id1", "title1", "description1", "url1", LocalDate.now(), Arrays.asList(category), false);
        Video video2 = new Video("id2", "title2", "description2", "url2", LocalDate.now().plusDays(1), Arrays.asList(category), true);

        when(videoRepository.findByCategoriesContaining(eq(category))).thenReturn(Flux.just(video1, video2));

        Flux<Video> result = videoService.getVideosByCategory(category);

        StepVerifier.create(result)
                .expectNext(video1, video2)
                .verifyComplete();

        verify(videoRepository, times(1)).findByCategoriesContaining(eq(category));
    }

}