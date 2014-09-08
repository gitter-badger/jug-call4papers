package org.jugvale.call4papers.inicio;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jugvale.call4papers.model.impl.Autor;
import org.jugvale.call4papers.model.impl.Evento;
import org.jugvale.call4papers.model.impl.Paper;
import org.jugvale.call4papers.model.impl.Usuario;

/**
 * 
 * Adiciona dados iniciais na aplicação no momento de deploy. O banco de dados
 * deve estar limpo, caso contrário, devemos verificar antes de adicionar dados
 * 
 * @author wsiqueir
 * 
 */
@Singleton
@Startup
public class CarregaDadosIniciais {
	
	@PersistenceContext
	EntityManager em;

	Logger log = Logger.getLogger(CarregaDadosIniciais.class.getCanonicalName());

	@PostConstruct
	public void carregaDadosIniciais() throws JsonGenerationException, JsonMappingException, IOException {
		log.fine("#### Salvando dados iniciais. #####");
		
		Usuario administrador = Usuario.administrador()
								 	   .comLogin("adm")
								 	   .comSenha("adm123").build();
		em.persist(administrador);
		
		Usuario mariaUsr = Usuario.autor()
								  .comLogin("Maria")
								  .comSenha("mariah").build();
		
		em.persist(mariaUsr);
		
		Usuario joseUsr = Usuario.autor()
								 .comLogin("Josevaldo")
								 .comSenha("jose123valdo").build();
		
		
		em.persist(joseUsr);	
		
		Evento grandeEvento =  Evento.newEvento()
									   .comNome("O Grande Evento")
									   .comDescricao("Esse é o melhor evento do mundo, o grande evento...")
									   .comDataInicio(new Date())
									   .comDataFim(new Date())
									   .noLocal("Rua dos grandes eventos")
									   .comSite("http://www.ograndeevento.com")
									   .aceitandoTrabalhos().build();
						
		em.persist(grandeEvento);
		
		Autor maria = Autor.newAutor()
							.comNome("Maria")
							.comEmail("meuemail@gmail.com")
							.comSite("www.mariajava.com")
							.comTelefone("(99) 9 9999-9999")
							.comMiniCV("Grande conhecida no mundo Java...")
							.comUsuario(mariaUsr).build();
		
		em.persist(maria);
		
		Autor jose = Autor.newAutor()
				   			.comNome("Josevaldo")
				   			.comEmail("josevaldoJava@gmail.com")
				   			.comSite("www.josevaldojava.com")
				   			.comTelefone("(99) 9 9999-9999")
				   			.comMiniCV("Mestre no mundo Java...")
				   			.comUsuario(joseUsr).build();
		
		em.persist(jose);
		
		Paper javaParaJaveiros = Paper.palestra()
									  .submetidoNaData(new Date())
									  .comDescricao("Java para quem ama Java. Java para javeiros")
									  .comTitulo("Java para javeiros")
									  .noEvento(grandeEvento)
									  .comAutor(maria)
									  .comAutor(jose).build();
		
		em.persist(javaParaJaveiros);
		
		log.fine("#### Dados iniciais salvos. ####");
	}
}