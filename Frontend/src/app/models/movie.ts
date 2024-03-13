import {MovieCredits} from "./movie-credits";
import {Genre} from "./genre";

export interface Movie {
  id: number;
  title: string;
  year: number;
  director: string | null;
  image: string;
  movie_background: string;
  budget: number;
  trailer: string;
  movieData: string;
  overview: string;
  genres: Genre[];
  reviews: any[];
  watchLists: any[];
  movieLists: any[];
  language: string;
}
