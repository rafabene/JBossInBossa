package org.jbossinbossa.dominio.entidade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;


@Entity
@SequenceGenerator(name="pessoa_seq", sequenceName="pessoa_sequence")
@Name("pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="pessoa_seq")
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	private double renda;
	
	@NotNull
	private boolean frequentaEscola;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@ManyToOne
	@NotNull
	private Familia familia;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getRenda() {
		return renda;
	}

	public void setRenda(double renda) {
		this.renda = renda;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public Familia getFamilia() {
		return familia;
	}
	
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public Long getId() {
		return id;
	}
	
	public boolean isFrequentaEscola() {
		return frequentaEscola;
	}
	
	public void setFrequentaEscola(boolean frequentaEscola) {
		this.frequentaEscola = frequentaEscola;
	}

	public int getIdade() {
		Calendar diaNascimento = Calendar.getInstance();
		Calendar hoje = Calendar.getInstance();
		
		if (dataNascimento == null){
			throw new IllegalArgumentException("Data de Nascimento Nula");
		}else{
			diaNascimento.setTime(dataNascimento);
		}

		int age = hoje.get(Calendar.YEAR) - diaNascimento.get(Calendar.YEAR);
		diaNascimento.add(Calendar.YEAR, age);

		if (hoje.before(diaNascimento)) {
			age--;
		}
		return age;
	}
	
	@Override
	public String toString() {
		return "Pessoa " + getNome() +  " nascida em " + getDataNascimento() + " com renda de R$ " + getRenda() ;
	}

}
