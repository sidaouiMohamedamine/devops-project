import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Skier } from '../skier';
import { SkierService } from '../skier.service';

@Component({
  selector: 'app-skier-list',
  templateUrl: './skier-list.component.html',
  styleUrls: ['./skier-list.component.css']
})
export class SkierListComponent {
  skier:Skier[]

  constructor(private skierService:SkierService,private router:Router) { }

  ngOnInit(): void {
    this.getSkier()
  }


  getSkier(){
    this.skierService.getSkier().subscribe(data=>{
      this.skier=data;
      console.log(data)
    })
}


        add() {
          this.router.navigate(['/'])
        }





}
