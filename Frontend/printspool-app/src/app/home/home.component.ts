import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  // @ts-ignore
  homePage: MenuItem[];

  // @ts-ignore
  home: MenuItem;

  constructor(private router: Router) {}

  ngOnInit() {
    // Breadcrumb
    this.homePage = [];

    this.home = { icon: 'pi pi-home' };
  }

  // Redirecciones a las funcionalidades para cada manager
  stratumRedirect() {
    this.router.navigate(['stratum']);
  }

  spoolRedirect() {
    this.router.navigate(['printspool']);
  }

  uploadRedirect() {
    this.router.navigate(['fileupload']);
  }
}
