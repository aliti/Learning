package com.example.androidnavigator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.androidnavigator.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2008-2013, Behsazan-e-Mellat, Co. All rights reserved.
 * <p> 5/11/14, 10:31 AM </p>
 * <p/>
 * <p> @author: <a href="mailto:ali.heydari@outlook.com">Ali Heydari Moghaddam</a></p>
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static String TAG = "*** DatabaseHandler ***";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UsersManager";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PASS = "pass";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT NOT NULL UNIQUE,"
                + KEY_MAIL + " TEXT NOT NULL UNIQUE,"
                + KEY_PASS + " TEXT NOT NULL "
                + ")";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        Log.d(TAG, "Table " + TABLE_USERS + " successfully created.");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new userModel
    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, userModel.getName()); // UserModel Name
            values.put(KEY_MAIL, userModel.getEmail()); // UserModel Mail
            values.put(KEY_PASS, userModel.getPassword()); // UserModel Pass

            // Inserting Row
            db.insertOrThrow(TABLE_USERS, null, values);
            result = true;
        } catch (SQLiteException e) {
            result = false;
        } finally {
            db.close();
        }

        return result;
    }

    // Getting single user
    public UserModel getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID, KEY_NAME, KEY_MAIL, KEY_PASS},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        UserModel userModel = new UserModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return userModel;
    }

    // Getting single user by name or mail
    public UserModel isValidUser(String userName, String pass) {
        UserModel userModel = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID, KEY_NAME, KEY_MAIL, KEY_PASS},
                KEY_NAME + "=? OR " + KEY_MAIL + "=? AND " + KEY_PASS + "=?",
                new String[]{String.valueOf(userName), String.valueOf(userName), String.valueOf(pass)}
                , null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            userModel = new UserModel(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        return userModel;
    }

    // Getting All users
    public List<UserModel> getAllUsers() {
        List<UserModel> userModelList = new ArrayList<UserModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(Integer.parseInt(cursor.getString(0)));
                userModel.setName(cursor.getString(1));
                userModel.setEmail(cursor.getString(2));
                userModel.setPassword(cursor.getString(3));
                // Adding userModel to list
                userModelList.add(userModel);
            } while (cursor.moveToNext());
        }

        // return user list
        return userModelList;
    }

    // Getting users Count
    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();

        // return count
        return count;
    }

    // Updating single userModel
    public int updateUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userModel.getName());
        values.put(KEY_MAIL, userModel.getEmail());
        values.put(KEY_PASS, userModel.getPassword());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?", new String[]{String.valueOf(userModel.getId())});
    }

    // Deleting single userModel
    public void deleteUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?", new String[]{String.valueOf(userModel.getId())});
        db.close();
    }

}
