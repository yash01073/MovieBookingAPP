import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {
  successMessage: string = '';

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    // Retrieve the message from the URL query parameters
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['message'];
    });
  }
}
