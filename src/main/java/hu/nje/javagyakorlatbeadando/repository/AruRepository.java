package hu.nje.javagyakorlatbeadando.repository;

import hu.nje.javagyakorlatbeadando.entity.Aru;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AruRepository extends JpaRepository<Aru, Long> {
    List<Aru> findAllByOrderByAruKodAsc();

    /**
     * Lekéri az első 10 árut ár szerint növekvő sorrendben (legolcsóbb elöl).
     */
    List<Aru> findTop10ByOrderByArAsc();

    @Query("SELECT a FROM Aru a " +
        "LEFT JOIN FETCH a.eladas e " +
        "LEFT JOIN FETCH a.kategoria k " +
        "ORDER BY e.mennyiseg DESC NULLS LAST")
    List<Aru> findTopByQuantity(Pageable pageable);
}
