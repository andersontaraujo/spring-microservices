package com.devaware.userservice.role;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleCustomRepository {
	
	private EntityManager em;
	
	public RoleRepositoryImpl(EntityManager entityManager) {
		em = entityManager;
	}

	@Override
	public List<Role> search(RoleFilter filter) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
		Root<Role> root = criteria.from(Role.class);
		List<Predicate> predicates = new LinkedList<>();
		if (filter.getName() != null) {
            predicates.add(builder.like(builder.upper(root.get(Role_.NAME)), "%" + filter.getName().toUpperCase() + "%"));
        }
        if (filter.getEnabled() != null) {
            predicates.add(builder.equal(builder.upper(root.get(Role_.IS_ENABLED)), filter.getEnabled()));
        }
        criteria.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return em.createQuery(criteria).getResultList();
	}

}
