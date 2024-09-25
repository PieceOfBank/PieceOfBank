package com.fintech.pob.domain.directory.entity;

import lombok.Data;

@Data
public class DirectoryRequestDto {
    private Long directoryId;
    private String userKey;
    private String accountNo;
    private Integer institutionCode;
    private String name;

}
