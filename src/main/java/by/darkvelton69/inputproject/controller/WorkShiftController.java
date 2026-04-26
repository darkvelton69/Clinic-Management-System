package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.WorkShiftRequest;
import by.darkvelton69.inputproject.dto.WorkShiftResponse;
import by.darkvelton69.inputproject.service.WorkShiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polyclinic34/workShift")
@RequiredArgsConstructor
public class WorkShiftController {

    private final WorkShiftService workShiftService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkShiftResponse create(@Valid @RequestBody WorkShiftRequest workShiftRequest){
        return workShiftService.registerWorkShift(workShiftRequest);
    }
}
