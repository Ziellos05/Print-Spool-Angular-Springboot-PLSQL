import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StratumTableComponent } from './stratum-table.component';

describe('StratumTableComponent', () => {
  let component: StratumTableComponent;
  let fixture: ComponentFixture<StratumTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StratumTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StratumTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
