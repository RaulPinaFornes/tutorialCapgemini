import { Injectable } from '@angular/core';
import { Pageable } from '../core/model/page/Pageable';
import { AuthorPage } from '../core/model/page/AuthorPage';
import { AUTHOR_DATA } from './model/mock-author';
import { Observable, of } from 'rxjs'
import { Author } from './model/Author';
import { HttpClient } from '@angular/common/http';
import { AUTHOR_DATA_LIST } from './model/mock-author-list';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(
    private http:HttpClient
  ) { }

  getAuthor(pageable: Pageable): Observable<AuthorPage> {
    return this.http.post<AuthorPage>('http://localhost:8080/author', {pageable:pageable});
  }

  saveAuthor(author:Author): Observable<void> {
    let url = 'http://localhost:8080/author';
    if(author.id != null) url += '/' +author.id;

    return this.http.put<void>(url, author);
  }

  deleteAuthor(idAuthor: number): Observable<void> {
    return this.http.delete<void>('http://localhost:8080/author/' + idAuthor);
  }

  getAllAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>('http://localhost:8080/author');
}
}
