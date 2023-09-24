import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ResetPasswordComponent } from './reset-password.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';

describe('ResetPasswordComponent', () => {
  let component: ResetPasswordComponent;
  let fixture: ComponentFixture<ResetPasswordComponent>;
  let httpTestingController: HttpTestingController;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResetPasswordComponent,FooterComponent,HeaderComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, FormsModule],
    });
    fixture = TestBed.createComponent(ResetPasswordComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpTestingController.verify(); // Ensure no outstanding requests
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should reset password successfully', () => {
    const newPassword = 'newPassword123';
    const username = 'yashsaxena';

    // Simulate the HTTP request and response
    const expectedResponse = { message: 'Password changed!' };
    component.newPassword = newPassword;
    component.username = username;

    // Spy on router.navigate method
    const navigateSpy = spyOn(router, 'navigate');

    // Trigger the resetPassword method
    component.resetPassword();

    // Expect an HTTP POST request to the correct URL with the expected data
    const req = httpTestingController.expectOne(
      `http://localhost:8080/api/v1.0/moviebooking/${username}/forgot`
    );
    expect(req.request.method).toEqual('POST');
    expect(req.request.headers.get('Content-Type')).toEqual('application/json');
    expect(req.request.headers.get('Authorization')).toContain('Bearer ');

    req.flush(expectedResponse); // Simulate a successful response from the server

    // Expect that the router.navigate method was called with the correct route
    //expect(navigateSpy).toHaveBeenCalledWith(['/success']);
     
  });

  it('should handle error when resetting password', () => {
    const newPassword = 'newPassword123';
    const username = 'testuser';

    // Simulate the HTTP request and response with an error
    component.newPassword = newPassword;
    component.username = username;

    // Spy on router.navigate method
    const navigateSpy = spyOn(router, 'navigate');

    // Trigger the resetPassword method
    component.resetPassword();

    // Expect an HTTP POST request to the correct URL with the expected data
    const req = httpTestingController.expectOne(
      `http://localhost:8080/api/v1.0/moviebooking/${username}/forgot`
    );
    expect(req.request.method).toEqual('POST');

    // Simulate an error response from the server
    req.error(new ErrorEvent('Network error'));

    
  });
});
