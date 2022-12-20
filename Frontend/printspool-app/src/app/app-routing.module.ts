import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { HomeComponent } from './home/home.component';
import { PrintSpoolComponent } from './print-spool/print-spool.component';
import { StratumComponent } from './stratum/stratum.component';

const routes: Routes = [
  {path:'', component: HomeComponent},
  {path:'stratum', component: StratumComponent},
  {path:'printspool', component: PrintSpoolComponent},
  {path:'fileupload', component: FileUploadComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
