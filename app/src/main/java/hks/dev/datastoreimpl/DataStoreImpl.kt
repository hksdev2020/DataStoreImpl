package hks.dev.datastoreimpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreImpl(private val context: Context) {

    private object PreferencesKeys {
        val LIGHT_ON_KEY = booleanPreferencesKey("isLightOn")
    }

    private val DATA_STORE_NAME = "settings"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)


    fun isLightOn(): Flow<Boolean> = context.applicationContext.dataStore
        .data //flow
        .catch { exception ->
            // throws IOException if it can't read the data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[PreferencesKeys.LIGHT_ON_KEY] ?: false
        }


    suspend fun toggleLight() {
        context.dataStore.edit {
            it[PreferencesKeys.LIGHT_ON_KEY] = !(it[PreferencesKeys.LIGHT_ON_KEY] ?: false)
        }
    }


}