package com.deepshooter.simplepresenterapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.deepshooter.simplepresenterapp.login.model.LoginOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by Avinash on 12-07-2017.
 */

public class Helper extends SQLiteOpenHelper {



    private static String DB_PATH = "/data/data/com.deepshooter.simplepresenterapp/databases/";
    private static String DB_NAME = "deepshooter.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private String TAG = "Helper";
    Cursor cursorGetData;
    private static final int DATABASE_VERSION_OLD = 1;
    private static final int DATABASE_VERSION_NEW = 1;


    public Helper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION_NEW);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {

            if (myContext.deleteDatabase(DB_NAME))
                Log.i(TAG, "Database Deleted....");
            else
                Log.i(TAG, "Database Not Deleted..");

            try {
                copyDataBase();
                exportDatabase();
            } catch (IOException e1) {
                throw new Error("Error copying database");
            }

        }

    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void exportDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.deepshooter.simplepresenterapp//databases//deepshooter.sqlite";
                // String currentDBPath = DB_PATH + DB_NAME;
                String backupDBPath = "deepshooter.sqlite";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB)
                            .getChannel();
                    FileChannel dst = new FileOutputStream(backupDB)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    //Log.d("db", "copied");
                } else {
                    //Log.d("db", "dbnotexist");
                }
            } else {
                //Log.d("db", "notcopied");
            }
        } catch (Exception e) {
            //Log.d("db", "error");
        }
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        Log.e("dbExist", ">>" + dbExist);
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        } else {

            try {
                Cursor cursor = getData("Select * from Details");
            } catch (Exception e) {
                onUpgrade(myDataBase, DATABASE_VERSION_OLD, DATABASE_VERSION_NEW);
                this.getReadableDatabase();

            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //Log.d(TAG, "Error is" + e.toString());
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private Cursor getData(String sql) {
        openDataBase();
        cursorGetData = getReadableDatabase().rawQuery(sql, null);
        return cursorGetData;
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public long insertData(String tableName, ContentValues values) {
        openDataBase();
        return myDataBase.insert(tableName, null, values);
    }



    private int updateData(String tableName, ContentValues values, String condition) {
        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myDataBase.update(tableName, values, condition, null);
    }


    public long insertValueToLoginTable(LoginOutput loginOutput) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            openDataBase();
            db.execSQL("delete from LoginTable");
            db.close();
            Log.d("LoginTable", "Delete is success");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LoginTable", "Delete is failed");
        }

        long mTotalInsertedValues = 0;

        try {

                ContentValues contentValues = new ContentValues();
                contentValues.put("email", loginOutput.getEmail());
                contentValues.put("password", loginOutput.getPassword());
                contentValues.put("success", loginOutput.getSuccess());
                contentValues.put("message", loginOutput.getMessage());
                contentValues.put("organisation_id", loginOutput.getOrganisationId());

                insertData("LoginTable", contentValues);

            Log.e("Done", "Insertion LoginTable Success");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "Insertion LoginTable failed");
        }
        exportDatabase();
        db.close();
        return mTotalInsertedValues;
    }


    public ArrayList<LoginOutput> getAllLoginData() {

        ArrayList<LoginOutput> arrayList = new ArrayList<LoginOutput>();

        String sql = "select * from LoginTable";

        Cursor cursor = getData(sql);
        Log.e("chkLogin1", "Count = " + cursor.getCount());

        if (cursor != null || cursor.getCount() > 0) {

            cursor.moveToFirst();

            Log.e("chkLogin2", "chkLogin2");

            for (int size = 0; size < cursor.getCount(); size++) {


                LoginOutput loginOutput = new LoginOutput();
                loginOutput.setEmail(cursor.getString(0));
                loginOutput.setPassword(cursor.getString(1));
                loginOutput.setSuccess(Boolean.valueOf(cursor.getString(2)));
                loginOutput.setMessage(cursor.getString(3));
                loginOutput.setOrganisationId(cursor.getInt(4));

                arrayList.add(loginOutput);

                cursor.moveToNext();


            }
            cursor.close();
            cursorGetData.close();
            myDataBase.close();
        }

        return arrayList;
    }

}
