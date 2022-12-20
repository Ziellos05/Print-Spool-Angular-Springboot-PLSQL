import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DownloadService {

  baseURL: string = "http://localhost:8080/download"

  constructor(private http:HttpClient) { }

  download(link: string) : Observable<any>{
    let params = new HttpParams();
    params = params.append('file', link);
    return this.http.get(this.baseURL, {params: params, responseType: 'blob'});
  }

}
