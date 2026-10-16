package com.tarun.composeformkit.design_system.atoms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AppTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onFocusLost: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    multiline: Boolean = false,
    maxLength: Int? = null
) {
    var wasFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (maxLength == null || newValue.length <= maxLength) {
                onValueChange(newValue)
            }
        },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { state ->
                if (wasFocused && !state.isFocused) {
                    onFocusLost()
                }
                wasFocused = state.isFocused
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = !multiline,
        minLines = if (multiline) 4 else 1,
        maxLines = if (multiline) 4 else 1
    )
}