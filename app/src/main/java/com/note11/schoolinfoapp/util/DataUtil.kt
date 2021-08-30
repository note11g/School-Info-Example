package com.note11.schoolinfoapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import com.note11.schoolinfoapp.data.TimeModel
import com.note11.schoolinfoapp.data.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "info")

class DataUtil(private val context: Context) {
    private val gson = GsonBuilder().create()
    private val userInfoKey = stringPreferencesKey("user")
    private val timeInfoKey = stringPreferencesKey("time")

    suspend fun setUserInfo(userInfo: UserModel) {
        val info = gson.toJson(userInfo, UserModel::class.java)
        context.dataStore.edit { it[userInfoKey] = info }
    }

    suspend fun getUserInfoOnce() : UserModel? = getUserInfo().firstOrNull()

    private fun getUserInfo(): Flow<UserModel?> = context.dataStore.data.map { p ->
        val info = p[userInfoKey]
        info?.let {
            return@let gson.fromJson(it, UserModel::class.java)
        }
    }

    suspend fun setTimeInfo(timeInfo: TimeModel) {
        val info = gson.toJson(timeInfo, TimeModel::class.java)
        context.dataStore.edit { it[timeInfoKey] = info }
    }
    suspend fun getTimeInfoOnce() : TimeModel? = getTimeInfo().firstOrNull()

    private fun getTimeInfo() : Flow<TimeModel?> = context.dataStore.data.map { p ->
        val info = p[timeInfoKey]
        info?.let{
            return@let gson.fromJson(it, TimeModel::class.java)
        }
    }
}