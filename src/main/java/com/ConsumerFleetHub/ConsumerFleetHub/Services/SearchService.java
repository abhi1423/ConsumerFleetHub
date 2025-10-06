package com.ConsumerFleetHub.ConsumerFleetHub.Services;


import com.ConsumerFleetHub.ConsumerFleetHub.Entities.SearchOperation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {
	
    @PersistenceContext
    private EntityManager entityManager;

    public List<SearchOperation> searchByKey(String searchKey) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SearchOperation> query = cb.createQuery(SearchOperation.class);
        Root<SearchOperation> root = query.from(SearchOperation.class);

        List<Predicate> predicates = new ArrayList<>();

        for (Field field : SearchOperation.class.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            try {
                // Only include fields that are supported types
                if (String.class.equals(fieldType)) {
                    predicates.add(cb.or(
                            cb.equal(root.get(fieldName), searchKey),
                            cb.like(root.get(fieldName), searchKey + "%")
                    ));
                } else if (Number.class.isAssignableFrom(fieldType) || fieldType.isPrimitive()) {
                    Expression<String> expr = root.get(fieldName).as(String.class);
                    predicates.add(cb.or(
                            cb.equal(expr, searchKey),
                            cb.like(expr, searchKey + "%")
                    ));
                }
            } catch (IllegalArgumentException e) {
                // Skip invalid fields
            }
        }

        if (predicates.isEmpty()) {
            return Collections.emptyList();
        }

        query.select(root).where(cb.or(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

}
