package dev.uncognic.didilockit
import android.content.Context
import android.text.format.DateUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "status")
class StatusManager(private val context: Context) {
    companion object {
        val is_locked_key = booleanPreferencesKey("is_locked_key")
        val last_updated_key = longPreferencesKey("last_updated_key")
    }
    val lockStatusFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[is_locked_key] ?: false
    }
    val lastUpdatedFlow: Flow<String> = context.dataStore.data.map { preferences ->
        val timestamp = preferences[last_updated_key] ?: 0L

        if (timestamp == 0L) {
            "Never"
        } else {
            val now = System.currentTimeMillis()
            val timeDiff = now - timestamp
            if (timeDiff < DateUtils.MINUTE_IN_MILLIS) {
                "Now"
            } else {
                DateUtils.getRelativeTimeSpanString(
                    timestamp,
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS
                ).toString()
            }
        }
    }
    suspend fun setStatus(is_locked: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[is_locked_key] = is_locked
        }
    }
    suspend fun updateTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[last_updated_key] = time
        }
    }




}