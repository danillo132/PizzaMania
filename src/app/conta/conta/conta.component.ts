import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { UsuarioServiceService } from 'src/app/service/usuario-service.service';
import { CEPError, Endereco, NgxViacepService } from "@brunoc/ngx-viacep"; 
import { catchError, EMPTY, Observable } from 'rxjs';
import { Router } from '@angular/router';


@Component({
  selector: 'app-conta',
  templateUrl: './conta.component.html',
  styleUrls: ['./conta.component.scss']
})
export class ContaComponent implements OnInit {

  usuario = new User();
  usuarios:Array<User>;

  constructor(private userService : UsuarioServiceService,private viacep: NgxViacepService,private router : Router ) { }

  ngOnInit(): void {

    if(this.userService.userAutenticado() === true){
      this.userService.listaPerfil().subscribe(data =>{
        this.usuario = data;
        this.usuarios = data.content;
        
      });
  }
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
    this.usuario.bairro = endereco.bairro;
    this.usuario.cidade = endereco.localidade;
    this.usuario.logradouro = endereco.logradouro;
    this.usuario.estado = endereco.uf;
    
  });
  }

  deleteUsuario(id: Number) {

    if(confirm("Deseja mesmo excluir sua conta?")){

  

  this.userService.deletarUsuario(id).subscribe(data => {
    

  });
  localStorage.clear();
  this.router.navigate(['pizzaMania']);
}
}


  salvarUser(){

     

    
      if(this.usuario.id != null && this.usuario.id.toString().trim() != null){

        this.userService.updateUsuario(this.usuario).subscribe(data =>{
          
          
        })

      }else {
        this.userService.salvarUsuario(this.usuario).subscribe(data =>{
          
           
        });
      }
  }



}
