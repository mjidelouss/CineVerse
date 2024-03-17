package com.cine.verse.service.impl;

import com.cine.verse.Dto.response.*;
import com.cine.verse.domain.Genre;
import com.cine.verse.domain.Movie;
import com.cine.verse.repository.GenreRepository;
import com.cine.verse.repository.MovieRepository;
import com.cine.verse.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Movie> getMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public List<Movie> getLastSixMovies() {
        return movieRepository.findTop6ByOrderByIdDesc();
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
        int currentPage = 1;
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
                        movie.setId(tmdbMovie.getId());

                        // Check if the movie already exists in the database
                        Optional<Movie> existingMovie = movieRepository.findByTitleAndYear(movie.getTitle(), movie.getYear());
                        if (!existingMovie.isPresent()) {
                            Integer parsedYear = parseYear(tmdbMovie.getRelease_date());
                            movie.setOverview(tmdbMovie.getOverview() != null ? tmdbMovie.getOverview() : "N/A");
                            movie.setTitle(tmdbMovie.getTitle() != null ? tmdbMovie.getTitle() : "N/A");
                            movie.setYear(parsedYear != null ? parsedYear : 3000);
                            movie.setImage(tmdbMovie.getPoster_path() != null ? tmdbMovie.getPoster_path() : "N/A");
                            MovieDetailsTrailer movieDetailsTrailer = getMovieDetailsTrailer(tmdbMovie.getId());
                            MovieCredits movieCredits = getMovieCredits(tmdbMovie.getId());
                            movie.setGenres(saveGenreIfExist(movieDetailsTrailer.getGenres()));
                            movie.setMovie_background(movieDetailsTrailer.getMovie_background());
                            movie.setLanguage(movieDetailsTrailer.getLanguage());
                            movie.setBudget(movieDetailsTrailer.getBudget());
                            movie.setTrailer(movieDetailsTrailer.getTrailer());
                            movie.setObjectData(movieCredits);
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

        // Log the last page number
        System.out.println("Last processed page: " + (currentPage - 1));

        return moviesSavedCount;
    }

    @Override
    public List<Movie> getTrendingMovies() {
        List<Movie> trendingMoviesList = new ArrayList<>();
        int moviesToFetch = 6;

        String apiUrl = "https://api.themoviedb.org/3/discover/movie?api_key=" + tmdbApiKey + "&page=1";

        ResponseEntity<TmdbApiResponse> responseEntity = restTemplate.getForEntity(apiUrl, TmdbApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            TmdbApiResponse tmdbApiResponse = responseEntity.getBody();

            if (tmdbApiResponse != null && tmdbApiResponse.getResults() != null) {
                for (int i = 0; i < Math.min(moviesToFetch, tmdbApiResponse.getResults().size()); i++) {
                    TmdbMovie tmdbMovie = tmdbApiResponse.getResults().get(i);

                    Movie movie = new Movie();
                    movie.setId(tmdbMovie.getId());
                    // Check if the movie already exists in the database
                    Optional<Movie> existingMovie = movieRepository.findByTitleAndYear(movie.getTitle(), movie.getYear());
                    if (!existingMovie.isPresent()) {
                        Integer parsedYear = parseYear(tmdbMovie.getRelease_date());
                        movie.setOverview(tmdbMovie.getOverview() != null ? tmdbMovie.getOverview() : "N/A");
                        movie.setTitle(tmdbMovie.getTitle() != null ? tmdbMovie.getTitle() : "N/A");
                        movie.setYear(parsedYear != null ? parsedYear : 3000);
                        movie.setImage(tmdbMovie.getPoster_path() != null ? tmdbMovie.getPoster_path() : "N/A");
                        trendingMoviesList.add(movie);
                        MovieDetailsTrailer movieDetailsTrailer = getMovieDetailsTrailer(tmdbMovie.getId());
                        MovieCredits movieCredits = getMovieCredits(tmdbMovie.getId());
                        movie.setGenres(saveGenreIfExist(movieDetailsTrailer.getGenres()));
                        movie.setMovie_background(movieDetailsTrailer.getMovie_background());
                        movie.setLanguage(movieDetailsTrailer.getLanguage());
                        movie.setBudget(movieDetailsTrailer.getBudget());
                        movie.setTrailer(movieDetailsTrailer.getTrailer());
                        movie.setObjectData(movieCredits);
                        movieRepository.save(movie);
                    }
                }
            } else {
                System.err.println("Tmdb Response is Null");
            }
        } else {
            System.err.println("Error fetching");
        }
        return trendingMoviesList;
    }

    public MovieDetailsTrailer getMovieDetailsTrailer(Long movieId) {
        MovieDetailsTrailer movie = new MovieDetailsTrailer();
        String apiUrl = "http://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + tmdbApiKey + "&append_to_response=videos";
        ResponseEntity<TmdbMovieDetailsTrailerResponse> responseEntity = restTemplate.getForEntity(apiUrl, TmdbMovieDetailsTrailerResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            TmdbMovieDetailsTrailerResponse tmdbApiResponse = responseEntity.getBody();

            if (tmdbApiResponse != null) {
                movie.setId(tmdbApiResponse.getId());
                movie.setGenres(saveGenreIfExist(tmdbApiResponse.getGenres()));
                movie.setMovie_background(tmdbApiResponse.getBackdrop_path());
                movie.setBudget(tmdbApiResponse.getBudget());
                if (!tmdbApiResponse.getSpoken_languages().isEmpty()) {
                    movie.setLanguage(tmdbApiResponse.getSpoken_languages().get(0).get("name"));
                }
                List<Map<String, Object>> videos = (List<Map<String, Object>>) tmdbApiResponse.getVideos().get("results");
                for (Map<String, Object> video : videos) {
                    String type = (String) video.get("type");
                    if ("Trailer".equals(type)) {
                        movie.setTrailer((String) video.get("key"));
                        break;
                    }
                }
            }
        }
        return movie;
    }

    public MovieCredits getMovieCredits(Long movieId) {
        MovieCredits movieCredits = new MovieCredits();
        String apiUrl = "https://api.themoviedb.org/3/movie/"+ movieId + "/credits?api_key=" + tmdbApiKey;
        ResponseEntity<TmdbCreditsResponse> responseEntity = restTemplate.getForEntity(apiUrl, TmdbCreditsResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            TmdbCreditsResponse tmdbApiResponse = responseEntity.getBody();

            if (tmdbApiResponse != null) {
                movieCredits.setId(tmdbApiResponse.getId());
                List<String> originalNames = new ArrayList<>();
                List<String> stuntsList = new ArrayList<>();
                List<String> visualEffectsList = new ArrayList<>();
                List<String> soundList = new ArrayList<>();
                List<String> cameraOperatorsList = new ArrayList<>();
                List<String> hairstylingList = new ArrayList<>();
                List<String> castingList = new ArrayList<>();
                List<String> producersList = new ArrayList<>();
                List<String> execProducersList = new ArrayList<>();
                List<String> setDecorationList = new ArrayList<>();
                for (Map<String, Object> castMember : tmdbApiResponse.getCast()) {
                    originalNames.add((String) castMember.get("original_name"));
                }
                movieCredits.setCast(originalNames);
                for (Map<String, Object> crewMember : tmdbApiResponse.getCrew()) {
                    if ("Original Music Composer".equals(crewMember.get("job"))) {
                        movieCredits.setComposer((String) crewMember.get("original_name"));
                    }
                    if ("Second Unit Director".equals(crewMember.get("job"))) {
                        movieCredits.setAdd_director((String) crewMember.get("original_name"));
                    }
                    if ("Director".equals(crewMember.get("job"))) {
                        movieCredits.setDirector((String) crewMember.get("original_name"));
                    }
                    if ("First Assistant Director".equals(crewMember.get("job"))) {
                        movieCredits.setAssistant_director((String) crewMember.get("original_name"));
                    }
                    if ("Costume Design".equals(crewMember.get("job"))) {
                        movieCredits.setCostume_design((String) crewMember.get("original_name"));
                    }
                    if ("Director of Photography".equals(crewMember.get("job"))) {
                        movieCredits.setCinematography((String) crewMember.get("original_name"));
                    }
                    if ("Gaffer".equals(crewMember.get("job"))) {
                        movieCredits.setLighting((String) crewMember.get("original_name"));
                    }
                    if ("Writer".equals(crewMember.get("job"))) {
                        movieCredits.setWriter((String) crewMember.get("original_name"));
                    }
                    if ("Stunts".equals(crewMember.get("job"))) {
                        stuntsList.add((String) crewMember.get("original_name"));
                    }
                    if ("Visual Effects".equals(crewMember.get("department"))) {
                        visualEffectsList.add((String) crewMember.get("original_name"));
                    }
                    if ("Sound".equals(crewMember.get("department"))) {
                        if (crewMember.get("job").toString().contains("Sound ")) {
                            soundList.add((String) crewMember.get("original_name"));
                        }
                    }
                    if ("Camera".equals(crewMember.get("department"))) {
                        if (crewMember.get("job").toString().contains(" Operator")) {
                            cameraOperatorsList.add((String) crewMember.get("original_name"));
                        }
                    }
                    if ("Makeup Department Head".equals(crewMember.get("job"))) {
                        movieCredits.setMakeup((String) crewMember.get("original_name"));
                    }
                    if ("Hairstylist".equals(crewMember.get("job"))) {
                        hairstylingList.add((String) crewMember.get("original_name"));
                    }
                    if ("Casting".equals(crewMember.get("job"))) {
                        castingList.add((String) crewMember.get("original_name"));
                    }
                    if ("Editor".equals(crewMember.get("job"))) {
                        movieCredits.setEditor((String) crewMember.get("original_name"));
                    }
                    if ("Producer".equals(crewMember.get("job"))) {
                        producersList.add((String) crewMember.get("original_name"));
                    }
                    if ("Executive Producer".equals(crewMember.get("job"))) {
                        execProducersList.add((String) crewMember.get("original_name"));
                    }
                    if (crewMember.get("job").toString().contains("Set Decoration")) {
                        setDecorationList.add((String) crewMember.get("original_name"));
                    }
                }
                movieCredits.setStunts(stuntsList);
                movieCredits.setVisual_effects(visualEffectsList);
                movieCredits.setCasting(castingList);
                movieCredits.setHairstyling(hairstylingList);
                movieCredits.setProducers(producersList);
                movieCredits.setExec_producers(execProducersList);
                movieCredits.setSound(soundList);
                movieCredits.setSet_decoration(setDecorationList);
                movieCredits.setCamera_operators(cameraOperatorsList);
            }
        }
        return movieCredits;
    }

    public List<Movie> getSimilarMovies(Long movieId) {
        List<Movie> similarMoviesList = new ArrayList<>();
        int moviesToFetch = 6;

        String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "/recommendations?api_key=" + tmdbApiKey;

        ResponseEntity<TmdbApiResponse> responseEntity = restTemplate.getForEntity(apiUrl, TmdbApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            TmdbApiResponse tmdbApiResponse = responseEntity.getBody();

            if (tmdbApiResponse != null && tmdbApiResponse.getResults() != null) {
                for (int i = 0; i < Math.min(moviesToFetch, tmdbApiResponse.getResults().size()); i++) {
                    TmdbMovie tmdbMovie = tmdbApiResponse.getResults().get(i);

                    Movie movie = new Movie();
                    movie.setId(tmdbMovie.getId());
                    // Check if the movie already exists in the database
                    Optional<Movie> existingMovie = movieRepository.findByTitleAndYear(movie.getTitle(), movie.getYear());
                    if (!existingMovie.isPresent()) {
                        Integer parsedYear = parseYear(tmdbMovie.getRelease_date());
                        movie.setOverview(tmdbMovie.getOverview() != null ? tmdbMovie.getOverview() : "N/A");
                        movie.setTitle(tmdbMovie.getTitle() != null ? tmdbMovie.getTitle() : "N/A");
                        movie.setYear(parsedYear != null ? parsedYear : 3000);
                        movie.setImage(tmdbMovie.getPoster_path() != null ? tmdbMovie.getPoster_path() : "N/A");
                        similarMoviesList.add(movie);
                        MovieDetailsTrailer movieDetailsTrailer = getMovieDetailsTrailer(tmdbMovie.getId());
                        MovieCredits movieCredits = getMovieCredits(tmdbMovie.getId());
                        movie.setGenres(saveGenreIfExist(movieDetailsTrailer.getGenres()));
                        movie.setMovie_background(movieDetailsTrailer.getMovie_background());
                        movie.setLanguage(movieDetailsTrailer.getLanguage());
                        movie.setBudget(movieDetailsTrailer.getBudget());
                        movie.setTrailer(movieDetailsTrailer.getTrailer());
                        movie.setObjectData(movieCredits);
                        movieRepository.save(movie);
                    }
                }
            } else {
                System.err.println("Tmdb Response is Null");
            }
        } else {
            System.err.println("Error fetching");
        }
        return similarMoviesList;
    }

    private Integer parseYear(String releaseDate) {
            if (releaseDate != null && releaseDate.length() >= 4) {
                return Integer.parseInt(releaseDate.substring(0, 4));
            }
            return null;
    }

    private Set<Genre> saveGenreIfExist(Set<Genre> genres){
        Set<Genre> genresCollect = new HashSet<>();
        Set<Genre> genresNew = new HashSet<>();
        for (Genre item : genres) {
            Long genreId = item.getId();
            Optional<Genre> existingGenre = genreRepository.findById(genreId);
            Genre genre;
            if (existingGenre.isPresent()) {
                genre = existingGenre.get();
            } else {
                genre = Genre.builder()
                        .id(genreId)
                        .name(item.getName())
                        .build();
                genresNew.add(genre);
            }
            genresCollect.add(genre);
        }
        genreRepository.saveAll(genresNew);
        return genresCollect;
    }
}
