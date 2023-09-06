import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {
  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const loginId = (document.getElementById('loginId') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;
    const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/login';

    const data = {
      loginId,
      password
    };

    this.http.post(apiUrl, data).subscribe((response: any) => {
      console.log('Login successful', response);
      // Handle successful login response here
      sessionStorage.setItem('jwtToken', response.access_token);
      console.log('Token', response.access_token);
      if(!response.roles.includes("ROLE_ADMIN")){
        this.router.navigate(['/error'], {
          queryParams: { message: 'Role is not Admin!' }
        });
      }else{
        // Redirect to another component or route
        this.router.navigate(['/admin-dashboard']);
      }
    }, error => {
      this.router.navigate(['/error'], {
        queryParams: { message: 'Login Failed!' }
      });
      
    });
  }
}
