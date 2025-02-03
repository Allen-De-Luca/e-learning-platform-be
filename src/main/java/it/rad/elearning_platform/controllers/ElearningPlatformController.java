package it.rad.elearning_platform.controllers;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class ElearningPlatformController {

    @GetMapping("/{id}")
    public Long returnId(@PathVariable Long id){
        System.out.println("TRYING TRYING TRYING");
        return id*2;
    }
}
