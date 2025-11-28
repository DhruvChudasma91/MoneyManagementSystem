package in.helldeveloper.moneymanager.repository;

import in.helldeveloper.moneymanager.entity.ExpenseEntity;
import in.helldeveloper.moneymanager.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    //SELECT * FROM tbl_expenses WHERE profile_id = ? ORDER BY date DESC
    List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);

    //SELECT * FROM tbl_expenses WHERE profile_id = ? ORDER BY date DESC LIMIT 5
    List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);
    @Query("SELECT SUM(e.amount) FROM ExpenseEntity e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

    //SELECT * FROM tbl_expenses WHERE profile_id = ? AND date BETWEEN ? AND ? AND name LIKE %?%
    List<ExpenseEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

    //SELECT * FROM tbl_expenses WHERE profile_id = ? AND date BETWEEN ? AND ?
    List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);
}
