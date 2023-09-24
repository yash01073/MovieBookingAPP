import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  movieList: any[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.fetchMovieList();
  }

  fetchMovieList() {
    const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/all';

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + sessionStorage.getItem('jwtToken') // Include JWT token
    });

    this.http.get((apiUrl),{headers}).subscribe(
      (response: any) => {
        // Filter movies with remainingTickets > 0
        this.movieList = response.movieList.filter((movie: any) => movie.remainingTickets > 0);
      },
      error => {
        this.router.navigate(['/error'], {
          queryParams: { message: 'No Shows Available!' }
        });
      }
    );
  }

  bookMovie(movie: any) {
    // Navigate to the seat selection page with the entire movie object as a parameter
    this.router.navigate(['/seat-selection', { movie: JSON.stringify(movie) }]);
  }

}
