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
@Table(name = "CAREER")
public class Career {

    @Id
    @Column(name = "ID", columnDefinition = "RAW(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "CAREER", length = 100, nullable = false)
    private String career;

    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;


    public Career(String career, String userId) {
        this.id = UUID.randomUUID();
        this.career = career;
        this.userId = userId;
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Career career1 = (Career) o;
        return Objects.equals(id, career1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
