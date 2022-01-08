import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import {SourceFileService} from '../../services/source-file.service';
import {SourceFileContentService} from '../../services/source-file-content.service';
import {SourceFile} from '../../model/sourceFile';
import {SourceFileContent} from '../../model/sourceFileContent';
import {MatDialog} from '@angular/material/dialog';
import {DialogComponent} from '../dialog/dialog.component';
import {ShareProjectDialogComponent} from '../share-project-dialog/share-project-dialog.component'
import {Project} from '../../model/project';
import {MatMenuTrigger} from '@angular/material/menu';
import {CompileComponent} from '../compile/compile.component';
import {DarkModeService} from '../../services/dark-mode.service';
import {interval} from 'rxjs';
import {AuthService} from '../../services/auth.service';
import {ProjectService} from '../../services/project.service';


@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit, OnDestroy {



  fileContent: SourceFileContent;
  projectFiles: SourceFile[] = [];
  project: Project;
  disableCompiler = true;

  @ViewChild(MatMenuTrigger)
  contextMenu: MatMenuTrigger;

  @ViewChild(CompileComponent)
  compiler: CompileComponent;

  contextMenuPosition = { x: '5px', y: '5px' };

  cFileExtension = '.c';
  javaFileExtension = '.java';
  editorLanguage = 'c';
  editorTheme = 'vs-dark';
  editorThemePreviousStatus = false;
  fileSelected = false;

  editorOptions = {theme: this.editorTheme, language: this.editorLanguage};
  code = ' ';

  // Interval variables to call the dark mode service
  timeInterval = interval(3000);
  subscribeToInterval: any;


  constructor(private route: ActivatedRoute,
              private location: Location,
              private sourceFile: SourceFileService,
              private sourceFileContent: SourceFileContentService,
              public dialog: MatDialog,
              public shareProjectDialog: MatDialog,
              private projectService: ProjectService,
              private darkModeService: DarkModeService,
              private authService: AuthService) {

    this.subscribeToInterval = this.timeInterval.subscribe(val => this.darkMode());
    this.fileSelected = false;

  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.project = this.projectService.project;
    this.getAllSourceFiles();
    this.disableCompiler = true;
  }

  ngOnDestroy(): void {
    this.subscribeToInterval.unsubscribe();
  }

  goBack(): void {
    this.location.back();
  }

  fileClicked(fileName: string, fileId: string): void {
    this.fileSelected = true;
    if (this.compiler !== undefined) {
      this.compiler.sourceCode = null;
    }
    if (fileName.endsWith(this.javaFileExtension)) {
      this.editorLanguage = 'java';
    } else {
      this.editorLanguage = 'c';
    }
    this.code = ' ';
    this.sourceFileContent.getSourceFileContent(fileName, this.project.name)
      .subscribe(sourceFileContent => {
        this.fileContent = sourceFileContent;
        this.code = this.fileContent.fileContent;
        });
    this.disableCompiler = true;
  }


  createSourceFileDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent);
    dialogRef.afterClosed().subscribe(fileName => {
      if (fileName === '' || fileName === undefined || fileName.isEmpty) {
        // console.log('file name is undefined');
      } else {
        this.createSourceFile(fileName, this.project.name);
      }
    });
  }

  createShareProjectDialog(): void {
      const dialogRef = this.shareProjectDialog.open(ShareProjectDialogComponent);
      dialogRef.afterClosed().subscribe(username => {
        if (username === '' || username === undefined || username.isEmpty) {
          console.log('file name is undefined');
        } else {
          console.log(username);
          this.projectService.shareProject(username, this.project.id).subscribe(response => {
            console.log(response)});
        }
      });
    }

  createSourceFile(fileName: string, projectName: string): void {
    if (fileName.endsWith(this.javaFileExtension)) {
      this.editorLanguage = 'java';
    } else if (fileName.endsWith(this.cFileExtension)) {
      this.editorLanguage = 'c';
    } else {
      alert('Invalid file extension');
      return;
    }
    this.sourceFile.createSourceFile(fileName, projectName)
      .subscribe(sourceFile => {
        this.projectFiles.push(new SourceFile(fileName, this.project));
      });
  }

  getAllSourceFiles(): void {
    this.sourceFile.getAllSourceFiles(this.project.name)
      .subscribe(files => {
        this.projectFiles = files;
      });
  }

  updateSourceFileName(fileName: string, newFileName: string, projectName: string): void {
    this.sourceFile.updateSourceFileName(fileName, newFileName, projectName)
      .subscribe(sourceFile => {
        this.fileContent.sourceFile.fileName = sourceFile.fileName;
        this.projectFiles.find(item => {
          if (item.fileName === fileName) {
            item.fileName = newFileName;
          }
        });
      });
  }

  updateSourceFileContent(): void {
    this.disableCompiler = false;
    this.fileContent.fileContent = this.code;
    this.sourceFileContent.updateSourceFileContent(this.fileContent.sourceFile.fileName,
      this.fileContent.sourceFile.project.name, this.code)
      .subscribe(sourceFileContent => {
      });
  }

  onContextMenuRename(item: SourceFile): void {
    const dialogRef = this.dialog.open(DialogComponent, {data: {name: item.fileName}});
    dialogRef.afterClosed().subscribe(fileName => {
      if (fileName === '' || fileName === undefined || fileName.isEmpty) {
        // console.log('file name is undefined');
      } else {
        this.updateSourceFileName(item.fileName, fileName, item.project.name);
      }
    });
  }

  onContextMenuDelete(item: SourceFile): void {
    this.sourceFile.deleteSourceFile(item.fileName, item.project.name)
      .subscribe( sourceFileResult => {
        this.projectFiles = this.projectFiles.filter(sourceFile => sourceFile.fileName !== item.fileName);
      });
  }

  onContextMenu(event: MouseEvent, item: SourceFile): void {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }

  compile(): void {
    this.compiler.compile(this.fileContent.fileContent, this.fileContent.sourceFile.fileName);
  }

  disableCompile(): void {
    this.disableCompiler = true;
  }

  darkMode(): void {
    this.darkModeService.getDarkMode()
      .subscribe(darkModeStatus => {
        if (this.editorThemePreviousStatus !== darkModeStatus) {
          this.editorThemePreviousStatus = darkModeStatus;
          if (darkModeStatus) {
            this.editorOptions = { ...this.editorOptions, theme: '' };
          } else {
            this.editorOptions = { ...this.editorOptions, theme: 'vs-dark' };
          }
        }

      });
  }

  logout(): void {
    this.authService.logout();
  }

}
