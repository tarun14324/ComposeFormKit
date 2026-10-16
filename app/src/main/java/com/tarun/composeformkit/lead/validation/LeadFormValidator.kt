package com.tarun.composeformkit.lead.validation

import com.tarun.composeformkit.lead.config.FieldConfig
import com.tarun.composeformkit.lead.config.FieldRule
import com.tarun.composeformkit.lead.config.FieldType

object LeadFormValidator {

    fun validate(
        config: List<FieldConfig>,
        values: Map<String, String>
    ): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        val visibleFields = config.filter { field ->
            val condition = field.visibleWhen
            condition == null ||
                    values[condition.fieldName] == condition.equalsValue
        }


        visibleFields.forEach { field ->
            val value = values[field.name].orEmpty()
            val valid = Regex(
                """^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)+$"""
            ).matches(value.trim())

            for (rule in field.rules) {
                val error = when (rule) {
                    FieldRule.Required -> {
                        val isCheckedField =
                            field.type ==
                                    FieldType.CHECKBOX

                        if (
                            (isCheckedField && value != "true") ||
                            (!isCheckedField && value.isBlank())
                        ) {
                            if (isCheckedField) {
                                "Consent is required"
                            } else {
                                "${field.label} is required"
                            }
                        } else {
                            null
                        }
                    }

                    FieldRule.ValidEmail -> {

                        if (
                            value.isNotBlank() &&
                            !valid
                        ) {
                            "Enter a valid email address"
                        } else {
                            null
                        }
                    }

                    is FieldRule.ExactDigits -> {
                        if (
                            value.isNotBlank() &&
                            !Regex("""\d{${rule.count}}""")
                                .matches(value)
                        ) {
                            "Enter exactly ${rule.count} digits"
                        } else {
                            null
                        }
                    }

                    is FieldRule.MaxLength -> {
                        if (value.length > rule.length) {
                            "Maximum ${rule.length} characters allowed"
                        } else {
                            null
                        }
                    }
                }

                if (error != null) {
                    errors[field.name] = error
                    break
                }
            }
        }

        return errors
    }
}