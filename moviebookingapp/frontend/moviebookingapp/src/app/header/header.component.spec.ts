import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderComponent]
    });
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return true from isLoggedIn when token exists', () => {
    // Simulate the presence of a JWT token in sessionStorage
    spyOn(sessionStorage, 'getItem').and.returnValue('your_jwt_token_here');

    const result = component.isLoggedIn();

    expect(result).toBeTruthy();
  });

  it('should return false from isLoggedIn when token does not exist', () => {
    // Simulate the absence of a JWT token in sessionStorage
    spyOn(sessionStorage, 'getItem').and.returnValue(null);

    const result = component.isLoggedIn();

    expect(result).toBeFalsy();
  });

  it('should remove token from sessionStorage on logout', () => {
    // Spy on sessionStorage.removeItem method
    const removeItemSpy = spyOn(sessionStorage, 'removeItem');

    component.logout();

    // Expect sessionStorage.removeItem to have been called with 'jwtToken'
    expect(removeItemSpy).toHaveBeenCalledWith('jwtToken');
  });
});
