package com.tarun.composeformkit.design_system.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tarun.composeformkit.design_system.tokens.AppColors
import com.tarun.composeformkit.design_system.tokens.AppDimensions.fieldMinHeight
import com.tarun.composeformkit.design_system.tokens.AppTypography

@Composable
fun AppField(
    label: String,
    hint: String?,
    error: String?,
    required: Boolean,
    modifier: Modifier = Modifier,
    control: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = fieldMinHeight)
    ) {
        if (label.isNotBlank()) {
            Text(
                text = if (required) "$label *" else label,
                style = AppTypography.body
            )
        }

        control()

        if (hint != null) {
            Text(
                text = hint,
                style = AppTypography.caption,
                color = AppColors.TextSecondary
            )
        }

        if (error != null) {
            Text(
                text = error,
                style = AppTypography.label,
                color = AppColors.Error
            )
        }
    }
}