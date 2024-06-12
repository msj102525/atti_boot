package org.ict.atti_boot.doctor.jpa.specification;

import jakarta.persistence.criteria.*;
import org.ict.atti_boot.doctor.jpa.entity.Education;
import org.springframework.data.jpa.domain.Specification;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;

import java.util.List;

public class DoctorSpecifications {

    public static Specification<Doctor> userNameContaining(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.join("user").get("userName"), "%" + name + "%");
    }

    public static Specification<Doctor> tagsIn(List<String> tags) {
    return (root, query, criteriaBuilder) -> {
        if (tags == null || tags.isEmpty()) {
            return criteriaBuilder.conjunction();
        }

        Predicate[] predicates = new Predicate[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Doctor> subRoot = subquery.from(Doctor.class);
            Join<Doctor, DoctorTag> subTagJoin = subRoot.join("tags");
            subquery.select(subRoot.get("id"))
                    .where(criteriaBuilder.equal(subTagJoin.get("tag"), tags.get(i)),
                           criteriaBuilder.equal(subRoot.get("id"), root.get("id")));
            predicates[i] = criteriaBuilder.exists(subquery);
        }

        return criteriaBuilder.and(predicates);
    };
}




    public static Specification<Doctor> genderContaining(Character gender) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("user").get("gender"), gender);
    }




}
