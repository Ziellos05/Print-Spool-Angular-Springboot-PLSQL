import { HttpClient } from '@angular/common/http';
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
}
