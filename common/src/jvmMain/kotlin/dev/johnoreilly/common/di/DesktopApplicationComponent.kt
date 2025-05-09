package dev.johnoreilly.common.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.johnoreilly.common.database.AppDatabase
import dev.johnoreilly.common.database.dbFileName
import io.ktor.client.engine.java.Java
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.io.File


@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class DesktopApplicationComponent: SharedApplicationComponent {
  override fun httpClientEngine() = Java.create()

  override fun appDatabase() = createRoomDatabase()
}

fun createRoomDatabase(): AppDatabase {
  val dbFile = File(System.getProperty("java.io.tmpdir"), dbFileName)
  return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
    .setDriver(BundledSQLiteDriver())
    .build()
}