import { Component } from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  // @ts-ignore
  homePage: MenuItem[];
  // @ts-ignore
  printSpoolPage: MenuItem[];
  // @ts-ignore
  stratumPage: MenuItem[];
  // @ts-ignore
  uploadPage: MenuItem[];

  // @ts-ignore
  home: MenuItem;

  ngOnInit() { 
    this.homePage = [];
    
    this.printSpoolPage = [{label:'Print Spool'}];

    this.stratumPage = [{label:'Stratum terms'}];

    this.uploadPage = [{label:'Upload files'}];

    this.home = {icon: 'pi pi-home'};
}

}
