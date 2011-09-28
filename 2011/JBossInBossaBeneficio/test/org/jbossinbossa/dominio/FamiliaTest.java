package org.jbossinbossa.dominio;

import static org.junit.Assert.*;

import org.jbossinbossa.dominio.entidade.Familia;
import org.jbossinbossa.dominio.entidade.Pessoa;
import org.junit.Test;

public class FamiliaTest {

	@Test
	public void testGetRendaPercapta() {
		Pessoa p1 = new Pessoa();
		
		p1.setRenda(35);
		
		Pessoa p2 = new Pessoa();
		p2.setRenda(30);
		
		Familia familia = new Familia();
		familia.getMembros().add(p1);
		familia.getMembros().add(p2);
		
		assertEquals(familia.getRendaPercapta(), 32.5, 0);
	}

}
