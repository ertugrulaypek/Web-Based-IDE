export class SourceCode {
  code: string;
  fileName: string;
  stdout: string;
  stderr: string;
  compilable: boolean;

  constructor(code: string, fileName: string) {
    this.code = code;
    this.fileName = fileName;
    this.stdout = '';
    this.stderr = '';
    this.compilable = false;
  }
}
