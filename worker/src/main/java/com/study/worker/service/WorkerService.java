package com.study.worker.service;

import com.study.worker.model.Worker;
import com.study.worker.repository.WorkerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;

    @Transactional(readOnly = true)
    public Page<Worker> findAll(Pageable pageable) {
        return workerRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Worker findById(Long id) {
        return workerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id not found"));
    }
}
