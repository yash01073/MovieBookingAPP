import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';



@Component({
  selector: 'app-seat-selection',
  templateUrl: './seat-selection.component.html',
  styleUrls: ['./seat-selection.component.css']
})
export class SeatSelectionComponent implements OnInit {
  seatNumbers: number[] = [];
  selectedSeats: number[] = [];
  movie: any;
  seatBooked: number[] = [];

  constructor(private http: HttpClient,private route: ActivatedRoute,private router: Router) {}

  ngOnInit() {
    // Extract movie ID from route parameter
    this.route.paramMap.subscribe(params => {
      const movieString = params.get('movie');
      if (movieString) {
        this.movie = JSON.parse(movieString);
      }
      this.initializeSeatData();
    });
  }

  initializeSeatData() {
    // Replace this with logic to fetch movie details and seat data based on movieId
    // Set seatNumbers and selectedSeats accordingly
    for (var i = 1; i <= this.movie.ticketsAllotted; i++) {
      this.seatNumbers.push(i);
   }
    this.seatBooked = this.movie.bookedSeatNumbers;
    
  }

  isSeatBooked(seatNumber: number): boolean {
    // Check if the seat is already booked
    return this.seatBooked.includes(seatNumber);
  }

  isSeatSelected(seatNumber: number): boolean {
    // Check if the seat is in the selectedSeats array
    return this.selectedSeats.includes(seatNumber);
  }

  toggleSeatSelection(seatNumber: number) {
    // Toggle seat selection
    if (this.isSeatSelected(seatNumber)) {
      // Deselect the seat
      this.selectedSeats = this.selectedSeats.filter(s => s !== seatNumber);
    } else {
      if(!this.isSeatBooked(seatNumber)){
        this.selectedSeats.push(seatNumber);
      }
      // Select the seat
      
    }
  }

  async submitBooking(): Promise<any> {
    // Construct the booking request body
    const bookingRequest = {
      movieName: this.movie.movieName,
      theatreName: this.movie.theatreName,
      numberOfSeats: this.selectedSeats.length,
      seatNumbers: this.selectedSeats
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + sessionStorage.getItem('jwtToken') // Include JWT token
    });

    const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/add';

    // Send the booking request to the API
    // Implement your HTTP POST request to the booking API here

    try {
      // Make the POST request with headers and the booking request data
      console.log(JSON.stringify(bookingRequest));
      const response = await this.http.post(apiUrl, bookingRequest, { headers }).toPromise();
      this.router.navigate(['/success'], {
        queryParams: { message: 'Booking successful!' }
      });
    } catch (error) {
      // Handle errors here
      this.router.navigate(['/error'], {
        queryParams: { message: 'Booking Failed.. Please Try Again.' }
      });
    }
  
    
}
}
