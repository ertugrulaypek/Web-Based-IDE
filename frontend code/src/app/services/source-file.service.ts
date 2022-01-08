import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {SourceFile} from '../model/sourceFile';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import {Project} from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class SourceFileService {

  private SOURCE_FILE_URL = '/project/source';
  constructor(private http: HttpClient) { }


  createSourceFile(sourceFileName: string, projectName: string): Observable<SourceFile> {
    // Initialize Params Object
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('source_file_name', sourceFileName);
    params = params.append('project_name', projectName);
    return this.http.post<Project>(this.SOURCE_FILE_URL,
      {
        source_file_name: sourceFileName,
        project_name: projectName
      },
      {
        params,
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Accept: 'application/json',
          'Access-Control-Allow-headers': 'content-type'
        })}).pipe(
      catchError(this.handleError<Project>('createSourceFile'))
    );
  }

  getSourceFile(fileName: string, projectName: string): Observable<SourceFile> {
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('source_file_name', fileName);
    params = params.append('project_name', projectName);
    const options = {
      params,
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
    return this.http.get<SourceFile>( this.SOURCE_FILE_URL, options).pipe(
      catchError(this.handleError<SourceFile>('getSourceFile'))
    );
  }

  getAllSourceFiles(projectName: string): Observable<SourceFile[]> {
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('project_name', projectName);
    const options = {
      params,
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
    return this.http.get<SourceFile[]>( '/project/source/getAllFiles', options).pipe(
      catchError(this.handleError<SourceFile>('getAllSourceFiles'))
    );
  }

  updateSourceFileName(fileName: string, newFileName: string, projectName: string): Observable<SourceFile> {
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('source_file_name', fileName);
    params = params.append('new_source_file_name', newFileName);
    params = params.append('project_name', projectName);
    const options = {
      params,
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
    return this.http.patch<SourceFile>(this.SOURCE_FILE_URL,
      {
      source_file_name: fileName,
      new_source_file_name: newFileName,
      project_name: projectName
    }, options).pipe(
      catchError(this.handleError<SourceFile>('updateSourceFileName'))
    );
  }

  deleteSourceFile(fileName: string, projectName: string): Observable<SourceFile> {
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('source_file_name', fileName);
    params = params.append('project_name', projectName);
    const options = {
      params,
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
    return this.http.delete<SourceFile>(this.SOURCE_FILE_URL, options)
      .pipe(
        catchError(this.handleError<SourceFile>('deleteSourceFile'))
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
      if (operation === 'createSourceFile') {
        alert('File name already exists, please try another name!');
      }
      // Let the app keep running by returning an empty result.
      return throwError(error.message);
    };
  }
}
