import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { DownloadService } from 'src/app/service/download.service';
import { UploadFileService } from 'src/app/service/upload-file.service';
import { UploadFile } from '../../models/upload-file'

@Component({
  selector: 'app-file-upload-table',
  templateUrl: './file-upload-table.component.html'
})
export class FileUploadTableComponent {

  uploadsArray: UploadFile[];
  cols: any[];

  file: UploadFile;

  selectedFile: UploadFile = {
    id: 0,
    filename: "",
    created: ""
  }
  
  items: MenuItem[];

  displayUploadDialog: boolean = false;

  blob: Blob;

  constructor(private uploadFileService : UploadFileService,
    private messageService : MessageService,
    private downloadService : DownloadService,
    private confirmationService : ConfirmationService) {
  }

  showUploadDialog(){
    this.displayUploadDialog = true;
  }

  downloadFile(){
    if(this.selectedFile! && this.selectedFile.id!) {
      this.file = this.selectedFile;
    } else {
      this.messageService.add({severity : 'warn', summary: "Warning!", detail: "Select a register first"})
      return;
    }
    this.confirmationService.confirm({
      message: "Are you sure do you want to download this file?",
      accept: () => {

        this.uploadFileService.download(this.selectedFile.filename).subscribe((data) => {

          this.blob = new Blob([data]);
        
          var downloadURL = window.URL.createObjectURL(data);
          var link = document.createElement('a');
          link.href = downloadURL;
          link.download = this.selectedFile.filename;
          link.click();
          this.messageService.add({severity: 'success', summary: "Success", detail:"Download has started"});
        },
        error => {
          console.log(error);
          this.messageService.add({severity: 'error', summary: 'Internal error', detail: ''});
        });

      }
    })
  };

  onUpload(event: any, fileUpload: any) {
    this.displayUploadDialog = false; // Cierra la ventana de descarga :)
    fileUpload.clear(); // Limpia la ventana de subida
    switch (event.error.status) {
      case 200:
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'File has been uploaded'}); 
        this.uploadFileService.getFileUploads().subscribe(
          (result: any ) => {
            this.uploadsArray = [];
            for (let i = 0; i < result.length; i++) {
              let upload = result[i] as UploadFile;
              this.uploadsArray.push(upload);
            }
          },
          error => {
            console.log(error);
            this.messageService.add({severity: 'error', summary: 'Internal Error', detail: ''});
          }
        )
        break;
      case 400:
        this.messageService.add({severity: 'error', summary: 'Bad request', detail: ''});
        break;
      case 409:
        this.messageService.add({severity: 'warn', summary: 'Warning!', detail: 'This file already exist'});
        break;
      case 413:
        this.messageService.add({severity: 'warn', summary: 'Warning!', detail: 'This file is larger than allowed'});
        break;
      default: 
      this.messageService.add({severity: 'error', summary: 'Internal error', detail: ''});
    }

  }

  ngOnInit() {

    this.uploadFileService.getFileUploads().subscribe(
      (result: any ) => {
        let uploadsArray: UploadFile[] = [];
        for (let i = 0; i < result.length; i++) {
          let upload = result[i] as UploadFile;
          uploadsArray.push(upload);
        }
        this.uploadsArray = uploadsArray;
      },
      error => {
        console.log(error);
      }
    )

    this.items = [{
      label: 'Upload',
      icon: 'pi pi-upload',
      command: () => this.showUploadDialog()
    },
    {
      label: 'Download',
      icon: 'pi pi-download',
      command: () => this.downloadFile()
    }]

    this.cols = [
      {field: "filename", header: "Filename"},
      {field: "created", header: "Created"}
    ];

  }

}