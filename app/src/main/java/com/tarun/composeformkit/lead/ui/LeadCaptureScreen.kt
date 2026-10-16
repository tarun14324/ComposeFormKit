package com.tarun.composeformkit.lead.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.tarun.composeformkit.design_system.atoms.AppButtonField
import com.tarun.composeformkit.design_system.tokens.AppSpacing
import com.tarun.composeformkit.lead.config.LeadFormConfig
import com.tarun.composeformkit.lead.validation.LeadFormValidator

@Composable
fun LeadCaptureScreen() {

    val config = LeadFormConfig.fields

    var values by remember {
        mutableStateOf(
            config.associate { field ->
                field.name to ""
            }
        )
    }

    var errors by remember {
        mutableStateOf<Map<String, String>>(emptyMap())
    }

    var submitted by remember {
        mutableStateOf(false)
    }

    var showResult by remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    // Show only fields whose visibility conditions are satisfied.
    val visibleFields = config.filter { field ->
        val condition = field.visibleWhen

        condition == null ||
                values[condition.fieldName] == condition.equalsValue
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = AppSpacing.md)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.md)
    ) {

        Spacer(
            modifier = Modifier.height(AppSpacing.md)
        )

        Text(
            text = "Get in touch",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Tell us a little about yourself.",
            style = MaterialTheme.typography.bodyMedium
        )

        LeadForm(
            fields = visibleFields,
            values = values,
            errors = errors,

            onValueChange = { name, newValue ->

                val updatedValues = values + (name to newValue)
                values = updatedValues

                showResult = false

                if (submitted || errors.containsKey(name)) {
                    errors = LeadFormValidator.validate(
                        config,
                        updatedValues
                    )
                }
            },

            onFocusLost = { _ ->
                errors = LeadFormValidator.validate(
                    config,
                    values
                )
            }
        )

        AppButtonField(
            label = "Submit",
            onClick = {
                keyboardController?.hide()
                submitted = true

                val currentErrors = LeadFormValidator.validate(
                    config,
                    values
                )

                errors = currentErrors

                showResult = currentErrors.isEmpty()
            }
        )

        if (showResult) {

            HorizontalDivider()

            Text(
                text = "Submitted successfully",
                style = MaterialTheme.typography.titleMedium
            )

            visibleFields.forEach { field ->

                val displayValue =
                    if (field.type.name == "CHECKBOX") {
                        if (values[field.name] == "true") {
                            "Yes"
                        } else {
                            "No"
                        }
                    } else {
                        values[field.name].orEmpty()
                            .ifBlank { "—" }
                    }

                Text(
                    text = "${field.label}: $displayValue",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(
            modifier = Modifier.height(AppSpacing.xl)
        )
    }
}