import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  baseURL: string = "http://localhost:8080/upload"

  constructor(private http:HttpClient) { }

  getFileUploads() : Observable<any>{
    return this.http.get(this.baseURL)
  }

  download(link: string) : Observable<any>{
    let params = new HttpParams();
    params = params.append('file', link);
    return this.http.get(this.baseURL+"/download", {params: params, responseType: 'blob'});
  }

}
