import { Component } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {

  newPassword: string = "";
  username: string= "";

  constructor(private http: HttpClient,private router: Router) {}

  async resetPassword(): Promise<any> {
    // Construct the booking request body
    const changeRequest = {
      password: this.newPassword
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + sessionStorage.getItem('jwtToken') // Include JWT token
    });

    const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking';

    // Send the booking request to the API
    // Implement your HTTP POST request to the booking API here

    try {
      // Make the POST request with headers and the booking request data
      console.log(JSON.stringify(changeRequest));
      const response = await this.http.post(`${apiUrl}/${this.username}/forgot`, changeRequest, { headers }).toPromise();
      this.router.navigate(['/success'], {
        queryParams: { message: 'Password changed!' }
      });
    } catch (error: any) {
      // Handle errors here
      console.log("Error: ",error);
      if(error.error.message==="Username is not correct"){
        this.router.navigate(['/error'], {
          queryParams: { message: 'Username is not correct!' }
        });
      }else{
      this.router.navigate(['/error'], {
        queryParams: { message: 'An error occured!' }
      });
    }
    }
  }
}
