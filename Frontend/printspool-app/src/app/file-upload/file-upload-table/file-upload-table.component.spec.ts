import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileUploadTableComponent } from './file-upload-table.component';

describe('FileUploadTableComponent', () => {
  let component: FileUploadTableComponent;
  let fixture: ComponentFixture<FileUploadTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FileUploadTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileUploadTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
