
package com.example.borrow.service.impl;

import com.example.borrow.dto.BorrowRequestDto;
import com.example.borrow.entity.BorrowRequest;
import com.example.borrow.exception.NotFoundException;
import com.example.borrow.repository.BorrowRequestRepository;
import com.example.borrow.service.BorrowRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowRequestServiceImpl implements BorrowRequestService {

    private final BorrowRequestRepository repository;

    public BorrowRequestServiceImpl(BorrowRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public BorrowRequestDto create(BorrowRequestDto dto) {
        BorrowRequest entity = dtoToEntity(dto);
        entity.setId(null); // ensure create
        BorrowRequest saved = repository.save(entity);
        return entityToDto(saved);
    }

    @Override
    public BorrowRequestDto update(Long id, BorrowRequestDto dto) {
        BorrowRequest existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("BorrowRequest id=" + id + " not found"));
        // update fields from DTO
        existing.setBorrowerName(dto.getBorrowerName());
        existing.setItemId(dto.getItemId());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setStatus(dto.getStatus());
        BorrowRequest saved = repository.save(existing);
        return entityToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BorrowRequestDto get(Long id) {
        BorrowRequest entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("BorrowRequest id=" + id + " not found"));
        return entityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowRequestDto> getList() {
        return repository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("BorrowRequest id=" + id + " not found");
        }
        repository.deleteById(id);
    }

    // helper methods
    @Override
    public BorrowRequestDto entityToDto(BorrowRequest entity) {
        if (entity == null) return null;
        return new BorrowRequestDto(
                entity.getId(),
                entity.getBorrowerName(),
                entity.getItemId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStatus()
        );
    }

    @Override
    public BorrowRequest dtoToEntity(BorrowRequestDto dto) {
        if (dto == null) return null;
        BorrowRequest entity = new BorrowRequest();
        entity.setId(dto.getId());
        entity.setBorrowerName(dto.getBorrowerName());
        entity.setItemId(dto.getItemId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
