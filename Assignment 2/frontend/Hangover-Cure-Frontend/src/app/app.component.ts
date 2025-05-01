import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header.component';
import { NavComponent } from './components/nav.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    HeaderComponent,
    NavComponent
  ],
  templateUrl: './app.component.html'
})
export class AppComponent {

}
