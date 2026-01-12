
package com.example.borrow.controller;

import com.example.borrow.dto.BorrowRequestDto;
import com.example.borrow.service.BorrowRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/borrow-requests")
public class BorrowRequestController {

    private final BorrowRequestService service;

    public BorrowRequestController(BorrowRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BorrowRequestDto> create(@Valid @RequestBody BorrowRequestDto dto, UriComponentsBuilder uriBuilder) {
        BorrowRequestDto created = service.create(dto);
        return ResponseEntity.created(
                uriBuilder.path("/borrow-requests/{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowRequestDto> update(@PathVariable Long id, @Valid @RequestBody BorrowRequestDto dto) {
        BorrowRequestDto updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowRequestDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<BorrowRequestDto>> getList() {
        return ResponseEntity.ok(service.getList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
