import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  // ... (component metadata)
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  errorMessage: string = '';

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    // Retrieve the error message from the URL query parameters
    this.route.queryParams.subscribe(params => {
      this.errorMessage = params['message'] || 'An error occurred!';
    });
  }
}
