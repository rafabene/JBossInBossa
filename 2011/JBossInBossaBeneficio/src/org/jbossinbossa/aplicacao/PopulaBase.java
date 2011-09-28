package org.jbossinbossa.aplicacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbossinbossa.dominio.entidade.Familia;
import org.jbossinbossa.dominio.entidade.Pessoa;

public class PopulaBase {

	private static final int QTD_FAMILIA = 1000000;
	private static final int QTD_MEMBROS = 6;
	private static final double RENDA_MAXIMA = 150;
	private static final int IDADE_MEDIA_FAMILIA = 30;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("JBossInBossaBeneficio");

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		new PopulaBase().executa();

	}

	private void executa() throws ParseException {
		EntityManager em = emf.createEntityManager();
		for (int x = 1; x <= QTD_FAMILIA; x++) {
			Familia familia = new Familia();
			int membrosDestaFamilia = (int) (Math.random() * QTD_MEMBROS);
			for (int y = 1; y <= membrosDestaFamilia; y++) {
				Pessoa p = new Pessoa();
				p.setNome("Pessoa " + y + " da Família " + x);
				int renda = (int) (Math.random() * RENDA_MAXIMA);
				p.setRenda(renda);
				int anoNascimento = (int) (2011 - (Math.random() * IDADE_MEDIA_FAMILIA));
				int diaNascimento =(int) (Math.random() * 28);
				int mesNascimentp =  (int) (Math.random() * 12);
				p.setDataNascimento(sdf.parse( diaNascimento + "/" + mesNascimentp + "/" + anoNascimento));
				familia.getMembros().add(p);
				p.setFamilia(familia);
				System.out.println(p);
			}
			em.getTransaction().begin();
			em.persist(familia);
			em.getTransaction().commit();
		}

	}

}
