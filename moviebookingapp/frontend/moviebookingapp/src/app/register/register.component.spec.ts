// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { RegisterComponent } from './register.component';
// import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
// import { RouterTestingModule } from '@angular/router/testing';
// import { Router } from '@angular/router';
// import { HeaderComponent } from '../header/header.component';
// import { FooterComponent } from '../footer/footer.component';
// import { FormsModule, ReactiveFormsModule,FormBuilder, Validators,FormGroup,FormControl } from '@angular/forms';

// describe('RegisterComponent', () => {
//   let component: RegisterComponent;
//   let fixture: ComponentFixture<RegisterComponent>;
//   let httpTestingController: HttpTestingController;
//   let router: Router;

//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       declarations: [RegisterComponent, FooterComponent, HeaderComponent],
//       imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule,FormsModule], // Import ReactiveFormsModule
//       providers: [
        
//       ],
//     });
//     httpTestingController = TestBed.inject(HttpTestingController);
//     const formBuilder = TestBed.inject(FormBuilder);
//     router = TestBed.inject(Router);
//     fixture = TestBed.createComponent(RegisterComponent);
//     component = fixture.componentInstance;
//     component.registerForm = new FormGroup({
//       contactNumber: new FormControl('', [Validators.required, Validators.pattern('[0-9]{10}')]),
//       // Add other form controls here if needed
//     });
   
//      // Provide the form to the component
//     fixture.detectChanges();
//   });

//   // it('should create', () => {
//   //   expect(component).toBeTruthy();
//   // });
// });
