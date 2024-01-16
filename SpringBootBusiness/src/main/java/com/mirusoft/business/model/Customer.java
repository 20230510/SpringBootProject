package com.mirusoft.business.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Customer {

    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.AUTO, example = "자동증가")
    private Long id;

    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "jung ji hoon")
    @NotNull(message = "Code cannot be null")
    @Size(min = 3, max = 30, message = "한글이름은 15자이내 이어야 합니다.")
    private String name;

    @Schema(description = "나이", requiredMode = Schema.RequiredMode.REQUIRED, example = "80")
    @NotNull(message = "Code cannot be null")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
