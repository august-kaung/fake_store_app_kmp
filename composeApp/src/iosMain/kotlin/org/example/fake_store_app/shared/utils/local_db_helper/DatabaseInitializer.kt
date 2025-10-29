package org.example.fake_store_app.shared.local_db_helper

import org.example.fake_store_app.AppDatabase

fun initializeDatabase(): AppDatabase {
    val factory = DatabaseDriverFactory()
    return DatabaseHelper(factory).database
}