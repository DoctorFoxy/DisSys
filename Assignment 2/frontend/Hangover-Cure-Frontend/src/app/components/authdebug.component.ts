import { HttpClient } from "@angular/common/http";
import { Component, inject } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { AuthService } from "@auth0/auth0-angular";
import { environment } from "../../environments/environment";

@Component({
    selector: 'authdebug',
    templateUrl: './authdebug.component.html',
    imports: [
        MatButtonModule
    ]
})
export class AuthdebugComponent {
    
    private http = inject(HttpClient);
    private auth0Service = inject(AuthService)

    requestPublic() {
        this.http.get(`${environment.apiDomain}/api/authdebug/public`).subscribe();
    }

    requestPrivate() {
        this.http.get(`${environment.apiDomain}/api/authdebug/private`).subscribe();
    }

    requestPrivateScoped() {
        this.http.get(`${environment.apiDomain}/api/authdebug/privatescoped`).subscribe();
    }

    getSilentToken() {
        this.auth0Service.getAccessTokenSilently().subscribe(console.log);
    }
}