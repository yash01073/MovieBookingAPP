import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ErrorComponent } from './error.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

describe('ErrorComponent', () => {
  let component: ErrorComponent;
  let fixture: ComponentFixture<ErrorComponent>;
  let activatedRoute: ActivatedRoute;
  let router: Router;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ErrorComponent,FooterComponent,HeaderComponent],
      imports: [RouterTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            queryParams: {
              subscribe: (fn: (value: any) => void) => {
                fn({
                  message: 'Error Message', // Replace with your desired error message
                });
              },
            },
            snapshot: {
              paramMap: convertToParamMap({ message: 'Error Message' }), // Replace with your desired error message
            },
          },
        },
      ],

    });
    fixture = TestBed.createComponent(ErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should retrieve error message from query parameters', () => {
    expect(component.errorMessage).toEqual('Error Message'); // Replace with your desired error message
  });
});
