package hu.nje.javagyakorlatbeadando.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELADAS")
public class Eladas {
    @Id
    @Column(name = "aru_kod")
    private Long aruKod;

    // Shared Primary Key kapcsolat: Az ID megegyezik a hivatkozott Aru ID-jával
    @OneToOne
    @MapsId // Ez jelzi a JPA-nak, hogy az entitás ID-ja megegyezik a hozzá kapcsolt Aru entitás ID-jával
    @JoinColumn(
        name = "aru_kod",
        foreignKey = @ForeignKey(
            name = "FK_ELADAS_ARU",
            foreignKeyDefinition = "FOREIGN KEY (aru_kod) REFERENCES ARU(aru_kod) ON UPDATE CASCADE ON DELETE CASCADE"
        )
    )
    private Aru aru;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) UNSIGNED")
    private BigDecimal mennyiseg;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getAruKod() { return aruKod; }
    public void setAruKod(Long aruKod) { this.aruKod = aruKod; }

    public Aru getAru() { return aru; }
    public void setAru(Aru aru) { this.aru = aru; }

    public BigDecimal getMennyiseg() { return mennyiseg; }
    public void setMennyiseg(BigDecimal mennyiseg) { this.mennyiseg = mennyiseg; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
