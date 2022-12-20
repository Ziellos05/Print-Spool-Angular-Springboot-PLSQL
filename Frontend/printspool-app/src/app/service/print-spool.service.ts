import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SpoolConfig } from '../models/spool-config';

@Injectable({
  providedIn: 'root'
})
export class PrintSpoolService {

  baseURL: string = "http://localhost:8080/"

  downloadURL: string = "file:///C:/Users/1152707384/Desktop/Requerimiento%20N16/Desarrollo/Backend/PrintSpool/"

  constructor(private http:HttpClient) { }

  getPrintSpools() : Observable<any>{
    return this.http.get(this.baseURL+"spool")
  }

  updatePrintSpools() : Observable<any>{
    return this.http.get(this.baseURL+"bills", { responseType: 'text' })
  }

  generatePrintSpool(spoolConfig: SpoolConfig): Observable<any>{

    let headers = new HttpHeaders();

    headers = headers.set('Content-Type', 'application/json');

    return this.http.post(this.baseURL+"spool", JSON.stringify(spoolConfig), {headers: headers});
  }

}
