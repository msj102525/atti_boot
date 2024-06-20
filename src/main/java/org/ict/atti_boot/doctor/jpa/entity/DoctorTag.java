package org.ict.atti_boot.doctor.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DOCTOR_TAG")
public class DoctorTag {

    @Id
    @Column(name = "ID", columnDefinition = "RAW(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "TAG", length = 30, nullable = false)
    private String tag;

    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;



    public DoctorTag(String tag, String userId) {
        this.tag = tag;
        this.userId = userId;
        this.id = UUID.randomUUID();
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorTag that = (DoctorTag) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
