package org.jbossinbossa.aplicacao;

import java.text.SimpleDateFormat;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
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
import org.jbossinbossa.dominio.entidade.Familia;
import org.jbossinbossa.dominio.entidade.Pessoa;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            FactType beneficio = kbase.getFactType("bolsaFamilia", "Beneficio");
            Object instanciaBeneficio = beneficio.newInstance();
            
            // go !
            Pessoa p1 = new Pessoa();
            p1.setDataNascimento(sdf.parse("10/10/1998"));
            p1.setRenda(110);
            Familia familia = new Familia();
            familia.adicionarMembro(p1);
            
            ksession.setGlobal("$beneficio", instanciaBeneficio);
            
            ksession.insert(familia);
            ksession.fireAllRules();
            System.out.println(beneficio.get(instanciaBeneficio, "valorBeneficio"));

            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
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
