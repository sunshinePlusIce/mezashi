package com.arato.Mezashi.Mezashi;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MezashiRepository extends JpaRepository<Mezashi, Long> {
    void deleteMezashiById(long id);
}
