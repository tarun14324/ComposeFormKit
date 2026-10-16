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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.tarun.composeformkit.design_system.atoms.AppButtonField
import com.tarun.composeformkit.design_system.tokens.AppColors
import com.tarun.composeformkit.design_system.tokens.AppSpacing
import com.tarun.composeformkit.design_system.tokens.AppTypography
import com.tarun.composeformkit.lead.config.LeadFormConfig
import com.tarun.composeformkit.lead.validation.LeadFormValidator

@Composable
fun LeadCaptureScreen(
    isSaving: Boolean,
    saveError: String?,
    onSubmit: (Map<String, String>) -> Unit,
    onClearSaveError: () -> Unit
) {
    val config = LeadFormConfig.fields

    var values by rememberSaveable {
        mutableStateOf(config.associate { it.name to "" })
    }

    var errors by rememberSaveable {
        mutableStateOf<Map<String, String>>(emptyMap())
    }

    var submitted by rememberSaveable {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val visibleFields = config.filter { field ->
        val condition = field.visibleWhen
        condition == null ||
                values[condition.fieldName] == condition.equalsValue
    }

    val validationErrors = LeadFormValidator.validate(
        config = config,
        values = values
    )

    val isFormValid = validationErrors.isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = AppSpacing.size16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.size16)
    ) {
        Spacer(Modifier.height(AppSpacing.size16))

        Text(
            text = "Get in touch",
            style = AppTypography.headline
        )

        Text(
            text = "Tell us a little about yourself.",
            style = AppTypography.title
        )

        LeadForm(
            fields = visibleFields,
            values = values,
            errors = errors,
            onValueChange = { name, newValue ->
                val updatedValues = values + (name to newValue)
                values = updatedValues

                onClearSaveError()

                if (submitted || errors.containsKey(name)) {
                    errors = LeadFormValidator.validate(
                        config,
                        updatedValues
                    )
                }
            },
            onFocusLost = { name ->
                val allErrors = LeadFormValidator.validate(
                    config,
                    values
                )

                // On blur, show the error for this field only.
                errors = if (allErrors.containsKey(name)) {
                    errors + (name to allErrors.getValue(name))
                } else {
                    errors - name
                }
            }
        )

        AppButtonField(
            label = if (isSaving) "Saving..." else "Submit",
            enabled = isFormValid && !isSaving,
            onClick = {
                keyboardController?.hide()
                submitted = true

                val submitErrors = LeadFormValidator.validate(
                    config = config,
                    values = values
                )

                errors = submitErrors

                if (submitErrors.isEmpty() && !isSaving) {
                    onClearSaveError()
                    onSubmit(values)
                }
            }
        )

        if (saveError != null) {
            Text(
                text = saveError,
                color = AppColors.Error,
                style = AppTypography.headline
            )
        }

        Spacer(Modifier.height(AppSpacing.size24))
    }
}