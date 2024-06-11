package org.ict.atti_boot.doctor.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

public class DoctorSpecifications {

    public static Specification<Doctor> userNameContaining(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.join("user").get("userName"), "%" + name + "%");
    }

    public static Specification<Doctor> tagsIn(List<String> tags) {
    return (root, query, criteriaBuilder) -> {
        Join<Doctor, DoctorTag> tagJoin = root.join("tags");
        Predicate[] predicates = new Predicate[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            predicates[i] = criteriaBuilder.equal(tagJoin.get("tag"), tags.get(i));
        }
        return criteriaBuilder.and(predicates);
    };
}




    public static Specification<Doctor> genderContaining(Character gender) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("user").get("gender"), gender);
    }
}
