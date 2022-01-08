import { Component, OnInit } from '@angular/core';
import {CompileService} from '../../services/compile.service';
import {SourceCode} from '../../model/sourceCode';

@Component({
  selector: 'app-compile',
  templateUrl: './compile.component.html',
  styleUrls: ['./compile.component.css']
})
export class CompileComponent implements OnInit {

  sourceCode: SourceCode = null;
  constructor(private compileService: CompileService) { }

  ngOnInit(): void {
  }

  compile(code: string, fileName: string): void {
    this.sourceCode = new SourceCode(code, fileName);
    this.compileService.compile(this.sourceCode)
      .subscribe(sourceCode => {
        this.sourceCode = sourceCode;
      });
  }
}
