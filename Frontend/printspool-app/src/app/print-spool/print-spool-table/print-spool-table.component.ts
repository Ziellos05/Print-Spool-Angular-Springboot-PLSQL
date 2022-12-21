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

  printSpoolArray: PrintSpoolCsv[];
  cols: any[];

  printSpool: PrintSpoolCsv;

  selectedPrintSpool: PrintSpoolCsv;

  items: MenuItem[];
  
  displaySpoolDialog: boolean = false;

  periods: any[] = [];

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

  }

  showSpoolDialog(){
  this.displaySpoolDialog = true;
  }

  generatePrintSpool(generateButton: any){
    generateButton.disabled = true;
    this.printSpoolService.generatePrintSpool(this.spoolConfig).subscribe(
      (result:any)=>{
        let printSpooCsv = result as PrintSpoolCsv;
        this.printSpoolArray.unshift(printSpooCsv)
        this.messageService.add({severity: 'success', summary: "Result", detail:"CSV print spool has been generated"});
        this.displaySpoolDialog = false;
        generateButton.disabled = false;
      },
      error => {
        console.log(error);
        this.messageService.add({severity: 'info', summary: 'Internal Error', detail: ''});
        this.displaySpoolDialog = false;
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

        this.printSpoolService.download(this.selectedPrintSpool.filename).subscribe((data) => {

          this.blob = new Blob([data]);
        
          var downloadURL = window.URL.createObjectURL(data);
          var link = document.createElement('a');
          link.href = downloadURL;
          link.download = this.selectedPrintSpool.filename;
          link.click();
          this.messageService.add({severity: 'success', summary: "Result", detail:"Download has started"});
        },
        error => {
          console.log(error);
          this.messageService.add({severity: 'info', summary: 'Download has failed', detail: ''});
          this.displaySpoolDialog = false;
        });

      }
    })
  };

  ngOnInit() {

    this.printSpoolService.getPrintSpools().subscribe(
      (result: any ) => {
        let printSpoolArray: PrintSpoolCsv[] = [];
        for (let i = 0; i < result.length; i++) {
          let printSpoolCSV = result[i] as PrintSpoolCsv;
          printSpoolArray.push(printSpoolCSV);
        }
        this.printSpoolArray = printSpoolArray;
      },
      error => {
        console.log(error);
      }
    );

    this.printSpoolService.getPeriods().subscribe(
      (result: any ) => {
        let periods: any[] = [];
        for (let i = 0; i < result.length; i++) {
          let period = result[i].monthYear as string;
          periods.push(period);
        }
        this.periods = periods;
      },
      error => {
        console.log(error);
      }
    );

    this.printSpoolService.updateBills().subscribe(
      (result: any) => {
        this.messageService.add({severity: 'success', summary: "Result", detail:result});
      },
      error => {
        console.log(error);
        this.messageService.add({severity: 'info', summary: 'Internal Error', detail: ''});
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

    this.cols = [
      {field: "period", header: "Period"},
      {field: "code", header: "Code"},
      {field: "created", header: "Created"},
    ];

  };

}