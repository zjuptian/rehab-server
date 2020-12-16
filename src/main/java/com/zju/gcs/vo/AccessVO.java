package com.zju.gcs.vo;

import lombok.Data;

@Data
public class AccessVO {
    private Integer doctorId;
    private String doctorName;
    private String department;
    private int[] accessArray;

}
