// @angular imports
import { Component, OnInit } from '@angular/core';

// PrimeNG imports
import { ConfirmationService, MenuItem , MessageService } from 'primeng/api';

// Import propios
import { PrintSpoolService } from 'src/app/service/print-spool.service';
import { PrintSpoolCsv } from '../../models/print-spool-csv'
import { SpoolConfig } from '../../models/spool-config'

interface Period {
  date: string;
}

@Component({
  selector: 'app-print-spool-table',
  templateUrl: './print-spool-table.component.html'
})
export class PrintSpoolTableComponent {

  // Array con la información de los spools de impresión
  printSpoolArray: PrintSpoolCsv[];

  // Array para iterar con los ngFor en la tabla
  cols: any[];

  // Objeto de tipo PrintSpoolCSV
  printSpool: PrintSpoolCsv;

  // Objeto de tipo PrintSpoolCSV que señala al elemento seleccionado
  selectedPrintSpool: PrintSpoolCsv;

  // Items de la barra de funciones
  items: MenuItem[];
  
  // Boolean para mostrar o no el modal de generación
  displaySpoolDialog: boolean = false;

  // Array que recibe los periodos hasta la fecha desde la API
  periods: any[] = [];

  // Objeto que se envia a la API para generar el Print Spool
  spoolConfig: SpoolConfig = {
    date: "",
    stratum: false,
    avgConsumption: false,
    lastConsumption: false,
    nconsumptions: 1
  }

  blob: Blob;

  constructor(private printSpoolService : PrintSpoolService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService) {

  }

  showSpoolDialog(){
  this.displaySpoolDialog = true;
  }

  // Función que llamada a la API para generar un Print Spool
  generatePrintSpool(generateButton: any){
    generateButton.disabled = true;
    this.printSpoolService.generatePrintSpool(this.spoolConfig).subscribe(
      (result:any)=>{
        this.displaySpoolDialog = false;
        generateButton.disabled = false;
        let printSpooCsv = result as PrintSpoolCsv;
        this.printSpoolArray.unshift(printSpooCsv)
        this.messageService.add({severity: 'success', summary: "Success", detail:"CSV print spool has been generated"});
      },
      error => {
        console.log(error);
        this.messageService.add({severity: 'error', summary: 'Internal Error', detail: ''});
        this.displaySpoolDialog = false;
      }
    );
  }

  // Función que llama a la API para descargar un Print Spool
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
          this.messageService.add({severity: 'success', summary: "Success", detail:"Download has started"});
        },
        error => {
          console.log(error);
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'Download has failed'});
          this.displaySpoolDialog = false;
        });

      }
    })
  };

  ngOnInit() {

    // Obtención del historias de spools de impresión generados
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

    // Obtención de los periodos a través de la API
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

    // Actualización de las facturas hasta el día actual a través de la API
    this.printSpoolService.updateBills().subscribe(
      (result: any) => {
        this.messageService.add({severity: 'success', summary: "Success", detail:result});
      },
      error => {
        console.log(error);
        this.messageService.add({severity: 'error', summary: 'Internal Error', detail: ''});
      }
    );

    this.items = [
      {
        label: 'Generate',
        icon: 'pi pi-file',
        command: () => this.showSpoolDialog()
      },
      {
        label: 'Download',
        icon: 'pi pi-download',
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