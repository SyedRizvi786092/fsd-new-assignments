
package com.example.borrow.repository;

import com.example.borrow.entity.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Long> {
}
