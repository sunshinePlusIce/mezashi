package com.arato.Mezashi.Mezashi;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MezashiRepository extends JpaRepository<Mezashi, Long> {
    void deleteMezashiById(long id);
    Optional<Mezashi> findByIdAndUser_Id(long id, long userId);
//    @Query("select mezashi from mezashi m where m.user_id=")
    List<Mezashi> findByUser_Id(long userId);
//    Optional<Mezashi> findMezashiByUserIdAndId(long userId, long id);
}
