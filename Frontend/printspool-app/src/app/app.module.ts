import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import {CardModule} from 'primeng/card';
import {ButtonModule} from 'primeng/button';
import { StratumComponent } from './stratum/stratum.component';
import { HomeComponent } from './home/home.component';
import { PrintSpoolComponent } from './print-spool/print-spool.component';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { StratumTableComponent } from './stratum/stratum-table/stratum-table.component';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService, MessageService} from 'primeng/api';
import {InputNumberModule} from 'primeng/inputnumber';
import { PanelModule } from 'primeng/panel';
import { HttpClientModule } from '@angular/common/http';
import { PrintSpoolTableComponent } from './print-spool/print-spool-table/print-spool-table.component';
import { FileUploadTableComponent } from './file-upload/file-upload-table/file-upload-table.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { FormsModule } from '@angular/forms';
import {DropdownModule} from 'primeng/dropdown';
import {FileUploadModule} from 'primeng/fileupload';
import {ToastModule} from 'primeng/toast';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    StratumComponent,
    HomeComponent,
    PrintSpoolComponent,
    FileUploadComponent,
    StratumTableComponent,
    PrintSpoolTableComponent,
    FileUploadTableComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    BreadcrumbModule,
    CardModule,
    ButtonModule,
    AppRoutingModule,
    TableModule,
    DialogModule,
    ConfirmDialogModule,
    InputNumberModule,
    HttpClientModule,
    PanelModule,
    MenubarModule,
    InputTextModule,
    CheckboxModule,
    CascadeSelectModule,
    FormsModule,
    DropdownModule,
    FileUploadModule,
    ToastModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
