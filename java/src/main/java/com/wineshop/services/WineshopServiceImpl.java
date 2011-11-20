package com.wineshop.services;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wineshop.entities.Vineyard;


@Service
public class WineshopServiceImpl implements WineshopService {

    @PersistenceContext
    private EntityManager entityManager;
    

    @Transactional
    public void save(Vineyard vineyard) {
        entityManager.merge(vineyard);
        entityManager.flush();
    }
    
    @Transactional
    public void remove(Long vineyardId) {
    	Vineyard vineyard = entityManager.find(Vineyard.class, vineyardId);
    	entityManager.remove(vineyard);
        entityManager.flush();
    }
    
    @Transactional(readOnly=true)
    public Map<String, Object> list(Vineyard filter, int first, int max, String[] sort, boolean[] asc) {
    	StringBuilder sb = new StringBuilder("from Vineyard vy ");
    	if (filter.getName() != null)
    		sb.append("where vy.name like '%' || :name || '%'");
    	if (sort != null && sort.length > 0) { 
    		sb.append("order by ");
    		for (int i = 0; i < sort.length; i++)
    			sb.append(sort[i]).append(" ").append(asc[i] ? " asc" : " desc");
	}
    	
    	Query qcount = entityManager.createQuery("select count(vy) " + sb.toString());
    	Query qlist = entityManager.createQuery("select vy " + sb.toString()).setFirstResult(first).setMaxResults(max);
    	if (filter.getName() != null) {
    		qcount.setParameter("name", filter.getName());
    		qlist.setParameter("name", filter.getName());
    	}
    	
    	Map<String, Object> result = new HashMap<String, Object>(4);
    	result.put("resultCount", (Long)qcount.getSingleResult());
    	result.put("resultList", qlist.getResultList());
    	result.put("firstResult", first);
    	result.put("maxResults", max);
    	return result;
    }
}
