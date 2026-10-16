package com.tarun.composeformkit.lead.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tarun.composeformkit.data.local.LeadEntity
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size10
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size12
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size16
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size20
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size5
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size6
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size8
import com.tarun.composeformkit.design_system.tokens.AppSpacing.size96
import com.tarun.composeformkit.design_system.tokens.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    leads: List<LeadEntity>,
    onAddClick: () -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    var leadToDelete by remember {
        mutableStateOf<LeadEntity?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lead Manager",
                        style = AppTypography.headline,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(
                    text = "+",
                    style = AppTypography.title,
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = size16),
            contentPadding = PaddingValues(
                top = size8,
                bottom = size96
            ),
            verticalArrangement = Arrangement.spacedBy(size12)
        ) {
            item(key = "dashboard_summary") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(size20),
                        verticalArrangement = Arrangement.spacedBy(size6)
                    ) {
                        Text(
                            text = "Total leads",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = leads.size.toString(),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = if (leads.size == 1) {
                                "1 contact saved"
                            } else {
                                "${leads.size} contacts saved"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            item(key = "leads_heading") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = size8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your leads",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "${leads.size} total",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (leads.isEmpty()) {
                item(key = "empty_state") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = size20),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(size8)
                    ) {
                        Surface(
                            modifier = Modifier.size(72.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "+",
                                    style = MaterialTheme.typography.displaySmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }

                        Text(
                            text = "No leads yet",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Tap + to add your first contact.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(
                    items = leads,
                    key = { it.id }
                ) { lead ->
                    LeadCard(
                        lead = lead,
                        onDelete = {
                            leadToDelete = lead
                        }
                    )
                }
            }
        }
    }

    // Confirm before permanently deleting a lead.
    leadToDelete?.let { lead ->
        AlertDialog(
            onDismissRequest = {
                leadToDelete = null
            },
            title = {
                Text("Delete lead?")
            },
            text = {
                Text(
                    "Are you sure you want to delete ${lead.fullName}? " +
                        "This action cannot be undone."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(lead.id)
                        leadToDelete = null
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        leadToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun LeadCard(
    lead: LeadEntity,
    onDelete: () -> Unit
) {

    val isCompanyLead = lead.leadType == "Company" && lead.companyName.isNotBlank()
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(46.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = lead.fullName
                                .trim()
                                .firstOrNull()
                                ?.uppercase() ?: "?",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = lead.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = lead.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete ${lead.fullName}",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Text(
                text = "Phone: ${lead.phone}",
                style = AppTypography.body,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Text(
                        text = lead.leadType,
                        modifier = Modifier.padding(
                            horizontal = size10,
                            vertical = size5
                        ),
                        style = AppTypography.label,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                if (lead.consent) {
                    Spacer(Modifier.size(size8))
                    Text(
                        text = "Consent given",
                        style = AppTypography.label,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            if (isCompanyLead) {
                Text(
                    text = "Company: ${lead.companyName}",
                    style = AppTypography.label,
                )
            }

            if (lead.notes.isNotBlank()) {
                Text(
                    text = "Note - ${lead.notes}",
                    style = AppTypography.body,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
