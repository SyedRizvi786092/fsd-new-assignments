
package com.example.borrow.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class BorrowRequestDto {
    private Long id;

    @NotBlank(message = "borrowerName is required")
    private String borrowerName;

    @NotNull(message = "itemId is required")
    private Long itemId;

    private LocalDate startDate;
    private LocalDate endDate;

    @NotBlank(message = "status is required")
    private String status;

    public BorrowRequestDto() {}

    public BorrowRequestDto(Long id, String borrowerName, Long itemId, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.borrowerName = borrowerName;
        this.itemId = itemId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
