package com.tarun.composeformkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tarun.composeformkit.data.local.DatabaseProvider
import com.tarun.composeformkit.data.repository.LeadRepository
import com.tarun.composeformkit.lead.ui.LeadCaptureScreen
import com.tarun.composeformkit.lead.ui.MainScreen
import com.tarun.composeformkit.lead.viewmodel.LeadEvent
import com.tarun.composeformkit.lead.viewmodel.LeadViewModel
import com.tarun.composeformkit.lead.viewmodel.LeadViewModelFactory

class MainActivity : ComponentActivity() {

    private val leadViewModel: LeadViewModel by viewModels {
        val database = DatabaseProvider.getDatabase(
            applicationContext
        )

        val repository = LeadRepository(
            database.leadDao()
        )

        LeadViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing
                    )
                ) {
                    LeadApp(leadViewModel)
                }
            }
        }
    }
}

@Composable
private fun LeadApp(
    viewModel: LeadViewModel
) {
    val navController = rememberNavController()

    val leads by viewModel.leads.collectAsStateWithLifecycle()
    val saveState by viewModel.saveState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                leads = leads,
                onAddClick = {
                    navController.navigate("add_lead") {
                        launchSingleTop = true
                    }
                },
                onDeleteClick = {
                    viewModel.deleteLead(it)
                }
            )
        }

        composable("add_lead") {
            LaunchedEffect(viewModel) {
                viewModel.events.collect { event ->
                    when (event) {
                        LeadEvent.Saved -> {
                            navController.popBackStack()
                        }
                    }
                }
            }

            LeadCaptureScreen(
                isSaving = saveState.isSaving,
                saveError = saveState.error,
                onSubmit = viewModel::addLead,
                onClearSaveError = viewModel::clearSaveError
            )
        }
    }
}