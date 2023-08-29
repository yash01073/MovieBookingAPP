import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
//import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerData = {
    loginId: '',
    firstName: '',
    lastName: '',
    contactNumber: '',
    email: '',
    password: '',
    confirmPassword: '',
    roles: ['user']
  };

  passwordsMatch = true;

  constructor(private http: HttpClient, private router: Router) {}

  register() {
    
    if (this.registerData.password !== this.registerData.confirmPassword) {
      this.passwordsMatch = false; // Set the flag to false
      return;
    }else {
      this.passwordsMatch = true;
    }
    const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/register';

    this.http.post(apiUrl, this.registerData).subscribe(
      response => {
        console.log('Registration successful', response);
        //this.showSuccessModal();
        this.router.navigate(['']);
      },
      error => {
        console.error('Registration failed', error);
        //this.showErrorModal();
      }
    );
  }

  // showSuccessModal() {
  //   this.modalService.open(SuccessModalComponent);
  // }

  // showErrorModal() {
  //   this.modalService.open(ErrorModalComponent);
  // }
}
