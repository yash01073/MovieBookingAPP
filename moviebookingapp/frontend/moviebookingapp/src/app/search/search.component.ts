import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{
  searchQuery: string = '';
  movieList: any[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
       
  }
  searchMovies(): void{
    const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/movies';

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + sessionStorage.getItem('jwtToken') // Include JWT token
    });

    this.http.get((`${apiUrl}/search/${this.searchQuery}`),{headers}).subscribe(
      (response: any) => {
        // Filter movies with remainingTickets > 0
        this.movieList = response.movieList.filter((movie: any) => movie.remainingTickets > 0);
      },
      error => {
        this.router.navigate(['/error'], {
          queryParams: { message: 'An error occured!' }
        });
      }
    );
  }

  bookMovie(movie: any) {
    // Navigate to the seat selection page with the entire movie object as a parameter
    this.router.navigate(['/seat-selection', { movie: JSON.stringify(movie) }]);
  }

}
