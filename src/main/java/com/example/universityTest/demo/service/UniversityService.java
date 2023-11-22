package com.example.universityTest.demo.service;

import com.example.universityTest.demo.exception.NotFoundEntityException;
import com.example.universityTest.demo.model.Department;
import com.example.universityTest.demo.model.Lector;
import com.example.universityTest.demo.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final DepartmentRepository departmentRepository;

    public String getStatistic(String name) {
        Department department = departmentRepository.getDepartmentByNameIgnoreCase(name).orElseThrow(
                () -> new NotFoundEntityException("Not found department: " + name));
        Map<String, Long> statistic = department.getLectors()
                .stream()
                .map(Lector::getDegree)
                .collect(Collectors.groupingBy(degree ->
                                degree.toString().replace("_", " ").toLowerCase(),
                        Collectors.counting()));

        List<String> result = new ArrayList<>();
        for (var degree : statistic.entrySet()) {
            result.add(degree.getKey() + "s - " + degree.getValue());
        }
        return String.join(System.lineSeparator(), result);
    }

    public String getDepartmentHead(String name) {
        Department department = departmentRepository.getDepartmentByNameIgnoreCase(name).orElseThrow(
                () -> new NotFoundEntityException("Not found department: " + name));
        return department.getDepartmentHead().getFirstName()
                + " " + department.getDepartmentHead().getLastName();
    }

    public BigDecimal getAverageSalary(String name) {
        return departmentRepository.getAverageSalaryByName(name).orElseThrow(
                () -> new NotFoundEntityException("Not found department: " + name));
    }

    public Integer getCountOfEmployee(String name) {
        return departmentRepository.countAllByName(name);
    }

    public String searchEverywhere(String template) {
        return String.join(", ", departmentRepository.searchEverywhere(template));
    }
}
