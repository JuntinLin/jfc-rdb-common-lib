package com.jfc.rdb.postgres.entity.autobom;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autobom_process_step")
public class QuotationProcessStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;

    @Column(name = "part_id", nullable = false)
    private Long partId;

    @Column(name = "part_name", length = 200)
    private String partName;

    @Column(name = "step_order")
    private Integer stepOrder;

    @Column(name = "machining_type", length = 30)
    private String machiningType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "estimated_hours", precision = 6, scale = 2)
    private BigDecimal estimatedHours;

    @Column(name = "hourly_rate", precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "cost", precision = 14, scale = 2)
    private BigDecimal cost;
}
