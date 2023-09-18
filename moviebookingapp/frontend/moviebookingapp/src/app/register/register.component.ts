import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule,FormGroup, Validators, FormBuilder } from '@angular/forms';

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

  // Declare registerForm as a FormGroup property
  registerForm: FormGroup;

  constructor(private http: HttpClient, private router: Router,private formBuilder: FormBuilder) {
    // Initialize the registerForm property with the form controls and validators
    this.registerForm = this.formBuilder.group({
      contactNumber: ['', [Validators.required, Validators.pattern('[0-9]{10}')]],
      // Add other form controls here if needed
    });
  }

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
        this.router.navigate(['/error'], {
          queryParams: { message: 'Registration Failed!' }
        });
      }
    );
  }

}
