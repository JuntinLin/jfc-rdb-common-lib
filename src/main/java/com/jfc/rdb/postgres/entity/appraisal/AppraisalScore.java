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
@Table(name = "appraisal_score")
public class AppraisalScore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "score_id")
    private UUID scoreId;

    @Column(name = "form_id", nullable = false)
    private UUID formId;

    @Column(name = "item_code", nullable = false, length = 30)
    private String itemCode;

    @Column(name = "item_name", nullable = false, length = 50)
    private String itemName;

    @Column(name = "max_score")
    private BigDecimal maxScore;

    @Column(name = "score")
    private BigDecimal score;

    @Column(name = "grade", length = 1)
    private String grade;

    @Column(name = "evidence", columnDefinition = "TEXT")
    private String evidence;
}
