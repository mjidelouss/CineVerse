# CineVerse

- CineVerse is an application where users can review, rate, and discover films. Similar to platforms like Letterboxd.
- CineVerse provides a platform for movie enthusiasts to share their thoughts, discover new films, and connect with others who share similar interests.

# Features

- Film Reviews: Users can write reviews for movies they've watched, sharing their thoughts and opinions with the community.
- Rating System: Users can rate movies on a scale, helping others gauge the overall quality and popularity of a film.
- Recommendations: CineVerse provides personalized film recommendations based on user preferences, watch history, and community ratings.
- Community Interaction: Users can connect with other movie enthusiasts, follow their reviews, and engage in discussions about films.
- Watchlists: Users can create and manage watchlists to keep track of movies they want to watch in the future.
- Film Details: Detailed information about each movie, including synopsis, cast and crew, release date, and genre.

# Technologies Used

- Frontend: Angular (version 16)
- Backend: Spring Boot (version 3.1.0)
- Database: MySQL (version latest)
- Authentication: JSON Web Tokens (JWT)
- API Used: themoviedb (TMDB)
- Additional Tech: Docker, Postman

# End Points

# Movie Endpoints

`GET /api/v1/movie/all`

- Description: Retrieve a list of all movies.
- Query Parameters: - page (optional): The page number for pagination.
                    - size (optional): The number of items per page.
- Example: GET `/api/v1/movie/all?page=1&size=10`

`GET /api/v1/movie/filterByGenre/{userId}`

- Description: Retrieve movies filtered by genre.
- Path Parameters: - userId: The ID of the user requesting the movies.
- Query Parameters: - genre (optional): The genre to filter by.
- Example: GET `/api/v1/movie/filterByGenre/123?genre=action`

`GET /api/v1/movie/filterByDecade/{userId}`

- Description: Retrieve movies filtered by decade.
- Path Parameters: - userId: The ID of the user requesting the movies.
- Query Parameters: - decade: The decade to filter by.
- Example: GET `/api/v1/movie/filterByDecade/123?decade=1990`

`GET /api/v1/movie/search`

- Description: Search for movies based on a query term.
- Query Parameters: - query: The search term.
- Example: GET `/api/v1/movie/search?query=terminator`

`GET /api/v1/movie/last`

- Description: Retrieve the last six movies added.
- Example: GET `/api/v1/movie/last`

`GET /api/v1/movie/trending`

- Description: Retrieve trending movies.
- Example: GET `/api/v1/movie/trending`

`GET /api/v1/movie/count`

- Description: Get the total count of movies.
- Example: GET `/api/v1/movie/count`

`GET /api/v1/movie/similar/{id}`

- Description: Retrieve movies similar to a given movie.
- Path Parameters: - id: The ID of the movie.
- Example: GET `/api/v1/movie/similar/123`

`GET /api/v1/movie/{id}`

- Description: Retrieve details of a specific movie by ID.
- Path Parameters: - id: The ID of the movie.
- Example: GET `/api/v1/movie/123`

`DELETE /api/v1/movie/{id}`

- Description: Delete a movie by ID.
- Path Parameters: - id: The ID of the movie to delete.
- Example: DELETE `/api/v1/movie/123`

# Review Endpoints

`GET /api/v1/review/all`

- Description: Retrieve a list of all reviews.
- Query Parameters: - page (optional): The page number for pagination.
                    - size (optional): The number of items per page.
- Example: GET `/api/v1/review/all?page=1&size=10`

`GET /api/v1/review/recent/{movieId}`

- Description: Retrieve recent reviews for a specific movie.
- Path Parameters: - movieId: The ID of the movie.
- Example: GET `/api/v1/review/recent/123`

`GET /api/v1/review/user-review/{userId}`

- Description: Retrieve recent reviews by a specific user.
- Path Parameters:- userId: The ID of the user.
- Example: GET `/api/v1/review/user-review/456`

`GET /api/v1/review/{movieId}/{userId}`

- Description: Retrieve a review for a specific movie and user.
- Path Parameters: - movieId: The ID of the movie. 
                   - userId: The ID of the user.
- Example: GET `/api/v1/review/123/456`

`GET /api/v1/review/reviewed-movies/{userId}`

- Description: Retrieve movies reviewed by a specific user.
- Path Parameters: - userId: The ID of the user.
- Example: GET `/api/v1/review/reviewed-movies/789`

`GET /api/v1/review/{id}`

- Description: Retrieve details of a specific review by ID.
- Path Parameters: - id: The ID of the review.
- Example: GET `/api/v1/review/123`

`GET /api/v1/review/diary/{userId}`

- Description: Retrieve a user's review diary.
- Path Parameters: - userId: The ID of the user.
- Example: GET `/api/v1/review/diary/456`

`GET /api/v1/review/popular`

- Description: Retrieve popular reviews.
- Example: GET `/api/v1/review/popular`

`GET /api/v1/review/watch-count/{userId}`

- Description: Get the count of movies watched by a user.
- Path Parameters: - userId: The ID of the user.
- Example: GET `/api/v1/review/watch-count/789`

`GET /api/v1/review/like-count/{userId}`

- Description: Get the count of movies liked by a user.
- Path Parameters: - userId: The ID of the user.
- Example: GET `/api/v1/review/like-count/789`

`GET /api/v1/review/liked/{userId}`

- Description: Retrieve movies liked by a specific user.
- Path Parameters: - userId: The ID of the user.
- Example: GET `/api/v1/review/liked/789`

`GET /api/v1/review/count`

- Description: Get the total count of reviews.
- Example: GET `/api/v1/review/count`

`GET /api/v1/review/total-watched/{movieId}`

- Description: Get the total count of users who watched a movie.
- Path Parameters: - movieId: The ID of the movie.
- Example: GET `/api/v1/review/total-watched/123`

`GET /api/v1/review/total-liked/{movieId}`

- Description: Get the total count of users who liked a movie.
- Path Parameters: - movieId: The ID of the movie.
- Example: GET `/api/v1/review/total-liked/123`

`POST /api/v1/review`

- Description: Add a new review.
- Request Body: ReviewRequest
- Example: POST `/api/v1/review`

`POST /api/v1/review/rate/{num}`

- Description: Add a rating to a movie.
- Path Parameters: - num: The rating to add.
- Request Body: RateRequest
- Example: POST `/api/v1/review/rate/5`

`POST /api/v1/review/like/{bool}`

- Description: Like or dislike a movie.
- Path Parameters: - bool: true for like, false for dislike.
- Request Body: RateRequest
- Example: POST `/api/v1/review/like/true`

`POST /api/v1/review/watchlist/{bool}`

- Description: Add or remove a movie from the watchlist.
- Path Parameters: - bool: true for adding to watchlist, false for removing.
- Request Body: RateRequest
- Example: POST `/api/v1/review/watchlist/true`

`PUT /api/v1/review/{id}`

- Description: Update an existing review.
- Path Parameters: - id: The ID of the review to update.
- Request Body: ReviewRequest
- Example: PUT `/api/v1/review/123`

`DELETE /api/v1/review/{id}`

- Description: Delete a review by ID.
- Path Parameters: - id: The ID of the review to delete.
- Example: DELETE `/api/v1/review/123`

# Watchlist Endpoints

`GET /api/v1/watchlist/user/{id}`

- Description: Retrieve the watchlist for a specific user.
- Path Parameters: - id: The ID of the user.
- Example: GET `/api/v1/watchlist/user/123`

`GET /api/v1/watchlist/filterWatchedMoviesByGenre/{userId}`

- Description: Filter watched movies in the watchlist by genre for a specific user.
- Path Parameters: - userId: The ID of the user.
- Query Parameters: - genre (optional): The genre name to filter by.
- Example: GET `/api/v1/watchlist/filterWatchedMoviesByGenre/456?genre=action`

`GET /api/v1/watchlist/filterWatchedMoviesByDecade/{userId}`

- Description: Filter watched movies in the watchlist by decade for a specific user.
- Path Parameters: - userId: The ID of the user.
- Query Parameters: - decade: The decade to filter by.
- Example: GET `/api/v1/watchlist/filterWatchedMoviesByDecade/789?decade=2000s`

`POST /api/v1/watchlist/remove`

- Description: Remove a movie from the watchlist.
- Request Body: WatchListRequest
- Example: POST `/api/v1/watchlist/remove`

# User Endpoints

`GET /api/v1/user/{id}`

- Description: Retrieve user details by user ID.
- Path Parameters:
- id: The ID of the user.
- Example: GET `/api/v1/user/123`

`GET /api/v1/user/all`

- Description: Retrieve a list of users.
- Query Parameters: - page (optional): The page number for pagination (default: 0).
                    - size (optional): The number of users per page (default: 5).
- Example: GET `/api/v1/user/all?page=1&size=10`

`DELETE /api/v1/user/{id}`

- Description: Delete a user by user ID.
- Path Parameters: - id: The ID of the user.
- Example: `DELETE /api/v1/user/456`

`GET /api/v1/user/count`

- Description: Retrieve the total number of users by role.
- Example: GET `/api/v1/user/count`

`PUT /api/v1/user/profile/{userId}`

- Description: Update user profile information.
- Path Parameters: - userId: The ID of the user.
- Request Body: UpdateProfileDTO
- Example: PUT `/api/v1/user/profile/789`

# Getting Started

- To run the CineVerse application locally, follow these steps:

- Clone the Repository: git clone https://github.com/mjidelouss/CineVerse.git
- run command "docker compose up"

# Backend Setup:

- Navigate to the backend directory: cd backend
- Install dependencies: mvn clean install
- Run the backend server: mvn spring-boot:run

# Frontend Setup:

- Navigate to the frontend directory: cd frontend
- Install dependencies: npm install
- Run the frontend server: ng serve


# Contributing

Contributions are welcome! If you'd like to contribute to CineVerse, please follow these guidelines:

- Fork the repository
- Create a new branch for your feature or bug fix: git checkout -b feature-name
- Commit your changes: git commit -m 'Add new feature'
- Push to your branch: git push origin feature-name
- Submit a pull request

# License

- This project is licensed under the YouCode License.

# Contact

- For questions or inquiries about CineVerse, please contact mjid.elouss@gmail.com.