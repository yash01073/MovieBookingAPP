import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const loginId = (document.getElementById('loginId') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;
    const apiUrl = 'https://moviebookingapp-261023.azurewebsites.net/api/login';

    const data = {
      loginId,
      password
    };

    this.http.post(apiUrl, data).subscribe((response: any) => {
      console.log('Login successful', response);
      // Handle successful login response here
      sessionStorage.setItem('jwtToken', response.access_token);
      console.log('Token', response.access_token);

        // Redirect to another component or route
        this.router.navigate(['/dashboard']);
    }, error => {
      this.router.navigate(['/error'], {
        queryParams: { message: 'Login Failed!' }
      });
      
    });
  }
}
