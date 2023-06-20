import { Injectable } from '@angular/core';
import { Lending } from './model/Lending';
import { Observable, of } from 'rxjs';
import { MOCK_LENDING } from './model/mock-lending';
import { Pageable } from '../core/model/page/Pageable';
import { LendingPage } from '../core/model/page/LendingPage';
import { HttpClient } from '@angular/common/http';
import { LendingFilter } from './model/LendingFilter';
@Injectable({
  providedIn: 'root'
})
export class LendingService {

  constructor(
    private http: HttpClient
  ) { }

  getAllLendings(): Observable<Lending[]> {
    const url:string = 'http://localhost:8080/lending';
    return this.http.get<Lending[]>(url);
  }
  getLendings (pageAble:Pageable, lendingFilter:LendingFilter): Observable<LendingPage> {
    const url:string = 'http://localhost:8080/lending';
    const body = {
      pageable:pageAble,
      lendingFilter:lendingFilter
    };
    return this.http.post<LendingPage>(url, body);
  }

  saveLending(lending:Lending): Observable<any> {
    const url:string = 'http://localhost:8080/lending';
    return this.http.put(url, lending)
  }

  deleteLending(idLending:number): Observable<any> {
    const url = `http://localhost:8080/lending/${idLending}`;
    return this.http.delete<void>(url);
  }
}
