import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let router: Router;
  let navigateSpy: jasmine.Spy;


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent,FooterComponent,HeaderComponent],
      imports: [RouterTestingModule]
    });
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    navigateSpy = spyOn(router, 'navigate');
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to dashboard if JWT token is found', () => {
    // Simulate the presence of a JWT token in sessionStorage
    spyOn(sessionStorage, 'getItem').and.returnValue('your_jwt_token_here');

    // Initialize the component (calls ngOnInit)
    component.ngOnInit();

    // Expect the router to have navigated to the '/dashboard' route
    expect(navigateSpy).toHaveBeenCalledWith(['/dashboard']);
  });
});
