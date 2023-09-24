import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SearchComponent } from './search.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';

describe('SearchComponent', () => {
  let component: SearchComponent;
  let fixture: ComponentFixture<SearchComponent>;
  let httpTestingController: HttpTestingController;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SearchComponent,FooterComponent,HeaderComponent],
      imports: [HttpClientTestingModule, RouterTestingModule]
    });
    fixture = TestBed.createComponent(SearchComponent);
    component = fixture.componentInstance;
    //fixture.detectChanges();
    router = TestBed.inject(Router);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
  it('should initialize with empty movieList and searchQuery', () => {
    expect(component.movieList).toEqual([]);
    expect(component.searchQuery).toBe('');
  });

  it('should search for movies and update movieList', () => {
    const mockResponse = {
      movieList: [
        {
          movieName: 'Movie 1',
          theatreName: 'Theatre A',
          status: 'Available',
          remainingTickets: 5,
          posterLink: 'poster1.jpg',
        },
        {
          movieName: 'Movie 2',
          theatreName: 'Theatre B',
          status: 'Sold Out',
          remainingTickets: 0,
          posterLink: 'poster2.jpg',
        },
      ],
    };

    component.searchQuery = 'Movie';

    component.searchMovies();

    const req = httpTestingController.expectOne({
      method: 'GET',
      url: 'http://localhost:8080/api/v1.0/moviebooking/movies/search/Movie',
    });

    req.flush(mockResponse);

    expect(component.movieList).toEqual([
      {
        movieName: 'Movie 1',
        theatreName: 'Theatre A',
        status: 'Available',
        remainingTickets: 5,
        posterLink: 'poster1.jpg',
      },
    ]);

    httpTestingController.verify();
  });

});
