package com.example.testspring.Spec;

import com.example.testspring.BO.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomSpecification implements Specification<Object> {


    private List<SearchCriteria> criterias;
    public CustomSpecification(List<SearchCriteria> criterias) {
        this.criterias = criterias;
    }

    @Override
    public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return getPredicate(root,query,criteriaBuilder,criterias);
    }

    public Predicate getPredicate (Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, List<SearchCriteria> criterias) {
        List<Predicate> andPredicates = new ArrayList<Predicate>();
        if(criterias!=null) {
            for(SearchCriteria criteria :criterias) {
                Predicate predicate =null ;
                if (criteria.getCondition().equalsIgnoreCase(">")) {
                    predicate = builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
                }else if (criteria.getCondition().equalsIgnoreCase("<")) {
                    predicate = builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
                }else if (criteria.getCondition().equalsIgnoreCase("=")) {
                    predicate = builder.equal(root.<String> get(criteria.getKey()), criteria.getValue().toString());
                }else if (criteria.getCondition().equalsIgnoreCase("like")) {
                    predicate = builder.like(builder.lower(root.<String>get(criteria.getKey())), ("%" + criteria.getValue() + "%").toLowerCase());
                }
                if(predicate !=null) {
                    andPredicates.add(predicate);
                }
            }
        }
        Predicate and = builder.and(andPredicates.toArray(new Predicate[0]));
        Predicate finalQuery= builder.and(and);
        return finalQuery;
    }
}
