import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  fileName = '';
  constructor(@Inject(MAT_DIALOG_DATA) data: any) {
    if (data) {
      this.fileName = data.name;
    }
  }

  ngOnInit(): void {
  }

}
