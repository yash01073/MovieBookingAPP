import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SeatSelectionComponent } from './seat-selection/seat-selection.component';
import { SuccessComponent } from './success/success.component';
import { ErrorComponent } from './error/error.component';
import { SearchComponent } from './search/search.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: HomeComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'seat-selection', component:SeatSelectionComponent},
  {path: 'success', component:SuccessComponent},
  {path: 'error', component:ErrorComponent},
  {path: 'search', component:SearchComponent},
  {path: 'reset-password', component:ResetPasswordComponent},
  {path: 'admin-login', component:AdminLoginComponent},
  {path: 'admin-dashboard', component:AdminDashboardComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
