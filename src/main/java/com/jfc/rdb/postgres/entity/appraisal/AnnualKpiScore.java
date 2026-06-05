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
@Table(name = "annual_kpi_score")
public class AnnualKpiScore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "form_id", nullable = false)
    private UUID formId;

    @Column(name = "kpi_id", nullable = false)
    private UUID kpiId;

    @Column(name = "kpi_scope", length = 20)
    private String kpiScope;

    @Column(name = "kpi_name", length = 100)
    private String kpiName;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "target_value")
    private BigDecimal targetValue;

    @Column(name = "last_year_value")
    private BigDecimal lastYearValue;

    @Column(name = "actual_value")
    private BigDecimal actualValue;

    @Column(name = "score")
    private BigDecimal score;
}
