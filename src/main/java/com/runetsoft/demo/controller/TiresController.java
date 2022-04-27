package com.runetsoft.demo.controller;

import com.runetsoft.demo.error.ServiceException;
import com.runetsoft.demo.model.TireModel;
import com.runetsoft.demo.service.TireService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("tires")
public class TiresController {

    private final TireService tireService;

    TiresController(TireService tireService) {
        this.tireService = tireService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/save",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveTires(MultipartFile file) throws ServiceException {
        tireService.saveTiresFromFile(file);
    }

    @GetMapping
    public List<TireModel> getAll() {
        return tireService.getAll();
    }

}
