import { Component , OnInit} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit{


movieList: any[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.fetchMovieList();
  }
  confirmUpdateMovie(movie: any) {
    const confirmation = window.confirm('Do you want to update the movie status?');

    if (confirmation) {
      // Call your update function here, passing movieId
      this.updateMovieStatus(movie);
    }
  }

  confirmDeleteMovie(movieId: string) {
    const confirmation = window.confirm('Do you want to delete the movie?');

    if (confirmation) {
      // Call your delete function here, passing movieId
      this.deleteMovie(movieId);
    }
  }

  logout(): void {
    // Implement the logout functionality here (clear the session storage, etc.)
    sessionStorage.removeItem('jwtToken');
    // Redirect to the login page or perform any other necessary actions
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
          queryParams: { message: 'Error Fetching Movie List!' }
        });
      }
    );
    }

   async updateMovieStatus(movie: any): Promise<any>{
    
      const updateRequest = {
        movieName: movie.movieName,
        theatreName: movie.theatreName,
        
      };
  
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + sessionStorage.getItem('jwtToken') // Include JWT token
      });
  
      const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/updateStatus';
  
      try {
        // Make the POST request with headers and the booking request data
        console.log(JSON.stringify(updateRequest));
        const response = await this.http.put(apiUrl, updateRequest, { headers }).toPromise();
        console.log(JSON.stringify(response));
        this.router.navigate(['/admin-dashboard'], {
        });
      } catch (error) {
        this.router.navigate(['/error'], {
          queryParams: { message: 'Updating Failed!' }
        });
      }
    }

    async deleteMovie(id: string){
      
  
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + sessionStorage.getItem('jwtToken') // Include JWT token
      });
  
      const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/delete';
  
      
        // Make the POST request with headers and the booking request data
        this.http.delete((`${apiUrl}/${id}`),{headers}).subscribe(
          (response: any) => {
            console.log(JSON.stringify(response));
          },
          error => {
            this.router.navigate(['/error'], {
              queryParams: { message: 'Delete Failed!' }
            });
          }
        );
      }
    }
  
