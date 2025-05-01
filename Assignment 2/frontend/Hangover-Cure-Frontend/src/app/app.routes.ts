import { Routes } from '@angular/router';
import { HomeComponent } from './components/home.component';
import { PageNotFoundComponent } from './components/page-not-found.component';
import { MyOrdersComponent } from './components/myorders.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'myorders', component: MyOrdersComponent },
    { path: '**', component: PageNotFoundComponent }
];
