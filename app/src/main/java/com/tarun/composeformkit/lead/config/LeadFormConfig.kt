package com.tarun.composeformkit.lead.config

object LeadFormConfig {

    val fields = listOf(
        FieldConfig(
            name = "fullName",
            type = FieldType.TEXT,
            label = "Full name",
            rules = listOf(FieldRule.Required)
        ),
        FieldConfig(
            name = "email",
            type = FieldType.EMAIL,
            label = "Email",
            rules = listOf(
                FieldRule.Required,
                FieldRule.ValidEmail
            )
        ),
        FieldConfig(
            name = "leadType",
            type = FieldType.SELECT,
            label = "Lead type",
            options = listOf("Individual", "Company"),
            rules = listOf(FieldRule.Required)
        ),
        FieldConfig(
            name = "companyName",
            type = FieldType.TEXT,
            label = "Company name",
            rules = listOf(FieldRule.Required),
            visibleWhen = VisibilityCondition(
                fieldName = "leadType",
                equalsValue = "Company"
            )
        ),
        FieldConfig(
            name = "phone",
            type = FieldType.TEXT,
            label = "Phone",
            hint = "Enter your 10-digit phone number",
            rules = listOf(
                FieldRule.Required,
                FieldRule.ExactDigits(10)
            )
        ),
        FieldConfig(
            name = "notes",
            type = FieldType.TEXTAREA,
            label = "Notes",
            hint = "Maximum 200 characters",
            rules = listOf(FieldRule.MaxLength(200)),
            span = FieldSpan.FULL
        ),
        FieldConfig(
            name = "consent",
            type = FieldType.CHECKBOX,
            label = "I agree to be contacted",
            rules = listOf(FieldRule.Required),
            span = FieldSpan.FULL
        )
    )
}