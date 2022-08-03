package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentRequestDto implements Serializable {
    private final String content;
}
