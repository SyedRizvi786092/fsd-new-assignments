
package com.example.borrow.service;

import com.example.borrow.dto.BorrowRequestDto;
import java.util.List;

public interface BorrowRequestService {
    // 5 service methods
    BorrowRequestDto create(BorrowRequestDto dto);
    BorrowRequestDto update(Long id, BorrowRequestDto dto);
    BorrowRequestDto get(Long id);
    List<BorrowRequestDto> getList();
    void delete(Long id);

    // 2 helper conversion methods
    BorrowRequestDto entityToDto(com.example.borrow.entity.BorrowRequest entity);
    com.example.borrow.entity.BorrowRequest dtoToEntity(BorrowRequestDto dto);
}
