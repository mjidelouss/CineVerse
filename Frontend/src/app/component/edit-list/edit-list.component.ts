import {Component, OnInit} from '@angular/core';
import {Movie} from "../../models/movie";

@Component({
  selector: 'app-edit-list',
  templateUrl: './edit-list.component.html',
  styleUrls: ['./edit-list.component.scss']
})
export class EditListComponent implements OnInit {

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
