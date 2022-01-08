import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import { Project } from '../model/project';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { catchError, retry } from 'rxjs/operators';
import { StringRespose } from '../model/shareProjectResponse';


@Injectable({
  providedIn: 'root'
})

export class ProjectService {
  private PROJECT_URL = '/project';
  project: Project;
  constructor(private http: HttpClient) {
  }

  getProject(projectName: string): Observable<Project> {
    projectName = projectName.trim();
    const options = {
      params: new HttpParams().set('project_name', projectName),
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
        })};
    return this.http.get<Project>(this.PROJECT_URL, options).pipe(
      catchError(this.handleError<Project>('getProject'))
    );
  }

  getAllProjects(): Observable<Project[]> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
    return this.http.get<Project[]>('/project/allProjects').pipe(
      catchError(this.handleError<Project>('allProjects'))
    );
  }

  createProject(): Observable<Project> {
    const random = Math.random();
    return this.http.post<Project>(this.PROJECT_URL, {project_name: 'project' + random}, {
        params: new HttpParams().set('project_name', 'project' + random),
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Accept: 'application/json',
          'Access-Control-Allow-headers': 'content-type'
        })}).pipe(
      catchError(this.handleError<Project>('createProject'))
    );
  }

  updateProjectName(projectName: string, newProjectName: string): Observable<Project> {
    // Initialize Params Object
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('project_name', projectName);
    params = params.append('new_project_name', newProjectName);
    return this.http.patch<Project>(this.PROJECT_URL,
      {
        project_name: projectName,
        new_project_name: newProjectName
      },
      {
        params,
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            Accept: 'application/json',
            'Access-Control-Allow-headers': 'content-type'
          }
        )}).pipe(
      catchError(this.handleError<Project>('updateProjectName'))
    );
  }

  deleteProject(project: Project): Observable<Project> {
    const options = {
      params: new HttpParams().set('project_name', project.name),
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
    return this.http.delete<Project>(this.PROJECT_URL, options)
      .pipe(
        catchError(this.handleError<Project>('deleteProject'))
    );
  }

  shareProject(username:string, id:string): Observable<StringRespose> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-headers': 'content-type'
      })};
      const url = this.PROJECT_URL + '/' + id + '/share/' + username;
      console.log(url);
      return this.http.put<StringRespose>(url, options)
        .pipe(
          catchError(this.handleError('shareProject'))
      );
  }

  setProject(project: Project): void {
    this.project = project;
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T): any {

    return (error: any): Observable<T> => {
      if (operation === 'updateProjectName') {
        alert('Project already exists, please try another name!');
      }
      // Let the app keep running by returning an empty result.
      return throwError(error.message);
    };
  }
}
