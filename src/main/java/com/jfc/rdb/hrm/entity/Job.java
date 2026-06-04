package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Job")
public class Job {
    @Id
    private UUID jobId;

    @Column(name = "Name")
    private String name;           // 職位名稱（課長、經理、組長...）

    @Column(name = "Code")
    private String code;           // 職位編碼

    @Column(name = "PositionId")
    private UUID positionId;       // 所屬職務

    @Column(name = "IsVice")
    private Boolean isVice;        // 是否副職

    @Column(name = "Flag")
    private Boolean flag;
}
