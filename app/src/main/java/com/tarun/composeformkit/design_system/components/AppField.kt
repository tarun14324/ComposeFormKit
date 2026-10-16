package com.tarun.composeformkit.design_system.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tarun.composeformkit.design_system.tokens.AppColors

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
            .heightIn(min = 80.dp)
    ) {
        if (label.isNotBlank()) {
            Text(
                text = if (required) "$label *" else label,
                style = MaterialTheme.typography.labelLarge
            )
        }

        control()

        if (hint != null) {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.TextSecondary
            )
        }

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.Error
            )
        }
    }
}