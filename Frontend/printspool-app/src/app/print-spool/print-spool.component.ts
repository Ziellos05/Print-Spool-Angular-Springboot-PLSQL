// @angular imports
import { Component } from '@angular/core';
import { Router } from '@angular/router';

// Imports propios
import { PrintSpoolCsv } from '../models/print-spool-csv';

@Component({
  selector: 'app-print-spool',
  templateUrl: './print-spool.component.html',
})
export class PrintSpoolComponent {
  printSpoolCsvArray: PrintSpoolCsv[];

  // @ts-ignore
  printSpoolPage: MenuItem[];

  // @ts-ignore
  home: MenuItem;

  constructor(private router: Router) {}

  ngOnInit() {
    // Breadcrumb
    this.printSpoolPage = [{ label: 'Print Spool' }];

    this.home = { icon: 'pi pi-home' };
  }

  homeRedirect() {
    // Breadcrumb navigation
    this.router.navigate(['']);
  }
}
