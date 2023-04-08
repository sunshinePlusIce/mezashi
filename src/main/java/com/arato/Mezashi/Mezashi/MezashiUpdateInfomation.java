package com.arato.Mezashi.Mezashi;

import com.arato.Mezashi.constant.Constant;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

// this structure is used to update basic information of an existing mezashi.

public record MezashiUpdateInfomation(
        CompleteCondition completeCondition,
        @Size(max=Constant.MEZASHI_DESCRIPTION_MAX_LENGTH) String description,
        @Size(
                min=Constant.MEZASHI_NAME_MIN_LENGTH,
                max=Constant.MEZASHI_NAME_MAX_LENGTH
        ) String name,
        @Future LocalDate targetDate,
        Set<Tag> tags,
        Long parentId
) {}
