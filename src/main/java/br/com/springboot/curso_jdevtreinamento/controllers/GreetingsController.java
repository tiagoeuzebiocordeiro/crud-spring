package br.com.springboot.curso_jdevtreinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdevtreinamento.model.Usuario;
import br.com.springboot.curso_jdevtreinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 * O controller é responsável por interceptar os dados de uma aplicação
 * Tudo que vem da tela de um sistema, requisição de navegador, aplicativo, tudo vai cair em um controller mapeado
 * pra depois executar os métodos (gravar, consultar) sempre temos que ter um controller para mapear a nossa requisição
 * a essência do spring boot é ter essa anotação de Controlador REST.
 */
@RestController
public class GreetingsController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public String metodoOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);
    	
    	return nome + ", diga ola mundo! :)";
    }
    
    
    @GetMapping(value = "listatodos")
    @ResponseBody /*Retorna o corpo da requisição*/
    public ResponseEntity<List<Usuario>> listaUsuario() {
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); /*Executa a consulta no banco de dados*/
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /*Retorna a lista em json*/
    }
    
    
    @PostMapping(value = "salvar") /*Mapeamento de url*/
    @ResponseBody /*Descrição resposta*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED); // CRIADO
    }
    
    @DeleteMapping(value = "delete") /*Mapeamento de url*/
    @ResponseBody /*Descrição resposta*/
    public ResponseEntity<String> delete(@RequestParam Long idUser) {
    	
    	usuarioRepository.deleteById(idUser);
    	
    	return new ResponseEntity<String>("Usuário deletado com sucesso.", HttpStatus.OK); //Deletado
    }
    
    @GetMapping(value = "buscaruserid") /*Mapeamento de url*/
    @ResponseBody /*Descrição resposta*/
    public ResponseEntity<Usuario> usuario(@RequestParam(name = "iduser") Long iduser) { /*Recebe os dados para consultar*/
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK); //Buscado
    }
    
    @PutMapping(value = "atualizar") /*Mapeamento de url*/
    @ResponseBody /*Descrição resposta*/
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { /*Se é pra atualizar é obrigatorio ter id*/
    	
    	if (usuario.getId() == null) {
    		return new ResponseEntity<String>("ID não foi informado.", HttpStatus.OK); // ID NULO = NÃO DÁ PRA ATUALIZAR.
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK); // CRIADO
    }
    
    @GetMapping(value = "buscarPorNome") /*Mapeamento de url*/
    @ResponseBody /*Descrição resposta*/
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { /*Recebe os dados para consultar*/
    
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK); //Buscado
    }
    
}
