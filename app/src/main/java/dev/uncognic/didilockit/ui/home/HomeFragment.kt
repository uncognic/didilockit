
package dev.uncognic.didilockit.ui.home

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.uncognic.didilockit.StatusManager
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var statusManager: StatusManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        statusManager = StatusManager(requireContext().applicationContext)


        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    LockScreen(statusManager)
                }
            }
        }
    }
}


@Composable
fun LockScreen(statusManager: StatusManager) {

    val isLocked by statusManager.lockStatusFlow.collectAsStateWithLifecycle(initialValue = false)
    val lastTime by statusManager.lastUpdatedFlow.collectAsStateWithLifecycle(initialValue = "Never")


    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = if (isLocked) "Locked" else "Unlocked",
            fontSize = 50.sp
        )
        Text(
            text = "Last updated: $lastTime",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                scope.launch {
                    val newStatus = !isLocked
                    statusManager.setStatus(newStatus)
                    statusManager.updateTime(System.currentTimeMillis())
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp),
            colors = if (isLocked) {
                ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inversePrimary)
            } else {
                ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            }
        ) {
            Text(
                if (isLocked) "Unlock" else "Lock",
            )
        }

    }

}
