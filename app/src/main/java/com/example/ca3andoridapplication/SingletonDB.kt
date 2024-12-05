//import android.content.Context
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//object DatabaseProvider {
//    @Volatile
//    private var INSTANCE: RoomDatabaseClass? = null
//    fun getDatabase(context: Context): RoomDatabaseClass {
//        return INSTANCE ?: synchronized(this) {
//            val instance = Room.databaseBuilder(
//                context.applicationContext,
//                RoomDatabaseClass::class.java,
//                "Resident_Table"
//            ).build()
//            INSTANCE = instance
//            instance
//        }
//    }
//}
