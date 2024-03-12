import {Component, OnDestroy, OnInit} from '@angular/core';
import {TrendingMovie} from "../../models/trendingMovie";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../../service/auth.service";
import {WatchlistService} from "../../service/watchlist.service";

@Component({
  selector: 'app-pofile-lists',
  templateUrl: './pofile-lists.component.html',
  styleUrls: ['./pofile-lists.component.scss']
})
export class PofileListsComponent implements OnInit, OnDestroy{

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
