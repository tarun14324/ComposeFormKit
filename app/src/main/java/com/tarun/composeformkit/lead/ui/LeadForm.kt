package com.tarun.composeformkit.lead.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.tarun.composeformkit.design_system.tokens.AppDimensions
import com.tarun.composeformkit.design_system.tokens.AppSpacing
import com.tarun.composeformkit.lead.config.FieldConfig
import com.tarun.composeformkit.lead.config.FieldSpan

@Composable
fun LeadForm(
    fields: List<FieldConfig>,
    values: Map<String, String>,
    errors: Map<String, String>,
    onValueChange: (String, String) -> Unit,
    onFocusLost: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        val isExpanded =
            maxWidth >= AppDimensions.expandedBreakpoint

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement =
                Arrangement.spacedBy(AppSpacing.size20)
        ) {
            var index = 0

            while (index < fields.size) {
                val current = fields[index]

                val hasPair =
                    isExpanded &&
                            current.span == FieldSpan.HALF &&
                            index + 1 < fields.size &&
                            fields[index + 1].span == FieldSpan.HALF

                if (hasPair) {
                    val next = fields[index + 1]

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.spacedBy(AppSpacing.size16)
                    ) {
                        Box(Modifier.weight(1f)) {
                            key(current.name) {
                                LeadFieldRenderer(
                                    config = current,
                                    value = values[current.name].orEmpty(),
                                    error = errors[current.name],
                                    onValueChange = {
                                        onValueChange(current.name, it)
                                    },
                                    onFocusLost = {
                                        onFocusLost(current.name)
                                    }
                                )
                            }
                        }

                        Box(Modifier.weight(1f)) {
                            key(next.name) {
                                LeadFieldRenderer(
                                    config = next,
                                    value = values[next.name].orEmpty(),
                                    error = errors[next.name],
                                    onValueChange = {
                                        onValueChange(next.name, it)
                                    },
                                    onFocusLost = {
                                        onFocusLost(next.name)
                                    }
                                )
                            }
                        }
                    }

                    index += 2
                } else {
                    Box(Modifier.fillMaxWidth()) {
                        key(current.name) {
                            LeadFieldRenderer(
                                config = current,
                                value = values[current.name].orEmpty(),
                                error = errors[current.name],
                                onValueChange = {
                                    onValueChange(current.name, it)
                                },
                                onFocusLost = {
                                    onFocusLost(current.name)
                                }
                            )
                        }
                    }

                    index++
                }
            }
        }
    }
}