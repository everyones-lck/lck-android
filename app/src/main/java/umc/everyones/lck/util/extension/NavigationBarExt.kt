package umc.everyones.lck.util.extension

import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView

inline fun NavigationBarView.setupWithNavControllerCustom(
    navController: NavController,
    crossinline viewingPartySelected: (MenuItem) -> Boolean
){
    setupWithNavController(navController)  // 기본 설정 유지

    setOnItemSelectedListener { item ->
        viewingPartySelected(item)
        onNavDestinationSelected(item, navController)
        true
    }
}