package org.jbossinbossa.dominio.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.jboss.seam.annotations.Name;

@Entity
@SequenceGenerator(name="familia_seq", sequenceName="familia_sequence")
@Name("familia")
public class Familia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="familia_seq")
	private Long id;
	
	
	@OneToMany(mappedBy = "familia", cascade=CascadeType.PERSIST)
	private List<Pessoa> membros = new ArrayList<Pessoa>();
	
	
	@Transient
	private String beneficio;
	
	

	public Long getId() {
		return id;
	}

	public List<Pessoa> getMembros() {
		return membros;
	}
	
	public void setMembros(List<Pessoa> membros) {
		this.membros = membros;
	}
	
	public void adicionarMembro(Pessoa p){
		this.membros.add(p);
	}

	public double getRendaPercapta() {
		double rendaTotal = 0;
		for (Pessoa p : membros) {
			rendaTotal += p.getRenda();
		}
		return rendaTotal ==0? 0 : rendaTotal / membros.size();
	}
	
	public String getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	@Override
	public String toString() {
		return "Família com " + getMembros().size() +  " membros e com renda percapta de " + getRendaPercapta();
	}

}
