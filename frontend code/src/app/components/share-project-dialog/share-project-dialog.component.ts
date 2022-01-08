import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-share-project-dialog',
  templateUrl: './share-project-dialog.component.html',
  styleUrls: ['./share-project-dialog.component.css']
})
export class ShareProjectDialogComponent implements OnInit {

  username = '';
  constructor(@Inject(MAT_DIALOG_DATA) data: any) {
      if (data) {
            this.username = data.name;
      }
  }


  ngOnInit(): void {
  }

}
