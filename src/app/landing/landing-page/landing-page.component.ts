import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UsuarioServiceService } from 'src/app/service/usuario-service.service';
import { CEPError, Endereco, NgxViacepService } from "@brunoc/ngx-viacep"; 
import { catchError } from 'rxjs';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {

  usuario = new User();

  constructor(private router : Router,private userService : UsuarioServiceService, private viacep: NgxViacepService) { }

  ngOnInit(): void {
  }

  buscaCep(cep: string) {
    
    this.viacep
  .buscarPorCep(cep)
  .pipe(
    catchError((error: CEPError) => {
      // Ocorreu algum erro :/
      console.log(error);
      return 'a';
    })
  )
  .subscribe((endereco: Endereco) => {
    // Endereço retornado :)
    
    this.usuario.cidade = endereco.localidade;
    this.usuario.logradouro = endereco.logradouro;
    
    
  });
  }

  salvarUser(){

     

    
    if(this.usuario.id != null && this.usuario.id.toString().trim() != null){

      this.userService.updateUsuario(this.usuario).subscribe(data =>{
        
        
      })

    }else {
      this.userService.salvarUsuario(this.usuario).subscribe(data =>{
        this.novo();
         
      });
    }

   
}


novo(){
  this.usuario = new User();
  
}
}
