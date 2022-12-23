import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { StratumService } from 'src/app/service/stratum.service';
import { Stratum } from '../../models/stratum';

@Component({
  selector: 'app-stratum-table',
  templateUrl: './stratum-table.component.html',
})
export class StratumTableComponent implements OnInit {
  // Array que recibe los estratos
  stratumArray: Stratum[];

  // Array con información que permite iterar con ngFor para generar la tabla
  cols: any[];

  // Items de la barra de funciones
  items: MenuItem[];

  // Boolean para mostrar u ocultar el componente de guardar
  displaySaveDialog: boolean = false;

  // Boolean para mostrar u ocultar el componente de editar
  displayEditDialog: boolean = false;

  // Objeto de tipo estrato para realizar CRUD sobre los estratos
  stratum = new Stratum();

  // Objeto de tipo estrato para los estratos seleccionados
  selectedStratum = new Stratum();

  constructor(
    private stratumService: StratumService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  showSaveDialog() {
    this.stratum = new Stratum();
    this.displaySaveDialog = true;
  }

  showEditDialog() {
    if (this.selectedStratum! && this.selectedStratum.id!) {
      this.stratum = this.selectedStratum;
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning!',
        detail: 'Select a register first',
      });
      return;
    }
    this.displayEditDialog = true;
  }

  // Función que guarda un nuevo estrato
  saveStratum() {
    this.stratumService.saveStratum(this.stratum).subscribe(
      (result: any) => {
        let stratum = result as Stratum;
        this.stratumArray.push(stratum);
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Stratum has been added',
        });
        this.displaySaveDialog = false;
      },
      (error) => {
        if (error.status == 409) {
          this.messageService.add({
            severity: 'warn',
            summary: 'Warning!',
            detail: 'This stratum already exist!',
          });
          console.log(error);
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Internal Error',
            detail: '',
          });
          console.log(error);
        }
      }
    );
  }

  // Funcióm que edita un estrato existente
  editStratum() {
    this.stratumService.editStratum(this.stratum).subscribe(
      (result: any) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Stratum has been edited',
        });
        this.displayEditDialog = false;
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Internal Error',
          detail: '',
        });
        console.log(error);
      }
    );
  }

  // Función que elimina un estrato
  deleteStratum() {
    if (this.selectedStratum! && this.selectedStratum.id!) {
      this.stratum = this.selectedStratum;
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning!',
        detail: 'Select a register first',
      });
      return;
    }
    if (this.selectedStratum.id < 7) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning!',
        detail: "You can't delete this stratum!",
      });
      return;
    }
    this.confirmationService.confirm({
      message: 'Are you sure do you want to delete this stratum?',
      accept: () => {
        this.stratumService.deleteStratum(this.stratum.id).subscribe(
          (result: any) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: result,
            });
            this.deleteObject(this.stratum.id);
            this.selectedStratum = new Stratum();
          },
          (error) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Internal Error',
              detail: '',
            });
            console.log(error);
          }
        );
      },
    });
  }

  // Función que elimina el objeto en la tabla directamente, respuesta dinámica
  deleteObject(id: number) {
    let index = this.stratumArray.findIndex((e) => e.id == id);
    if (index != -1) {
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
        command: () => this.deleteStratum(),
      },
    ];

    this.cols = [
      { field: 'id', header: 'Stratum' },
      { field: 'costPerCm', header: 'Cost per CM' },
      { field: 'businessDays', header: 'Business days' },
    ];
  }
}
