import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../app-constants';

@Injectable({
  providedIn: 'root'
})
export class UsuarioServiceService {

  constructor(private http: HttpClient) { }


  userAutenticado(){
    if(localStorage.getItem('token') != null && localStorage.getItem('token')?.toString().trim() != null){
      return true;
  }else{
    return false;
  }
  }

  deletarUsuario(id: Number) : Observable<any>{

    return this.http.delete(AppConstants.baseUrl + id, {responseType : 'text'});
  }

  salvarUsuario(user) : Observable<any>{
    return this.http.post<any>(AppConstants.baseUrl + "cadastrar", user);
  }

  updateUsuario(user) : Observable<any>{
    return this.http.put<any>(AppConstants.baseUrl + "atualizar", user);
  }

  listaPerfil(): Observable<any>{
    return this.http.get<any>(AppConstants.baseUrl + "Perfil");
    
  }
  
}
