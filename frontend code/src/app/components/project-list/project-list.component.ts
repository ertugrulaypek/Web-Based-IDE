import {Component, OnInit, ViewChild} from '@angular/core';

import { ProjectService } from '../../services/project.service';
import {Project} from '../../model/project';
import {MatTable} from '@angular/material/table';
import {AuthService} from '../../services/auth.service';


@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  displayedColumns: string[] = ['id', 'name', 'user', 'action'];
  dataSource = [];  // projects list
  project: Project = null;
  @ViewChild(MatTable, { static: true}) table: MatTable<Project>;
  oldProjectName: string;


  constructor(private projectService: ProjectService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.getAllProjects();
  }

  getProject(projectName: string): void {
    this.projectService.getProject(projectName)
      .subscribe(project => this.project = project);
    this.dataSource.push(this.project);
  }

  getAllProjects(): void {
    this.projectService.getAllProjects()
      .subscribe(projects => {
        this.dataSource = projects;
      });
  }

  createProject(): void {
    this.projectService.createProject()
      .subscribe(p => {
        if (p !== undefined) {
          this.dataSource.push(p);
          this.table.renderRows();
        }
      });
  }

  updateProjectName(project: Project): void {
    this.projectService.updateProjectName(this.oldProjectName, project.name)
      .subscribe(p => {
        if (p !== undefined) {
          this.table.renderRows();
        }
      },
        error => this.getAllProjects()
      );
  }

  deleteProject(project: Project): void {
    this.projectService.deleteProject(project)
      .subscribe(deletedProject => {
        this.dataSource =  this.dataSource.filter(p => p.id !== deletedProject.id);
        this.table.renderRows();
      });
  }

  logout(): void {
    this.authService.logout();
  }

  setOldProjectName(project: Project): void {
    this.oldProjectName = project.name;
  }

  sendProject(project: Project): void {
    this.projectService.setProject(project);
  }
}
