package com.team5.nbe341team05.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseMessage<T>(String message, String resultCode, T data) {
}