package org.example.fake_store_app.shared.local_db_helper

import org.example.fake_store_app.AppDatabase

class DatabaseHelper(factory: DatabaseDriverFactory) {
    val database: AppDatabase = AppDatabase(factory.create())
}

