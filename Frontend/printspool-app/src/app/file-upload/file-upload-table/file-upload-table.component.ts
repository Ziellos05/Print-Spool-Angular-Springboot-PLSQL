import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { DownloadService } from 'src/app/service/download.service';
import { UploadFileService } from 'src/app/service/upload-file.service';
import { UploadFile } from '../../models/upload-file'

@Component({
  selector: 'app-file-upload-table',
  templateUrl: './file-upload-table.component.html',
  styleUrls: ['./file-upload-table.component.css']
})
export class FileUploadTableComponent {

  uploadsArray: UploadFile[] = [];

  file: UploadFile;

  selectedFile: UploadFile = {
    id: 0,
    link: "",
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
      message: "Are you sure do you want to download this Print Spool?",
      accept: () => {

        this.downloadService.download(this.selectedFile.link).subscribe((data) => {

          this.blob = new Blob([data]);
        
          var downloadURL = window.URL.createObjectURL(data);
          var link = document.createElement('a');
          link.href = downloadURL;
          link.download = this.selectedFile.link.split('/')[4];
          link.click();
          this.messageService.add({severity: 'success', summary: "Result", detail:"Download has started"});
        });

      }
    })
  };

  ngOnInit() {
    this.uploadFileService.getFileUploads().subscribe(
      (result: any ) => {
        for (let i = 0; i < result.length; i++) {
          let upload = result[i] as UploadFile;
          this.uploadsArray.push(upload);
        }
      },
      error => {
        console.log(error);
      }
    )

    this.items = [{
      label: 'New',
      icon: 'pi pi-fw pi-plus',
      command: () => this.showUploadDialog()
    },
    {
      label: 'Download',
      icon: 'pi pi-fw pi-pencil',
      command: () => this.downloadFile()
    }]

  }

}