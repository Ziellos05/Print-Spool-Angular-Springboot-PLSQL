// @angular imports
import { Component } from '@angular/core';
import { Router } from '@angular/router';

// Imports propios
import { UploadFile } from '../models/upload-file'

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html'
})
export class FileUploadComponent {

  uploadFileArray: UploadFile[]
  
  // @ts-ignore
  uploadPage: MenuItem[];

  // @ts-ignore
  home: MenuItem;

  constructor(
    private router: Router
  ){}
  
  ngOnInit() { 

    this.uploadPage = [{label:'Upload files'}];

    this.home = {icon: 'pi pi-home'};
}

  homeRedirect() {
    this.router.navigate(['']);
  }

}
