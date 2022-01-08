import { Injectable } from '@angular/core';

import {Observable, of} from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DarkModeService {
  private DARK_MODE_URL = '/dark-mode';
  constructor(private http: HttpClient) { }

  getDarkMode(): Observable<boolean> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
    return this.http.get<boolean>(this.DARK_MODE_URL, options).pipe(
      catchError(this.handleError<boolean>('getDarkMode'))
    );
  }
  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T): any {
    return (error: any): Observable<T> => {
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
