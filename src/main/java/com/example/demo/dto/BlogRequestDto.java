package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogRequestDto implements Serializable {
    private final String title;
    private final String description;
}
