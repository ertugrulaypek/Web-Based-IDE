import { NgModule } from '@angular/core';

import {MatTableModule} from '@angular/material/table';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatButtonModule} from '@angular/material/button';
import {MatDividerModule} from '@angular/material/divider';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import {MatMenuModule} from '@angular/material/menu';
import {MatTabsModule} from '@angular/material/tabs';
import {MatIconModule} from '@angular/material/icon';



const materialComponents = [
  MatTableModule,
  MatSidenavModule,
  MatListModule,
  MatGridListModule,
  MatButtonModule,
  MatDividerModule,
  MatDialogModule,
  MatInputModule,
  MatMenuModule,
  MatTabsModule,
  MatIconModule
];

@NgModule({
  imports: [materialComponents],
  exports: [materialComponents]
})
export class MaterialModule { }
