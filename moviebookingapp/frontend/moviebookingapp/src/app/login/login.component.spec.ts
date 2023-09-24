import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpTestingController: HttpTestingController;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent,FooterComponent,HeaderComponent],
      imports: [HttpClientTestingModule, RouterTestingModule]
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should login successfully', () => {
    const loginId = 'mockLoginId';
    const password = 'mockPassword';

    const mockResponse = { access_token: 'mockToken' }; // Mock response data

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
    expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
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
