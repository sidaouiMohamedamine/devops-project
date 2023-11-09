import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Skier } from './skier';

@Injectable({
  providedIn: 'root'
})
export class SkierService {



  constructor(private http:HttpClient) { }




  addSkier(skier: Skier):Observable<Object> {
    return  this.http.post("http://localhost:8089/skier/add",skier);
  }


  getSkier():Observable<Skier[]>{
    return this.http.get<Skier[]>("http://localhost:8089/skier/all")
  }

}
