package com.unifiedbookcatalog.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.jpa.QueryHints;

import com.unifiedbookcatalog.models.Livro;

public class LivroDAO {
	
	@PersistenceContext
	private EntityManager manager;

	public void salvar(Livro livro) {
		//arrumar
		if(livro.getId() == null)
			manager.persist(livro);
		else
			manager.merge(livro);
	}

	public void deletar(Livro livro) {
		livro = manager.find(Livro.class, livro.getId());
		manager.remove(livro);
	}
	
	public List<Livro> listar() {
		String jpql = "select distinct(l) from Livro l left join fetch l.autores";
		return manager.createQuery(jpql, Livro.class).getResultList();
	}

	public List<Livro> ultimosLancamentos() {
	    String jpql = "select l from Livro l order by l.id desc";
	    return manager.createQuery(jpql, Livro.class).setMaxResults(5).setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
	}
	
	 public List<Livro> demaisLivros() {
        String jpql = "select l from Livro l order by l.id desc";
        return manager.createQuery(jpql, Livro.class).setFirstResult(5).setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
	 }

	public Livro buscarPorId(Integer id) {
	    String jpql = "select l from Livro l left join fetch l.autores where l.id = :id";
	    return manager.createQuery(jpql, Livro.class).setParameter("id", id).getSingleResult();
	}

	public Livro buscarPorCodigo(String codigo) {
		Livro livro = null;
	    String jpql = "select l from Livro l where l.codigo = :codigo";
	    
	    try {
	    	livro = manager.createQuery(jpql, Livro.class).setParameter("codigo", codigo).getSingleResult();
		} catch (Exception e) {
		}
	    
	    return livro;
	}
}