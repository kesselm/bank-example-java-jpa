import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { BANKEN_API, KONTEN_API} from "../util/http.constants";
import { BankDAO } from "../dao/bankDAO.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  private url = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  headers = new HttpHeaders()
    .set('content-type','application/json')
    .set('Access-Control-Allow-Origin','*');

  getBanks(): Observable<BankDAO[]> {
    return this.http.get<BankDAO[]>(this.url+BANKEN_API, {'headers':this.headers});
  }

  getBankById(id: any): Observable<BankDAO> {
    return this.http.get<BankDAO>(`${this.url}/${id}`);
  }

  saveBank(data: any): Observable<any> {
    return this.http.post(this.url+BANKEN_API, BankDAO);
  }
}
