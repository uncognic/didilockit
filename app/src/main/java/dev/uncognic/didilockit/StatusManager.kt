package dev.uncognic.didilockit
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "status")
class StatusManager(private val context: Context) {
    companion object {
        val is_locked_key = booleanPreferencesKey("is_locked_key")
    }

    suspend fun set_status(is_locked: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[is_locked_key] = is_locked
        }
    }
    val lockStatusFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[is_locked_key] ?: false
    }



}