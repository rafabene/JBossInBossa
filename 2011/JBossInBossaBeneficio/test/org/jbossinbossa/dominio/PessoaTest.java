package org.jbossinbossa.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jbossinbossa.dominio.entidade.Pessoa;
import org.junit.Assert;
import org.junit.Test;

public class PessoaTest {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Test
	public void testIdadeAniversarioFoiEsteAno() throws ParseException {
		Pessoa p = new Pessoa();
		Date dataNascimento = sdf.parse("10/06/1977");
		p.setDataNascimento(dataNascimento);
		Assert.assertEquals(p.getIdade(), 34);
	}
	
	@Test
	public void testIdadeAniversarioSeraEsteAno() throws ParseException {
		Pessoa p = new Pessoa();
		Date dataNascimento = sdf.parse("10/11/1977");
		p.setDataNascimento(dataNascimento);
		Assert.assertEquals(p.getIdade(), 33);
	}
	
	@Test
	public void testIdadeDtNascimentoNulo() throws ParseException {
		Pessoa p = new Pessoa();
		try {
			p.getIdade();
			Assert.assertTrue(false); //Deveria falhar
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);
		}
	}
	

}
