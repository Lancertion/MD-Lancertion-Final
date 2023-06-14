package com.app.lancertion.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

const val SERIAL_DECODER_DATA_STORE_NAME = "lancertionPreferenceDataStore"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SERIAL_DECODER_DATA_STORE_NAME)