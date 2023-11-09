import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Skier } from '../skier';
import { SkierService } from '../skier.service';


@Component({
  selector: 'app-add-skier',
  templateUrl: './add-skier.component.html',
  styleUrls: ['./add-skier.component.css']
})
export class AddSkierComponent {
  skier=new Skier()
  constructor(private skierService:SkierService,private router:Router) { }

  ngOnInit(): void {
  }

  send() {
      this.skierService.addSkier(this.skier).subscribe(data=>{
        console.log('data',this.skier);
        this.router.navigate(['/skier'])
      },
        error=>console.log('erreur')
    )}



}
