package FinalProject2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FinalProject2.model.BonusPointUse;

@Repository
public interface BonusPointUseRepository extends JpaRepository<BonusPointUse, String>{

	@Query("SELECT SUM(u.bonus_point_use) FROM BonusPointUse u WHERE employee_id = :username")
	int getSumUsePoint(@Param("username") String username);

	@Query("SELECT MAX(u.bonus_point_use_id) FROM BonusPointUse u")
	int getMaxId();

	@Query("SELECT u FROM BonusPointUse u WHERE employee_id = :employeeId  ORDER BY u.use_date DESC")
	List<BonusPointUse> findByEmployeeId(String employeeId);

	@Query("SELECT u FROM BonusPointUse u WHERE employee_id = :employeeId  ORDER BY u.use_date DESC")
	Page<BonusPointUse> findByEmployeeId(String employeeId, Pageable pageable);

}
