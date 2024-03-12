import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-pofile-reviews',
  templateUrl: './pofile-reviews.component.html',
  styleUrls: ['./pofile-reviews.component.scss']
})
export class PofileReviewsComponent implements OnInit, OnDestroy{

  userId!: number;

  constructor(private route: ActivatedRoute,private router: Router, public dialog: MatDialog) {

  }
  ngOnDestroy(): void {
  }

  onMovieClick(movieId: number): void {
    this.router.navigate(['/movie', movieId]);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
    });
  }
}
