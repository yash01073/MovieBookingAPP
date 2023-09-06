import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor() { }

  ngOnInit(): void {
  }

  isLoggedIn(): boolean {
    // Check if a token exists in session storage
    const token = sessionStorage.getItem('jwtToken');
    return token !== null;
  }

  logout(): void {
    // Implement the logout functionality here (clear the session storage, etc.)
    sessionStorage.removeItem('jwtToken');
    // Redirect to the login page or perform any other necessary actions
  }

}
