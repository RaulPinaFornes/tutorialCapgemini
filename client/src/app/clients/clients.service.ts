import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Client } from './model/Client';
import { CLIENTS_DATA } from './model/mock-clients'
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  constructor(
    private http: HttpClient
  ) { }

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>('http://localhost:8080/client');
  }

  saveClient(client:Client): Observable<Client> {
    let url = 'http://localhost:8080/client'
    if(client.id != null)
      url += '/' + client.id;
    return this.http.put<Client>(url, client);
  }
  deleteClient(id: number): Observable<any> {
    return this.http.delete('http://localhost:8080/client/' + id);
  }
}
