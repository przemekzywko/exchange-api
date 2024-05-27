package com.example.exchangeapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConvertConfirmationData {
    private ConvertResult convertResult;
    private String email;

}
