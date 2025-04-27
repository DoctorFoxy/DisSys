import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";

@Component({
    imports: [
        RouterLink
    ],
    template: `
    <p>
        404 Page not found<br>
        <a routerLink="/">Go back home</a>
    </p>`
})
export class PageNotFoundComponent {

}