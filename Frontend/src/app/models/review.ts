import {User} from "./user";
import {Movie} from "./movie";

export interface Review {
  user: User,
  movie: Movie,
  content: string,
  rating: number,
  watched: boolean
}
