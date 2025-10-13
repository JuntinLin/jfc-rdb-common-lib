package com.jfc.rdb.tiptop.model.enums;

public enum ComplaintProcessType {
	COMPLAINT_REASON("0", "客訴原因"), INVESTIGATION_RESULT("1", "調查結果"), IMPROVEMENT_PLAN("2", "處理對策及改善對策"),
	REVIEW("3", "審核"), APPROVAL("4", "核決"), CASE_CLOSED("5", "結案註記");

	private final String code;
	private final String description;

	ComplaintProcessType(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static ComplaintProcessType fromCode(String code) {
		for (ComplaintProcessType type : values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid ComplaintProcessType code: " + code);
	}
}
