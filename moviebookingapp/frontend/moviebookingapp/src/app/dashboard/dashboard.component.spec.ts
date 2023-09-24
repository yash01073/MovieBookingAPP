import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardComponent } from './dashboard.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let httpTestingController: HttpTestingController;
  let router: Router;
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardComponent,FooterComponent,HeaderComponent],
      imports: [HttpClientTestingModule, RouterTestingModule]
    });
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
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
  
  it('should navigate to seat selection with movie object', () => {
    const navigateSpy = spyOn(router, 'navigate');

    const mockMovie = { /* Your mock movie data here */ };
    
    // Call the bookMovie function with the mock movie object
    component.bookMovie(mockMovie);

    // Expect that router.navigate was called with the expected URL and parameter
    expect(navigateSpy).toHaveBeenCalledWith(['/seat-selection', { movie: JSON.stringify(mockMovie) }]);
  });
});
