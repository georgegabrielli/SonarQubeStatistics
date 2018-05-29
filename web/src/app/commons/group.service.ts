import {Observable} from 'rxjs';
import {Group} from './group.model';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Message} from '@angular/compiler/src/i18n/i18n_ast';

@Injectable()
export class GroupService {
  private url = 'http://localhost:8081/groups';
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  message: Message[];

  constructor(private http: HttpClient) {
  }

  getGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(this.url);
  }

  delete(id: number) {
    const url = `${this.url}/${id}`;
    return this.http
      .delete(url, {headers: this.headers});
  }


  create(name: string) {
    const group = {name};
    return this.http.post(this.url, JSON.stringify({'group': group}), {headers: this.headers});
  }

  getGroup(id: number) {
    const url = `${this.url}/${id}`;
    return this.http.get<Group>(url);
  }

  updateGroup(group: Group) {
    const url = `${this.url}/${group.id}`;
    return this.http.put(url, JSON.stringify({'group': group}), {headers: this.headers});
  }
}
