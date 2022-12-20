import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrintSpoolComponent } from './print-spool.component';

describe('PrintSpoolComponent', () => {
  let component: PrintSpoolComponent;
  let fixture: ComponentFixture<PrintSpoolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrintSpoolComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrintSpoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
