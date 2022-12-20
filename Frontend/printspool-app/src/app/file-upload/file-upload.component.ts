import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UploadFile } from '../models/upload-file'

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
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
