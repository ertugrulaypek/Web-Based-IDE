import {SourceFile} from './sourceFile';

export class SourceFileContent{
  fileId: string;
  sourceFile: SourceFile;
  fileContent: string;

  constructor(sourceFile: SourceFile, fileContent: string) {
    this.fileId = sourceFile.fileId;
    this.sourceFile = sourceFile;
    this.fileContent = fileContent;
  }
}
