import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SpoolConfig } from '../models/spool-config';

@Injectable({
  providedIn: 'root'
})
export class PrintSpoolService {

  baseURL: string = "http://localhost:8080/"

  constructor(private http:HttpClient) { }

  getPrintSpools() : Observable<any>{
    return this.http.get(this.baseURL+"spool")
  }

  updateBills() : Observable<any>{
    return this.http.get(this.baseURL+"bills", { responseType: 'text' })
  }

  generatePrintSpool(spoolConfig: SpoolConfig): Observable<any>{

    let headers = new HttpHeaders();

    headers = headers.set('Content-Type', 'application/json');

    return this.http.post(this.baseURL+"spool", JSON.stringify(spoolConfig), {headers: headers});
  }

  download(link: string) : Observable<any>{
    let params = new HttpParams();
    params = params.append('file', link);
    return this.http.get(this.baseURL+"spool/download", {params: params, responseType: 'blob'});
  }

  getPeriods() : Observable<any>{
    return this.http.get(this.baseURL+"period")
  }

}
