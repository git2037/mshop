package com.mshop.app.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserJob {
    private String name;
    private Long lastProcessedTime;
}