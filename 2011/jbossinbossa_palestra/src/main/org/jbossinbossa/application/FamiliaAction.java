package org.jbossinbossa.application;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.WorkingMemory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.type.FactType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jbossinbossa.dao.FamiliaDAO;
import org.jbossinbossa.dominio.entidade.Familia;
import org.jbossinbossa.dominio.entidade.Pessoa;


@Name("familiaAction")
@Scope(ScopeType.CONVERSATION)
public class FamiliaAction {
	
	//@In
	//private WorkingMemory bolsaFamilia;
	
	private static final String URL_CONNECTION="http://localhost:8080/guvnor-5.2.0.Final-jboss-as-5.1/org.drools.guvnor.Guvnor/package/bolsaFamilia/TEST";
	
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
		// load up the knowledge base
        KnowledgeBase kbase = readKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
        FactType beneficio = kbase.getFactType("bolsaFamilia", "Beneficio");
        Object instanciaBeneficio = beneficio.newInstance();
            
        ksession.setGlobal("$beneficio", instanciaBeneficio);
        
        ksession.insert(familia);
        ksession.fireAllRules();
        setBeneficio((Double)beneficio.get(instanciaBeneficio, "valorBeneficio"));
	}
	
	 private static KnowledgeBase readKnowledgeBase() throws Exception {
	        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	        kbuilder.add(ResourceFactory.newUrlResource(URL_CONNECTION), ResourceType.PKG);
	        KnowledgeBuilderErrors errors = kbuilder.getErrors();
	        if (errors.size() > 0) {
	            for (KnowledgeBuilderError error: errors) {
	                System.err.println(error);
	            }
	            throw new IllegalArgumentException("Could not parse knowledge.");
	        }
	        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	        return kbase;
	    }


}
