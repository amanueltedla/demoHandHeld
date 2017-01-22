package com.example.dventus_hq.demohandheldapp.Database_and_File_Managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HandheldDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MeterConsumption"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    public HandheldDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE METER_FILE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "METER_ID TEXT, "
                + "EMAIL_STATUS TEXT, "
                + "FILE_STATUS TEXT);");
        db.execSQL("CREATE TABLE METER_DATA("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "CONSUMPTION INTEGER, "
                + "FLOW_RATE INTEGER, "
                + "EVENT TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void insertMeterFile(SQLiteDatabase db, String meterId,String emailStatus,String fileStatus) {
        ContentValues MeterFileValues = new ContentValues();
        MeterFileValues.put("METER_ID", meterId);
        MeterFileValues.put("EMAIL_STATUS", emailStatus);
        MeterFileValues.put("FILE_STATUS", fileStatus);
        db.insert("METER_FILE", null,MeterFileValues);

    }
    public static void insertMeterConsumption(SQLiteDatabase db,int consumption, int flowRate,String event){
        ContentValues meterConsumptionValues = new ContentValues();
        meterConsumptionValues.put("CONSUMPTION",consumption);
        meterConsumptionValues.put("FLOW_RATE",flowRate);
        meterConsumptionValues.put("EVENT",event);
        db.insert("METER_DATA",null,meterConsumptionValues);
    }
    public static Cursor loadConsumption(SQLiteDatabase db){
        Cursor cursor = db.query ("METER_DATA",
                new String[] {"CONSUMPTION"},
                null,
                null,
                null, null,null);
        return cursor;
    }
    public static String loadMeter_Id(SQLiteDatabase db) {
        Cursor cursor = db.query ("METER_FILE",
                new String[] {"METER_ID", "EMAIL_STATUS"},
                null,
                null,
                null, null,null);
        cursor.moveToFirst();
        String meter_id = cursor.getString(0);
        cursor.close();
        db.close();
        return meter_id;
    }

    public static Cursor loadUnsentMeterFile(SQLiteDatabase db){
        Cursor cursor = db.query ("METER_FILE",
                new String[] {"_id","METER_ID"},
                "EMAIL_STATUS = ? AND FILE_STATUS = ?",
                new String[] {"Unsent","Generated"},
                null, null,null);
        return cursor;
    }

    public static Cursor loadSentMeterFile(SQLiteDatabase db){
        Cursor cursor = db.query ("METER_FILE",
                new String[] {"_id","METER_ID"},
                "EMAIL_STATUS = ?",
                new String[] {"Sent"},
                null, null,null);
        return cursor;
    }

    public static void UpdateSentMeterFiile(SQLiteDatabase db, String meterID){
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL_STATUS", "Sent");
        db.update("METER_FILE",
                contentValues,
                "METER_ID = ?",
                new String[] {meterID});
    }
    public static void UpdateGeneratedMeterFile(SQLiteDatabase db,String meterID){
        ContentValues contentValues = new ContentValues();
        contentValues.put("FILE_STATUS","Generated");
        db.update("METER_FILE",
                contentValues,
                "METER_ID = ?",
                new String[] {meterID});

    }

   public static void DeleteMeterFileRecord(SQLiteDatabase db, String meterID){
       db.delete("METER_FILE",
               "METER_ID = ?",
               new String[]{meterID});
   }
    public static void clearDatabase(SQLiteDatabase db){
        db.execSQL("delete from "+ "METER_FILE");
    }
}
