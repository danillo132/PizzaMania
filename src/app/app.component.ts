import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { ContaComponent } from './conta/conta/conta.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'pizzamania';


  constructor(private router : Router ){}

  ngOnInit(): void {
    
    
  }

 

  public sair(){
      localStorage.clear();
      this.router.navigate(['login']);
      
  }

  public esconderBarra(){
    if(localStorage.getItem('token') != null && localStorage.getItem('token')?.toString().trim() != null){
      return false;
  }else{
    return true;
  }
  }
}
