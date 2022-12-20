import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem , MessageService } from 'primeng/api';
import { DownloadService } from 'src/app/service/download.service';
import { PrintSpoolService } from 'src/app/service/print-spool.service';
import { PrintSpoolCsv } from '../../models/print-spool-csv'
import { SpoolConfig } from '../../models/spool-config'

interface Period {
  date: string;
}

@Component({
  selector: 'app-print-spool-table',
  templateUrl: './print-spool-table.component.html',
  styleUrls: ['./print-spool-table.component.css']
})
export class PrintSpoolTableComponent {

  printSpoolArray: PrintSpoolCsv[] = [];

  printSpool: PrintSpoolCsv;

  selectedPrintSpool: PrintSpoolCsv = {
    id: 0,
    period: "",
    link: "",
    created: ""
  }

  items: MenuItem[];
  
  displaySpoolDialog: boolean = false;

  periods: any[];

  spoolConfig: SpoolConfig = {
    date: "",
    stratum: false,
    avgConsumption: false,
    lastConsumption: false,
    nconsumptions: 1
  }

  blob: Blob;

  constructor(private printSpoolService : PrintSpoolService,
    private downloadService : DownloadService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService) {

    this.periods = [
      '01/2022',
      '02/2022',
      '03/2022',
      '04/2022',
      '05/2022',
      '06/2022',
      '07/2022',
      '08/2022',
      '09/2022',
      '10/2022',
      '11/2022',
      '12/2022'
  ];

  }

  showSpoolDialog(){
  this.displaySpoolDialog = true;
  }

  generatePrintSpool(){
    this.printSpoolService.generatePrintSpool(this.spoolConfig).subscribe(
      (result:any)=>{
        let printSpooCsv = result as PrintSpoolCsv;
        this.printSpoolArray.unshift(printSpooCsv)
        this.messageService.add({severity: 'success', summary: "Result", detail:"CSV print spool has been generated"});
        this.displaySpoolDialog = false;
      },
      error => {
        console.log(error);
      }
    );
  }

  downloadPrintSpool(){
    if(this.selectedPrintSpool! && this.selectedPrintSpool.id!) {
      this.printSpool = this.selectedPrintSpool;
    } else {
      this.messageService.add({severity : 'warn', summary: "Warning!", detail: "Select a register first"})
      return;
    }
    this.confirmationService.confirm({
      message: "Are you sure do you want to download this Print Spool?",
      accept: () => {

        this.downloadService.download(this.selectedPrintSpool.link).subscribe((data) => {

          this.blob = new Blob([data]);
        
          var downloadURL = window.URL.createObjectURL(data);
          var link = document.createElement('a');
          link.href = downloadURL;
          link.download = this.selectedPrintSpool.link.split('/')[4];
          link.click();
          this.messageService.add({severity: 'success', summary: "Result", detail:"Download has started"});
        });

      }
    })
  };

  ngOnInit() {

    this.printSpoolService.getPrintSpools().subscribe(
      (result: any ) => {
        for (let i = 0; i < result.length; i++) {
          let printSpoolCSV = result[i] as PrintSpoolCsv;
          this.printSpoolArray.push(printSpoolCSV);
        }
      },
      error => {
        console.log(error);
      }
    );

    this.printSpoolService.updatePrintSpools().subscribe(
      (result: any) => {
        this.messageService.add({severity: 'success', summary: "Result", detail:result});
      }
    );

    this.items = [
      {
        label: 'New',
        icon: 'pi pi-fw pi-plus',
        command: () => this.showSpoolDialog()
      },
      {
        label: 'Download',
        icon: 'pi pi-fw pi-pencil',
        command: () => this.downloadPrintSpool()
      }
    ];

  };

}