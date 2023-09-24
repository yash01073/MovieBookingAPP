import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SuccessComponent } from './success.component';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

describe('SuccessComponent', () => {
  let component: SuccessComponent;
  let fixture: ComponentFixture<SuccessComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuccessComponent,FooterComponent,HeaderComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            queryParams: {
            subscribe: (fn: (value: any) => void) => {
              fn({
                message: 'Success Message', // Replace with your desired error message
              });
            },
          },
            snapshot: {
              paramMap: convertToParamMap({ message: 'Success Message' }),
            },
          },
        },
      ],
    });
    fixture = TestBed.createComponent(SuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize successMessage from query parameter', () => {
    expect(component.successMessage).toBe('Success Message');
  });
});
