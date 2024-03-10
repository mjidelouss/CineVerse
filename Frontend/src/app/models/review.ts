import {User} from "./user";
import {Movie} from "./movie";

export interface Review {
  userId: number,
  movieId: number,
  content: string
}
