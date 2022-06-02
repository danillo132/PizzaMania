package br.com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.model.Usuario;
import br.com.repository.UsuarioRepository;


@CrossOrigin
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario){
		
		String senhacrip = new BCryptPasswordEncoder().encode(usuario.getSenha());
		
		usuario.setSenha(senhacrip);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
	
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public String delete(@PathVariable("id") Long id) {
		
		usuarioRepository.deleteById(id);
		
		return "ok";
		
	}
	
	@PutMapping(value = "/atualizar", produces = "application/json")
	public ResponseEntity<Usuario> atualizarUser(@RequestBody Usuario usuario){
		
	
			
			
		Usuario userTemporario = usuarioRepository.findById(usuario.getId()).get();
		
		if(!userTemporario.getSenha().equals(usuario.getSenha())) {
			String senhacrip = new BCryptPasswordEncoder().encode(usuario.getSenha());
			
			usuario.setSenha(senhacrip);
		}
		
		
		
		Usuario usuariosalvo = usuarioRepository.save(usuario);
		
		
		return new ResponseEntity<Usuario>(usuariosalvo, HttpStatus.OK); 
		
	}
	
	
	@GetMapping(value = "/Perfil", produces = "application/json")
	public ResponseEntity<Usuario> listaPerfil() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String nome;    

		if (principal instanceof UserDetails) {
		    nome = ((UserDetails)principal).getUsername();
		   
		} else {
		    nome = principal.toString();
		   
		}
		
		Usuario usuario = usuarioRepository.findUserByEmail(nome);
		
	
		
		return  new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		
		
	}
	
}
