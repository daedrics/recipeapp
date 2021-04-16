import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthenticationService} from '../services/authentication.service';
import {ToastrService} from 'ngx-toastr';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService, private toastr: ToastrService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            console.log(err);
            if (err.status === 401) {
                this.authenticationService.logout();
                location.reload(true);
                this.toastr.warning('You should log in again!', 'Token expired!');
            }

            if (err.status === 422) {
                const validationErrors = err.error.error;
              // tslint:disable-next-line:forin
                for (const key in validationErrors) {
                    this.toastr.warning(validationErrors[key], 'Error!', {disableTimeOut: true});
                }
            }
            return throwError(err.error);
        }));
    }
}
