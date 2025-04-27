import { Routes } from '@angular/router';
import { HomeComponent } from './components/home.component';
import { PageNotFoundComponent } from './components/page-not-found.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: '**', component: PageNotFoundComponent }
];
