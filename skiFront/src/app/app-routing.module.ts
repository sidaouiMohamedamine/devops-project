import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddSkierComponent } from './add-skier/add-skier.component';
import { SkierListComponent } from './skier-list/skier-list.component';

const routes: Routes = [
  {path:'addSkier', component:AddSkierComponent,pathMatch:'full'},
  {path:'skier',component:SkierListComponent},
  {path:'' ,redirectTo:'addSkier',pathMatch:'full'},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
