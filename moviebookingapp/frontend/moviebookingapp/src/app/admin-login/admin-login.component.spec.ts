import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminLoginComponent } from './admin-login.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FooterComponent } from '../footer/footer.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';

describe('AdminLoginComponent', () => {
  let component: AdminLoginComponent;
  let fixture: ComponentFixture<AdminLoginComponent>;
  let httpTestingController: HttpTestingController;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminLoginComponent,FooterComponent,HeaderComponent],
      imports: [HttpClientTestingModule, RouterTestingModule]
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(AdminLoginComponent);
    component = fixture.componentInstance;
    //fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  afterEach(() => {
    // Verify that there are no outstanding HTTP requests
    httpTestingController.verify();
  });

  it('should login successfully as admin', () => {
    const loginId = 'admin'; // Replace with your admin login ID
    const password = 'adminpassword'; // Replace with your admin password

    const mockResponse = {
      access_token: 'mockToken',
      roles: ['ROLE_ADMIN'], // Simulate an admin role
    }; // Mock response data

    // Set up a spy for router.navigate
    spyOn(router, 'navigate');

    // Call the login function
    component.login();

    // Expect that an HTTP POST request was made
    const req = httpTestingController.expectOne('http://localhost:8080/api/v1.0/moviebooking/login');

    // Check that it's a POST request
    expect(req.request.method).toBe('POST');

    // Respond with the mock response data
    req.flush(mockResponse);

    // Assert that sessionStorage is set with the token
    expect(sessionStorage.getItem('jwtToken')).toBe('mockToken');

    // Assert that router.navigate was called with the expected route
    expect(router.navigate).toHaveBeenCalledWith(['/admin-dashboard']);
  });

  it('should handle login failure', () => {
    // Set up a spy for router.navigate
    spyOn(router, 'navigate');

    // Call the login function
    component.login();

    // Expect that an HTTP POST request was made
    const req = httpTestingController.expectOne('http://localhost:8080/api/v1.0/moviebooking/login');

    // Respond with an error (adjust error message as needed)
    req.error(new ErrorEvent('HttpErrorResponse', { error: 'Login failed' }));

    // Assert that router.navigate was called with the error route
    expect(router.navigate).toHaveBeenCalledWith(['/error'], {
      queryParams: { message: 'Login Failed!' },
    });
  });
});
