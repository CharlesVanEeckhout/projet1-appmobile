package ca.qc.cgodin.projet1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.qc.cgodin.projet1.model.Succursale

@Database(entities = arrayOf(Succursale::class), version = 1, exportSchema = false)
abstract class SuccursaleRoomDatabase : RoomDatabase() {
    abstract fun succursaleDao(): SuccursaleDao
    companion object{
        //Singleton to prevent multiple instances of database openint at the same time
        @Volatile
        private var INSTANCE: SuccursaleRoomDatabase? = null
        fun getDatabase(context: Context): SuccursaleRoomDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                SuccursaleRoomDatabase::class.java,
                "succursale_database"
            ).build()
            return INSTANCE as SuccursaleRoomDatabase
        }
    }

}