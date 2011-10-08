package org.jbossinbossa.application;

import java.util.List;

import org.drools.definition.type.FactType;
import org.drools.reteoo.ReteooStatefulSession;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jbossinbossa.dao.FamiliaDAO;
import org.jbossinbossa.dominio.entidade.Familia;


@Name("familiaAction")
@Scope(ScopeType.CONVERSATION)
public class FamiliaAction {
	
	@In
	private ReteooStatefulSession bolsaFamilia;
	
	@In(create=true)
	@Out(required=false)
	private Familia familia;
	
	@In
	private FamiliaDAO familiaDao;
	
	private double beneficio;
	
	
	
	
	public double getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(double beneficio) {
		this.beneficio = beneficio;
	}

	public List<Familia> findByAll() {
		return familiaDao.findByAll();
	}
	
	public String detail(Long id) {
		this.familia = familiaDao.findById(id);
		return "detailFamiliaList";
	}
	
	public void calculaDesconto(Long id) throws Exception {
		this.familia = familiaDao.findById(id);
		System.out.println(bolsaFamilia);
		// load up the knowledge base
		
        FactType beneficio = bolsaFamilia.getKnowledgeRuntime().getKnowledgeBase().getFactType("bolsaFamilia", "Beneficio");
        Object instanciaBeneficio = beneficio.newInstance();
            
        bolsaFamilia.setGlobal("$beneficio", instanciaBeneficio);
        
        bolsaFamilia.insert(familia);
        bolsaFamilia.fireAllRules();
        setBeneficio((Double)beneficio.get(instanciaBeneficio, "valorBeneficio"));
	}
	
}
