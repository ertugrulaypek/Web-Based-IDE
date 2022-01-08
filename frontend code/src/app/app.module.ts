import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ProjectListComponent } from './components/project-list/project-list.component';

import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { EditorComponent } from './components/editor/editor.component';
import { AppRoutingModule } from './components/app-routing.module';
import { MonacoEditorModule } from 'ngx-monaco-editor';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from './material.module';
import { HttpClientModule } from '@angular/common/http';
import { DialogComponent } from './components/dialog/dialog.component';
import { CompileComponent } from './components/compile/compile.component';
import {HomeComponent} from "./components/home/home.component";
import { ShareProjectDialogComponent } from './components/share-project-dialog/share-project-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    ProjectListComponent,
    EditorComponent,
    DialogComponent,
    CompileComponent,
    HomeComponent,
    ShareProjectDialogComponent
  ],
  imports: [
    BrowserModule,
    NoopAnimationsModule,
    AppRoutingModule,
    MonacoEditorModule.forRoot(),
    FormsModule,
    MaterialModule,
    HttpClientModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
