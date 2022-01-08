import {Project} from './project';

export class SourceFile {
  fileId: string;
  fileName: string;
  project: Project;

  constructor(filename: string, project: Project) {
    this.fileName = filename;
    this.project = project;
  }
}
