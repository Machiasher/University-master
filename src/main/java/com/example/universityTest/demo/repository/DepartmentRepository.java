package com.example.universityTest.demo.repository;

import com.example.universityTest.demo.model.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @EntityGraph(attributePaths = {"department", "lectors"})
    Optional<Department> getDepartmentByNameIgnoreCase(String name);

    @Query
            ("SELECT COUNT(d) FROM Department d JOIN d.lectors WHERE LOWER(d.name) = LOWER(:name)")
    Integer countAllByName(String name);

    @Query
            ("SELECT AVG(l.salary) FROM Department d JOIN d.lectors l WHERE LOWER(d.name) = LOWER(:name)")
    Optional<BigDecimal> getAverageSalaryByName(String name);

    @Query
            ("SELECT CONCAT(l.firstName, ' ', l.lastName) FROM Lector l " +
            "WHERE LOWER(CONCAT(l.firstName, ' ', l.lastName)) LIKE LOWER(CONCAT('%', :template, '%')) " +
            "UNION " +
            "SELECT d.name FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :template, '%'))")
    List<String> searchEverywhere(@Param("template") String template);
}
