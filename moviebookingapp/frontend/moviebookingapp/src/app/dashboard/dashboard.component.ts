import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  movieList: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchMovieList();
  }

  fetchMovieList() {
    const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/all';

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + sessionStorage.getItem('jwtToken') // Include JWT token
    });

    this.http.get(apiUrl,{ headers }).subscribe(
      (response: any) => {
        this.movieList = response.movieList.filter((movie: any) => movie.remainingTickets > 0);
      },
      error => {
        console.error('Error fetching movie list', error);
      }
    );
  }
}
