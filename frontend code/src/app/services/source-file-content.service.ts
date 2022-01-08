import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import {SourceFileContent} from '../model/sourceFileContent';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SourceFileContentService {

  private SOURCE_FILE_CONTENT_URL = '/project/source/content';

  constructor(private http: HttpClient) { }

  getSourceFileContent(fileName: string, projectName: string): Observable<SourceFileContent> {
    // Initialize Params Object
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('source_file_name', fileName);
    params = params.append('project_name', projectName);
    return this.http.get<SourceFileContent>(this.SOURCE_FILE_CONTENT_URL,
      {
        params,
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Accept: 'application/json',
          'Access-Control-Allow-headers': 'content-type'
        })}).pipe(
      catchError(this.handleError<SourceFileContent>('getSourceFileContent'))
    );
  }

  updateSourceFileContent(fileName: string, projectName: string, content: string): Observable<SourceFileContent> {
    // Initialize Params Object
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('source_file_name', fileName);
    params = params.append('project_name', projectName);
    params = params.append('content', content);
    return this.http.patch<SourceFileContent>(this.SOURCE_FILE_CONTENT_URL,
      {
        source_file_name: fileName,
        project_name: projectName,
        content
      },
      {
        params,
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Accept: 'application/json',
          'Access-Control-Allow-headers': 'content-type'
        })}).pipe(
      catchError(this.handleError<SourceFileContent>('updateSourceFileContent'))
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
