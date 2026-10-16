package com.tarun.composeformkit.lead.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.tarun.composeformkit.design_system.atoms.AppCheckBoxField
import com.tarun.composeformkit.design_system.atoms.AppSelectField
import com.tarun.composeformkit.design_system.atoms.AppTextField
import com.tarun.composeformkit.design_system.components.AppField
import com.tarun.composeformkit.lead.config.FieldConfig
import com.tarun.composeformkit.lead.config.FieldRule
import com.tarun.composeformkit.lead.config.FieldType

@Composable
fun LeadFieldRenderer(
    config: FieldConfig,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    onFocusLost: () -> Unit
) {
    val required = config.rules.contains(FieldRule.Required)

    AppField(
        label = if (config.type == FieldType.CHECKBOX) "" else config.label,
        hint = config.hint,
        error = error,
        required = required
    ) {
        when (config.type) {
            FieldType.TEXT,
            FieldType.EMAIL,
            FieldType.TEXTAREA -> {
                AppTextField(
                    value = value,
                    label = config.label,
                    onValueChange = onValueChange,
                    onFocusLost = onFocusLost,
                    keyboardType = when (config.type) {
                        FieldType.EMAIL -> KeyboardType.Email
                        else -> KeyboardType.Text
                    },
                    multiline = config.type == FieldType.TEXTAREA,
                    maxLength = if (config.type == FieldType.TEXTAREA) 200 else null
                )
            }

            FieldType.SELECT -> {
                AppSelectField(
                    value = value,
                    label = config.label,
                    options = config.options,
                    onValueChange = onValueChange
                )
            }

            FieldType.CHECKBOX -> {
                AppCheckBoxField(
                    value = value == "true",
                    label = config.label,
                    onValueChange = { onValueChange(it.toString()) }
                )
            }
        }
    }
}