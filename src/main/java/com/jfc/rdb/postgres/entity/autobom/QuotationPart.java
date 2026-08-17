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
@Table(name = "autobom_quotation_part")
public class QuotationPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "part_number", length = 100)
    private String partNumber;

    @Column(name = "part_type", length = 20)
    private String partType;

    @Column(name = "material", length = 100)
    private String material;

    @Column(name = "specification", length = 300)
    private String specification;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "outer_diameter", precision = 10, scale = 3)
    private BigDecimal outerDiameter;

    @Column(name = "inner_diameter", precision = 10, scale = 3)
    private BigDecimal innerDiameter;

    @Column(name = "length", precision = 10, scale = 3)
    private BigDecimal length;

    @Column(name = "special_spec")
    private Boolean specialSpec;

    @Column(name = "material_cost", precision = 14, scale = 2)
    private BigDecimal materialCost;

    @Column(name = "machining_cost", precision = 14, scale = 2)
    private BigDecimal machiningCost;

    @Column(name = "subtotal", precision = 14, scale = 2)
    private BigDecimal subtotal;
}
