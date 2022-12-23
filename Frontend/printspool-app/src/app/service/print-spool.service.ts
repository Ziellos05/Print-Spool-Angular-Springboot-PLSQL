import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SpoolConfig } from '../models/spool-config';

@Injectable({
  providedIn: 'root',
})
export class PrintSpoolService {
  baseURL: string = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  // Servicio para obtener el Print Spools
  getPrintSpools(): Observable<any> {
    return this.http.get(this.baseURL + 'spool');
  }

  // Servicio para actualizar las facturas al momento actual
  updateBills(): Observable<any> {
    return this.http.get(this.baseURL + 'bills', { responseType: 'text' });
  }

  // Servicio para generar un Print Spool
  generatePrintSpool(spoolConfig: SpoolConfig): Observable<any> {
    let headers = new HttpHeaders();

    headers = headers.set('Content-Type', 'application/json');

    return this.http.post(this.baseURL + 'spool', JSON.stringify(spoolConfig), {
      headers: headers,
    });
  }

  // Servicio para descargar el Print Spool
  download(link: string): Observable<any> {
    let params = new HttpParams();
    params = params.append('file', link);
    return this.http.get(this.baseURL + 'spool/download', {
      params: params,
      responseType: 'blob',
    });
  }

  // Servicio para obtener los periodos al momento actual
  getPeriods(): Observable<any> {
    return this.http.get(this.baseURL + 'period');
  }
}
