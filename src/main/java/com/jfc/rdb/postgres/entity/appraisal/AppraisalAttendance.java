package com.jfc.rdb.postgres.entity.appraisal;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appraisal_attendance")
public class AppraisalAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "form_id", nullable = false)
    private UUID formId;

    @Column(name = "leave_type", nullable = false, length = 50)
    private String leaveType;

    @Column(name = "leave_name", length = 50)
    private String leaveName;

    @Column(name = "hours")
    private BigDecimal hours;
}
