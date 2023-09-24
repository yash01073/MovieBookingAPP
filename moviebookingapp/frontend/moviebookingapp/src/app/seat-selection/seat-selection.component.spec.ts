import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SeatSelectionComponent } from './seat-selection.component';
import { ActivatedRoute,Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { of } from 'rxjs';

describe('SeatSelectionComponent', () => {
  let component: SeatSelectionComponent;
  let fixture: ComponentFixture<SeatSelectionComponent>;
  let mockActivatedRoute: any;
  let mockRouter: any;
  let router: Router;

  beforeEach(() => {
    mockActivatedRoute = {
      paramMap: of({
        get: () => JSON.stringify({
          movieName: 'Movie 1',
          theatreName: 'Theatre A',
          ticketsAllotted: 10,
          bookedSeatNumbers: [3, 7, 9]
        })
      })
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate')
    };


    TestBed.configureTestingModule({
      declarations: [SeatSelectionComponent,FooterComponent,HeaderComponent],
      imports: [HttpClientTestingModule],
      providers: [
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: Router, useValue: mockRouter }
      ],
    });
    fixture = TestBed.createComponent(SeatSelectionComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize seat data', () => {
    fixture.detectChanges();
    expect(component.movie).toEqual({
      movieName: 'Movie 1',
      theatreName: 'Theatre A',
      ticketsAllotted: 10,
      bookedSeatNumbers: [3, 7, 9]
    });
    expect(component.seatNumbers).toEqual([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
    expect(component.seatBooked).toEqual([3, 7, 9]);
  });

  it('should toggle seat selection', () => {
    fixture.detectChanges();
    component.toggleSeatSelection(1);
    expect(component.selectedSeats).toEqual([1]);
    component.toggleSeatSelection(1);
    expect(component.selectedSeats).toEqual([]);
    component.toggleSeatSelection(3); // Should not select a booked seat
    expect(component.selectedSeats).toEqual([]);
  });

  
});
