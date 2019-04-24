package com.devaware.userservice.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserCustomRepository {

    private EntityManager em;

    public UserRepositoryImpl(EntityManager entityManager) {
        em = entityManager;
    }

    @Override
    public List<User> search(UserFilter filter) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        List<Predicate> predicates = new LinkedList<>();
        if (filter.getName() != null) {
            predicates.add(builder.like(builder.upper(root.get(User_.NAME)), "%" + filter.getName().toUpperCase() +
                    "%"));
        }
        if (filter.getUsername() != null) {
            predicates.add(builder.like(builder.upper(root.get(User_.USERNAME)), "%" + filter.getUsername().toUpperCase() +
                    "%"));
        }
        criteria.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return em.createQuery(criteria).getResultList();
    }
}
