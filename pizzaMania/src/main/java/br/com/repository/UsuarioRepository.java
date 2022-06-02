package br.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	
	@Query("Select u from Usuario u where u.email = ?1")
	Usuario findUserByEmail(String email);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update Usuario set token = ?1 where email = ?2")
	void atualizaTokenUser(String token,String email);
}
