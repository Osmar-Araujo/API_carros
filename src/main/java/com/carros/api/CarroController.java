package com.carros.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {
	
	@Autowired
	private CarroService service;
	
	@GetMapping
	public ResponseEntity<List<CarroDTO>> get() {
		return ResponseEntity.ok(service.getCarros());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>  get(@PathVariable("id") Long id) {
		Optional<CarroDTO> carro =service.getCarroById(id);
		return carro.isPresent() ?
				ResponseEntity.ok(carro.get()):
				ResponseEntity.notFound().build();
	}
	
	@GetMapping("tipo/{tipo}")
	public ResponseEntity<List<CarroDTO>> getCarosByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carros = service.getCarroByTipo(tipo);
		
		return carros.isEmpty() ?
				ResponseEntity.noContent().build():
				ResponseEntity.ok(carros);
	}
	
	@PostMapping
	public ResponseEntity<?> post(@RequestBody Carro carro) {
		try {
			
			CarroDTO c = service.insert(carro);
			
			URI location = getUri(c.getId());
			
			return ResponseEntity.created(location).build();
			
		}catch (Exception ex){
			return ResponseEntity.badRequest().build();
		}
	}
	
	private URI getUri(Long id) {
		
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/delete/{id}").buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		carro.setId(id);
		CarroDTO c = service.update(carro, id);
		return c != null ?
				ResponseEntity.ok(c):
				ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		boolean ok = service.delete(id);
	
		return ok ?
				ResponseEntity.ok().build() :
					ResponseEntity.notFound().build();
	}
}