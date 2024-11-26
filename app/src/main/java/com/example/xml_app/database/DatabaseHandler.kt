package com.example.xml_app.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class ParticipantDB(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val studentStatus: Int,
    val skillLevel: String
)
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {
    companion object {
        private const val DATABASE_NAME = "participants.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "participants"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRST_NAME = "first_name"
        private const val COLUMN_LAST_NAME = "last_name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_STUDENT_STATUS = "student_status"
        private const val COLUMN_SKILL_LEVEL = "skill_level"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME " +
                    "($COLUMN_ID INTEGER PRIMARY KEY, " +
                    "$COLUMN_FIRST_NAME TEXT, " +
                    "$COLUMN_LAST_NAME TEXT, " +
                    "$COLUMN_EMAIL TEXT, " +
                    "$COLUMN_GENDER TEXT," +
                    "$COLUMN_STUDENT_STATUS INTEGER, " +
                    "$COLUMN_SKILL_LEVEL INTEGER) "
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertData(participantDB: ParticipantDB) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRST_NAME, participantDB.firstName)
            put(COLUMN_LAST_NAME, participantDB.lastName)
            put(COLUMN_EMAIL, participantDB.email)
            put(COLUMN_GENDER, participantDB.gender)
            put(COLUMN_STUDENT_STATUS, participantDB.studentStatus)
            put(COLUMN_SKILL_LEVEL, participantDB.skillLevel)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllParticipants() : List<ParticipantDB> {
        val factsList = mutableListOf<ParticipantDB>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))
            val studentStatus = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_STATUS))
            val skillLevel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL_LEVEL))

            val dbRecord = ParticipantDB(id, firstName, lastName, email, gender, studentStatus, skillLevel)
            factsList.add(dbRecord)
        }
        cursor.close()
        db.close()
        return factsList
    }

    fun clearDatabase() {
        val db = writableDatabase
        val clearQuery = "DELETE FROM $TABLE_NAME"
        db.execSQL(clearQuery)
    }

    fun getRecord(participantID: String) : ParticipantDB? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(participantID))

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))
            val studentStatus = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_STATUS))
            val skillLevel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL_LEVEL))

            cursor.close()
            db.close()

            return ParticipantDB(id, firstName, lastName, email, gender, studentStatus, skillLevel)
        } else {
            cursor.close()
            db.close()
            return null
        }
    }

    fun deleteParticipant(id: Int?) {
        val db = writableDatabase
        val where = "$COLUMN_ID = ?"
        val args = arrayOf(id.toString())
        db.delete(TABLE_NAME, where, args)
        db.close()
    }
}