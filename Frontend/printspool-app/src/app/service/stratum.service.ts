import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Stratum } from '../models/stratum';

@Injectable({
  providedIn: 'root'
})
export class StratumService {

  baseURL: string = "http://localhost:8080/stratum"

  constructor(private http:HttpClient) { }

  getStratums() : Observable<any>{
    return this.http.get(this.baseURL)
  }

  saveStratum(stratum: Stratum): Observable<any>{

    let headers = new HttpHeaders();

    headers = headers.set('Content-Type', 'application/json');

    return this.http.post(this.baseURL, JSON.stringify(stratum), {headers: headers});
  }

  editStratum(stratum: Stratum): Observable<any>{

    let headers = new HttpHeaders();

    headers = headers.set('Content-Type', 'application/json');

    return this.http.put(this.baseURL+"/"+stratum.id, JSON.stringify(stratum), {headers: headers});
  }

  deleteStratum(id: number) : Observable<any>{
    return this.http.delete(this.baseURL+"/"+id, { responseType: 'text' });
  }

}
