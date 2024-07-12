package com.study.worker.controller;

import com.study.worker.model.Worker;
import com.study.worker.service.WorkerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerService workerService;

    @GetMapping
    public ResponseEntity<Page<Worker>> findAll(Pageable pageable) {
        return ResponseEntity.ok(workerService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.findById(id));
    }
}
