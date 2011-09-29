package org.jbossinbossa.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jbossinbossa.dominio.entidade.Familia;

@Name("familiaDao")
@AutoCreate
public class FamiliaDAO {
	
	@In
	private EntityManager entitymanager;
	
	
	public List<Familia> findByAll() {
		return entitymanager.createQuery(" from Familia").getResultList();
	}
	
	public Familia findById(Long id) {
		return entitymanager.find(Familia.class, id);
	}

}
