package com.example.universityTest.demo.service;

import com.example.universityTest.demo.exception.NotFoundEntityException;
import com.example.universityTest.demo.model.Department;
import com.example.universityTest.demo.model.Lector;
import com.example.universityTest.demo.model.enums.LectorDegree;
import com.example.universityTest.demo.repository.DepartmentRepository;
import com.example.universityTest.demo.repository.LectorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class UniversityServiceTest {
    @Autowired
    UniversityService universityService;

    @BeforeAll
    static void beforeAll(@Autowired LectorRepository lectorRepository,
                          @Autowired DepartmentRepository departmentRepository){


        Lector lector1 = new Lector();
        lector1.setDegree(LectorDegree.DOCTORAL);
        lector1.setFirstName("Eugene");
        lector1.setLastName("Giovanni");
        lector1.setSalary(new BigDecimal("30000"));

        Lector lector2 = new Lector();
        lector2.setDegree(LectorDegree.PROFESSOR);
        lector2.setFirstName("Robert");
        lector2.setLastName("Giovan");
        lector2.setSalary(new BigDecimal("20000"));

        Lector lector3 = new Lector();
        lector3.setDegree(LectorDegree.ASSISTANT);
        lector3.setFirstName("Grim");
        lector3.setLastName("Stroke");
        lector3.setSalary(new BigDecimal("10000"));

        Lector lector4 = new Lector();
        lector4.setDegree(LectorDegree.ASSISTANT);
        lector4.setFirstName("Spirit");
        lector4.setLastName("Breaker");
        lector4.setSalary(new BigDecimal("7500"));

        lectorRepository.saveAll(List.of(lector1,lector2,lector3,lector4));

        Department department1 = new Department();
        department1.setName("1 department");
        department1.setDepartmentHead(lector1);
        department1.setLectors(Set.of(lector2, lector4));

        Department department2 = new Department();
        department2.setName("2 department");
        department2.setDepartmentHead(lector2);
        department2.setLectors(Set.of(lector1, lector3));

        Department department3 = new Department();
        department3.setName("3 department");
        department3.setDepartmentHead(lector3);
        department3.setLectors(Set.of(lector2, lector4));

        Department department4 = new Department();
        department4.setName("4 department");
        department4.setDepartmentHead(lector4);
        department4.setLectors(Set.of(lector1, lector2));

        departmentRepository.saveAll(List.of(department1, department2));
    }

    @Test
    void getStatistic(){
        String doctoral = "Doctoral - 1";
        String professor = "Professor - 1";
        String actual = universityService.getStatistic("1 department");
        System.out.println(actual);

        Assertions.assertTrue(actual.contains(doctoral));
        Assertions.assertTrue(actual.contains(professor));
    }
    @Test
    void who_is_head(){
        String expected = "Spirit Breaker";
        String actual = universityService.getDepartmentHead("2 department");
    }

    @Test
    void invalidDepName(){
        Assertions.assertThrows(NotFoundEntityException.class,
                () -> universityService.getDepartmentHead("1"));
        Assertions.assertThrows(NotFoundEntityException.class,
                () -> universityService.getStatistic("2"));
        Assertions.assertThrows(NotFoundEntityException.class,
                () -> universityService.getAverageSalary("3"));
    }
}
