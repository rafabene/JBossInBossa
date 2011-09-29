package org.jbossinbossa.application;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbossinbossa.dao.FamiliaDAO;
import org.jbossinbossa.dominio.entidade.Familia;


@Name("familiaAction")
@Scope(ScopeType.CONVERSATION)
public class FamiliaAction {
	
	@In
	private Familia familia;
	
	@In
	private FamiliaDAO familiaDao;
	
	
	public List<Familia> findByAll() {
		return familiaDao.findByAll();
	}
	
	public void detail(Long id) {
		this.familia = familiaDao.findById(id);
	}

}
