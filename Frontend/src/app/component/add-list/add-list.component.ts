import {Component, OnInit} from '@angular/core';
import {Movie} from "../../models/movie";

@Component({
  selector: 'app-add-list',
  templateUrl: './add-list.component.html',
  styleUrls: ['./add-list.component.scss']
})
export class AddListComponent implements OnInit{

  searchResults: Movie[] = []
  listTitle: string = ''
  listDescription: string = ''
  searchTerm: string = ''
  constructor() {
  }
  ngOnInit(): void {
  }

  addMovieToList() {

  }

  searchMovies() {

  }

  addMovie(movie: Movie) {

  }

}
