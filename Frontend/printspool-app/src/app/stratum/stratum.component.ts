import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-stratum',
  templateUrl: './stratum.component.html',
})
export class StratumComponent {
  // @ts-ignore
  stratumPage: MenuItem[];

  // @ts-ignore
  home: MenuItem;

  constructor(private router: Router) {}

  ngOnInit() {
    // Breadcrumb
    this.stratumPage = [{ label: 'Stratum terms' }];

    this.home = { icon: 'pi pi-home' };
  }

  homeRedirect() {
    this.router.navigate(['']);
  }
}
