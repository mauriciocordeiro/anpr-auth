import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: 'login', component: LoginComponent, data: { breadcrumb: { skip: true } } },
  { path: 'home', loadChildren: () => import('./components/home/home.module').then(module => module.HomeModule), data: { breadcrumb: 'Home' } },
  { path: '**', redirectTo: '404' },
  { path: '404', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
