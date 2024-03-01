package dev.archimedes.utils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicRecord {

    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss", fallbackPatterns = {"yyyy-MM-dd HH:mm:ss"})
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "created_by", updatable = true)
    private Integer createdBy;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss", fallbackPatterns = {"yyyy-MM-dd HH:mm:ss"})
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;
}
