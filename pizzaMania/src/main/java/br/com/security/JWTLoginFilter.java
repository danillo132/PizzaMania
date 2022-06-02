package br.com.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.model.Usuario;



public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		//Obrigamos a autenticar a URL
		super(new AntPathRequestMatcher(url));
		
		setAuthenticationManager(authenticationManager);
		
	}

	
	//Retorna o usuário ao processar a autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		
		//Retorna o usuario, login, senha e acessos
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSenha()));
	}
	
		@Override
		protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
				Authentication authResult) throws IOException, ServletException {
			
			
			new JWTTokeAutenticacaoService().addAuthentication(response, authResult.getName());
		}

}
