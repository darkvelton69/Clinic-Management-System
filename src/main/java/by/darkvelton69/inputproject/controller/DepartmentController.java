package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.DepartmentRequest;
import by.darkvelton69.inputproject.dto.DepartmentResponse;
import by.darkvelton69.inputproject.entity.Department;
import by.darkvelton69.inputproject.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/polyclinic34/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/created")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createdDep(@RequestBody DepartmentRequest request){
        return departmentService.createdDepartment(request);
    }
}
