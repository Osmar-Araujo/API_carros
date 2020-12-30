
package com.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarrosApplicationTests {

	@Autowired
	private CarroService service;
	
	@Test
	public void testeSave() {
		Carro carro = new Carro();
		carro.setNome("Porshe");
		carro.setTipo("esportivos");
		
		CarroDTO c = service.insert(carro);
		Long id=c.getId();
		assertNotNull(id);
		
		Optional<CarroDTO> op = service.getCarroById(id);
		assertTrue(op.isPresent());
		
		c = op.get();
		assertEquals("Ferrari",c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		service.delete(id);
		
		assertFalse(service.getCarroById(id).isPresent());
		
	}
	@Test
	public void testeLista() {
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30, carros.size());
		
	}

}
