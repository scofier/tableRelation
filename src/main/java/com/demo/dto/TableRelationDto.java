package com.demo.dto;

import lombok.Data;

@Data
public class TableRelationDto {

    String id;
    String db;
    String from;
    String text;
    String to;
    String toText;

}
