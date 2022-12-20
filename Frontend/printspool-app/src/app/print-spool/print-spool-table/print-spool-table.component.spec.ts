import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrintSpoolTableComponent } from './print-spool-table.component';

describe('PrintSpoolTableComponent', () => {
  let component: PrintSpoolTableComponent;
  let fixture: ComponentFixture<PrintSpoolTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrintSpoolTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrintSpoolTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
