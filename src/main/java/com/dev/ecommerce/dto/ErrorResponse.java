package com.dev.ecommerce.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {

   private Instant timestamp;
   private Integer status;
   private String error;
   private String path;
}
