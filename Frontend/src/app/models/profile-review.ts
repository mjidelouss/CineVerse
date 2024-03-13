import {Movie} from "./movie";

export interface ProfileReview {
  firstname: string,
  lastname: string,
  content: string,
  image: string,
  rating: number,
  timestamp: string,
  likes: any[]
  movie: Movie
}
