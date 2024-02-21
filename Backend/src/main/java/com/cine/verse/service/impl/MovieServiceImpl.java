package com.cine.verse.service.impl;

import com.cine.verse.Dto.response.TmdbApiResponse;
import com.cine.verse.Dto.response.TmdbMovie;
import com.cine.verse.domain.Movie;
import com.cine.verse.repository.GenreRepository;
import com.cine.verse.repository.MovieRepository;
import com.cine.verse.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Autowired
    private RestTemplate restTemplate;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie, Long id) {
        return null;
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public int syncMovies() {
        int currentPage = 101;
        int moviesSavedCount = 0;
        final int MAX_PAGES = 42562;

        do {
            String apiUrl = "https://api.themoviedb.org/3/discover/movie?api_key=" + tmdbApiKey + "&page=" + currentPage;

            ResponseEntity<TmdbApiResponse> responseEntity = restTemplate.getForEntity(apiUrl, TmdbApiResponse.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                TmdbApiResponse tmdbApiResponse = responseEntity.getBody();

                System.out.println("Processing page: " + currentPage);

                if (tmdbApiResponse != null && tmdbApiResponse.getResults() != null) {
                    for (TmdbMovie tmdbMovie : tmdbApiResponse.getResults()) {
                        Movie movie = new Movie();
                        Integer parsedYear = parseYear(tmdbMovie.getRelease_date());
                        movie.setOverview(tmdbMovie.getOverview() != null ? tmdbMovie.getOverview() : "N/A");
                        movie.setTitle(tmdbMovie.getTitle() != null ? tmdbMovie.getTitle() : "N/A");
                        movie.setYear(parsedYear != null ? parsedYear : 3000);
                        movie.setImage(tmdbMovie.getPoster_path() != null ? tmdbMovie.getPoster_path() : "N/A");
                        if (tmdbMovie.getGenre_ids() != null && !tmdbMovie.getGenre_ids().isEmpty()) {
                            movie.setGenre(genreRepository.findById(tmdbMovie.getGenre_ids().get(0).longValue()).orElse(null));
                        } else {
                            movie.setGenre(genreRepository.findById(1L).orElse(null));
                        }

                        // Check if the movie already exists in the database
                        Optional<Movie> existingMovie = movieRepository.findByTitleAndYear(movie.getTitle(), movie.getYear());

                        if (!existingMovie.isPresent()) {
                            movieRepository.save(movie);
                            moviesSavedCount++;
                        }
                    }

                    // Increment the page number for the next iteration
                    currentPage++;
                } else {
                    // No more pages or an issue with the API response
                    break;
                }
            } else {
                // Handle non-OK response, e.g., log an error
                System.err.println("Error fetching page " + currentPage);
                break;
            }

        } while (currentPage <= MAX_PAGES);

        // Log the last page number to a file or another persistent storage
        System.out.println("Last processed page: " + (currentPage - 1));

        return moviesSavedCount;
    }

    private Integer parseYear(String releaseDate) {
            if (releaseDate != null && releaseDate.length() >= 4) {
                return Integer.parseInt(releaseDate.substring(0, 4));
            }
            return null;
        }
}
