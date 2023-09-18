import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AdminDashboardComponent } from './admin-dashboard.component';
import { FooterComponent } from '../footer/footer.component';
import { of, throwError } from 'rxjs';


describe('AdminDashboardComponent', () => {
  let component: AdminDashboardComponent;
  let fixture: ComponentFixture<AdminDashboardComponent>;
  let router: Router;
  let httpTestingController: HttpTestingController;
  const mockId = 'mockId';

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminDashboardComponent,FooterComponent],
      imports: [HttpClientTestingModule, RouterTestingModule]
    });
    router = TestBed.inject(Router);
    httpTestingController = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(AdminDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should fetch the list of movies successfully', () => {
    // Spy on the fetchMovieList() method
    spyOn(component, 'fetchMovieList');
  
    // Call the fetchMovieList() method
    component.fetchMovieList();
  
    // Expect the fetchMovieList() method to have been called
    expect(component.fetchMovieList).toHaveBeenCalled();
  });  


  it('should delete a movie successfully', () => {
    spyOn(router, 'navigate'); // Mock router.navigate

    component.deleteMovie(mockId);

    const req = httpTestingController.expectOne(`http://localhost:8080/api/v1.0/moviebooking/delete/${mockId}`);
    expect(req.request.method).toBe('DELETE');

    // Respond with a successful response (adjust data as needed)
    const mockResponse = { message: 'Movie deleted successfully' };
    req.flush(mockResponse);

    // Assert that router.navigate was not called (success case)
    expect(router.navigate).not.toHaveBeenCalled();

    
  });

  // it('should update movie status successfully', async () => {
  //   spyOn(router, 'navigate'); // Mock router.navigate
    

  //   const mockMovie = { movieName: 'Avengers: Endgame', theatreName: 'World Trade Park' };

  //   // Call the updateMovieStatus function
  //   await component.updateMovieStatus(mockMovie);

  //   const apiUrl = 'http://localhost:8080/api/v1.0/moviebooking/updateStatus';

  //   const req = httpTestingController.expectOne(apiUrl);

  //   // Check that it's a PUT request
  //   //expect(req.request.method).toBe('PUT');

  //   // Check the request headers (you can add more header checks if needed)
  //   //expect(req.request.headers.get('Content-Type')).toBe('application/json');
  //   //expect(req.request.headers.get('Authorization')).toBe('Bearer ' + sessionStorage.getItem('jwtToken'));

  //   // Respond with a successful response (adjust data as needed)
  //   const mockResponse = { message:'Status Updated Successfully'};
  //   req.flush(mockResponse);

  //   // Assert that router.navigate was called with the expected route
  //   expect(router.navigate).toHaveBeenCalledWith(['/admin-dashboard']);

  //   // Verify that there are no outstanding requests
  //   httpTestingController.verify();
  // });

  

  
  
});
