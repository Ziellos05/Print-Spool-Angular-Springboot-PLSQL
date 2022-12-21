import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { StratumService } from 'src/app/service/stratum.service';
import { Stratum } from '../../models/stratum';

@Component({
  selector: 'app-stratum-table',
  templateUrl: './stratum-table.component.html',
  styleUrls: ['./stratum-table.component.css'],
})
export class StratumTableComponent implements OnInit {
  stratumArray: Stratum[];
  cols: any[];

  items: MenuItem[];

  displaySaveDialog: boolean = false;

  displayEditDialog: boolean = false;

  stratum = new Stratum;

  selectedStratum= new Stratum;

  constructor(private stratumService: StratumService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService) {}

  showSaveDialog() {
    this.stratum = new Stratum;
    this.displaySaveDialog = true;
  }

  showEditDialog() {
      if(this.selectedStratum! && this.selectedStratum.id!) {
        this.stratum = this.selectedStratum;
      } else {
        this.messageService.add({severity : 'warn', summary: "Warning!", detail: "Select a register first"})
        return;
      }
    this.displayEditDialog = true;
  }

  saveStratum() {
    this.stratumService.saveStratum(this.stratum).subscribe(
      (result:any)=>{
        let stratum = result as Stratum;
        this.stratumArray.push(stratum)
        this.messageService.add({severity: 'success', summary: "Result", detail:"Stratum has been added"});
        this.displaySaveDialog = false;
      },
      error => {
        if (error.status == 409) {
          this.messageService.add({severity: 'success', summary: "This stratum already exist!", detail:""});
          console.log(error);
        } else {
        this.messageService.add({severity: 'success', summary: "Internal Error", detail:""});
        console.log(error);
        }
      }
    );
  }

  editStratum() {
    this.stratumService.editStratum(this.stratum).subscribe(
      (result:any)=>{
        this.messageService.add({severity: 'success', summary: "Result", detail:"Stratum has been edited"});
        this.displayEditDialog = false;
      },
      error => {
        this.messageService.add({severity: 'success', summary: "Internal Error", detail:""});
        console.log(error);
      }
    );
  };

  deleteStratum(){
    if(this.selectedStratum.id < 7) {
      this.messageService.add({severity : 'warn', summary: "Warning!", detail: "You can't delete this stratum!"})
      return;
    }
    if(this.selectedStratum! && this.selectedStratum.id!) {
      this.stratum = this.selectedStratum;
    } else {
      this.messageService.add({severity : 'warn', summary: "Warning!", detail: "Select a register first"})
      return;
    }
    this.confirmationService.confirm({
      message: "Are you sure do you want to delete this stratum?",
      accept: () => {
        this.stratumService.deleteStratum(this.selectedStratum.id).subscribe(
          (result: any) => {
            this.messageService.add({severity: 'success', summary: "Result", detail:result});
            this.deleteObject(this.selectedStratum.id);
            this.selectedStratum = new Stratum;
          },
          error => {
            this.messageService.add({severity: 'success', summary: "Internal Error", detail:""});
            console.log(error);
          }
        )
      }
    })
  };

  deleteObject(id:number){
    let index = this.stratumArray.findIndex((e) => e.id == id);
    if(index != -1) {
      this.stratumArray.splice(index, 1);
    }
  }

  ngOnInit() {
    this.stratumService.getStratums().subscribe(
      (result: any) => {
        let stratumArray: Stratum[] = [];
        for (let i = 0; i < result.length; i++) {
          let stratum = result[i] as Stratum;
          stratumArray.push(stratum);
        }
        this.stratumArray = stratumArray;
      },
      (error) => {
        console.log(error);
      }
    );

    this.items = [
      {
        label: 'New',
        icon: 'pi pi-fw pi-plus',
        command: () => this.showSaveDialog(),
      },
      {
        label: 'Edit',
        icon: 'pi pi-fw pi-pencil',
        command: () => this.showEditDialog(),
      },
      {
        label: 'Delete',
        icon: 'pi pi-fw pi-times',
        command: () => this.deleteStratum()
      },
    ];

    this.cols = [
      {field: "id", header: "Stratum"},
      {field: "costPerCm", header: "Cost per CM"},
      {field: "businessDays", header: "Business days"},
    ];

  }
}
