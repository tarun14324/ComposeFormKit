package com.tarun.composeformkit.lead.config

enum class FieldType {
    TEXT,
    EMAIL,
    SELECT,
    TEXTAREA,
    CHECKBOX
}

enum class FieldSpan {
    HALF,
    FULL
}

sealed interface FieldRule {
    data object Required : FieldRule
    data object ValidEmail : FieldRule
    data class ExactDigits(val count: Int) : FieldRule
    data class MaxLength(val length: Int) : FieldRule
}

data class VisibilityCondition(
    val fieldName: String,
    val equalsValue: String
)

data class FieldConfig(
    val name: String,
    val type: FieldType,
    val label: String,
    val rules: List<FieldRule> = emptyList(),
    val options: List<String> = emptyList(),
    val hint: String? = null,
    val span: FieldSpan = FieldSpan.HALF,
    val visibleWhen: VisibilityCondition? = null
)