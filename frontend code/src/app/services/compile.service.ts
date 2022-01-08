import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import {SourceCode} from '../model/sourceCode';

@Injectable({
  providedIn: 'root'
})
export class CompileService {

  private COMPILE_URL = '/compile';
  constructor(private http: HttpClient) { }

  compile(sourceCode: SourceCode): Observable<SourceCode> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json;charset=UTF-8',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};

    return this.http.post<SourceCode>(this.COMPILE_URL, JSON.stringify(sourceCode), options)
      .pipe(
      catchError(this.handleError<SourceCode>('createProject'))
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
