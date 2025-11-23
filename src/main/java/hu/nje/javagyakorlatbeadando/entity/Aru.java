package hu.nje.javagyakorlatbeadando.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ARU", indexes = {
    @Index(name = "idx_aru_kat_kod", columnList = "kat_kod"),
    @Index(name = "idx_aru_ar", columnList = "ar"),
    @Index(name = "idx_aru_nev", columnList = "nev")
})
public class Aru {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aru_kod")
    private Long aruKod;

    @ManyToOne
    @JoinColumn(
        name = "kat_kod",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_ARU_KATEGORIA",
            foreignKeyDefinition = "FOREIGN KEY (kat_kod) REFERENCES KATEGORIA(kat_kod) ON UPDATE CASCADE ON DELETE RESTRICT"
        )
    )
    private Kategoria kategoria;

    @OneToOne(mappedBy = "aru")
    private Eladas eladas;

    @Column(nullable = false)
    private String nev;

    @Column(nullable = false, columnDefinition = "ENUM('darab', 'doboz', 'kg', 'liter')")
    private String egyseg;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) UNSIGNED")
    private BigDecimal ar;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    public Long getAruKod() { return aruKod; }
    public void setAruKod(Long aruKod) { this.aruKod = aruKod; }

    public Kategoria getKategoria() { return kategoria; }
    public void setKategoria(Kategoria kategoria) { this.kategoria = kategoria; }

    public Eladas getEladas() { return eladas; }
    public void setEladas(Eladas eladas) { this.eladas = eladas; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getEgyseg() { return egyseg; }
    public void setEgyseg(String egyseg) { this.egyseg = egyseg; }

    public BigDecimal getAr() { return ar; }
    public void setAr(BigDecimal ar) { this.ar = ar; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public BigDecimal getBevetel() {
        if (this.ar == null || this.eladas == null || this.eladas.getMennyiseg() == null) {
            return BigDecimal.ZERO;
        }
        return this.ar.multiply(this.eladas.getMennyiseg());
    }

}
