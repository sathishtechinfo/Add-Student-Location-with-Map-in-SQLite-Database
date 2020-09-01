package com.example.studentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalit on 9/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserLog.db";

    // User table name
    private static final String TABLE_USER = "user";

    private static final String TABLE_STUDENT = "student";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_PASSWORD = "user_password";


    //Student table
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_IMGPATH="student_profileImg";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_SECTION = "section";
    private static final String COLUMN_SCHOOL = "school";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_BLOOD = "blood";
    private static final String COLUMN_FATHER = "fathername";
    private static final String COLUMN_MOTHER = "mothername";
    private static final String COLUMN_PARENTNO = "parentno";
    private static final String COLUMN_ADD1 = "add1";
    private static final String COLUMN_ADD2 = "add2";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_ZIPCODE = "zipcode";
    private static final String COLUMN_EMERGENCY= "emergencyno";
    private static final String COLUMN_LOCATION= "location";



    SQLiteDatabase db;

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PHONE + " NUMBER," + COLUMN_USER_PASSWORD + " TEXT" +")";


    private String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("

        + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_IMGPATH + " TEXT, "
          + COLUMN_NAME + " TEXT," + COLUMN_CLASS + " TEXT," +
            COLUMN_SECTION + " TEXT," + COLUMN_SCHOOL + " TEXT," + COLUMN_GENDER + " TEXT," + COLUMN_DOB + " TEXT," + COLUMN_BLOOD + " TEXT," + COLUMN_FATHER + " TEXT," +
            COLUMN_MOTHER + " TEXT," + COLUMN_PARENTNO + " TEXT," + COLUMN_ADD1 + " TEXT," + COLUMN_ADD2 + " TEXT," + COLUMN_CITY + " TEXT," + COLUMN_STATE + " TEXT," + COLUMN_ZIPCODE + " TEXT," +
            COLUMN_EMERGENCY + " TEXT," +COLUMN_LOCATION + " TEXT " +")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     * 
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
       // db.execSQL(DR);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());



        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addStudent(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, student.getUsername());
        values.put(COLUMN_IMGPATH,student.getImagePath());
        values.put(COLUMN_CLASS, student.getClassname());
        values.put(COLUMN_SECTION, student.getSection());
        values.put(COLUMN_SCHOOL, student.getSchool());
        values.put(COLUMN_GENDER, student.getGender());
        values.put(COLUMN_DOB, student.getDob());
        values.put(COLUMN_BLOOD, student.getBlood());
        values.put(COLUMN_FATHER, student.getFather());
        values.put(COLUMN_MOTHER, student.getMother());
        values.put(COLUMN_PARENTNO, student.getParentno());
        values.put(COLUMN_ADD1, student.getAdd1());
        values.put(COLUMN_ADD2, student.getAdd2());
        values.put(COLUMN_CITY, student.getCity());
        values.put(COLUMN_STATE, student.getState());
        values.put(COLUMN_EMERGENCY, student.getEmergencyno());
        values.put(COLUMN_LOCATION, student.getLocation());


        // Inserting Row
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }



    public void updateStudent(Student student)
    {

        String[] columns = {
                COLUMN_NAME

        };
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, student.getUsername());
        values.put(COLUMN_IMGPATH,student.getImagePath());
        values.put(COLUMN_CLASS, student.getClassname());
        values.put(COLUMN_SECTION, student.getSection());
        values.put(COLUMN_SCHOOL, student.getSchool());
        values.put(COLUMN_GENDER, student.getGender());
        values.put(COLUMN_DOB, student.getDob());
        values.put(COLUMN_BLOOD, student.getBlood());
        values.put(COLUMN_FATHER, student.getFather());
        values.put(COLUMN_MOTHER, student.getMother());
        values.put(COLUMN_PARENTNO, student.getParentno());
        values.put(COLUMN_ADD1, student.getAdd1());
        values.put(COLUMN_ADD2, student.getAdd2());
        values.put(COLUMN_CITY, student.getCity());
        values.put(COLUMN_STATE, student.getState());
        values.put(COLUMN_EMERGENCY, student.getEmergencyno());
        values.put(COLUMN_LOCATION, student.getLocation());


        // Inserting Row
        db.update(TABLE_STUDENT, values, COLUMN_NAME+"='"+student.getUsername()+"'",null);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_PHONE,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_PHONE + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public boolean checkStudent(String name) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {name};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_STUDENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method to check user exist or not
     *
     * @param username
     * @param password
     * @return true/false
     */
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE user_name=? AND user_password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;

    }
    public List<Student> getAllStudent(String id) {
        // array of columns to fetch
        String[] columns = {

                COLUMN_STUDENT_ID,
                COLUMN_IMGPATH,
                COLUMN_NAME,
                COLUMN_CLASS,
                COLUMN_SECTION,
                COLUMN_SCHOOL,
                COLUMN_GENDER,
                COLUMN_DOB,
                COLUMN_BLOOD,
                COLUMN_FATHER,
                COLUMN_MOTHER,
                COLUMN_PARENTNO,
                COLUMN_ADD1,
                COLUMN_ADD2,
                COLUMN_STATE,
                COLUMN_CITY,
                COLUMN_EMERGENCY,
                COLUMN_LOCATION

        };

        List<Student> userList = new ArrayList<Student>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        if(id.isEmpty())
        {
             cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null);

        }else {
             cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT + " WHERE student_id=?", new String[]{id});
        }
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                student.setImagePath(cursor.getString(cursor.getColumnIndex(COLUMN_IMGPATH)));
                student.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                student.setClassname(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));
                student.setSchool(cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL)));
                student.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                student.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));
                student.setBlood(cursor.getString(cursor.getColumnIndex(COLUMN_BLOOD)));
                student.setFather(cursor.getString(cursor.getColumnIndex(COLUMN_FATHER)));
                student.setMother(cursor.getString(cursor.getColumnIndex(COLUMN_MOTHER)));
                student.setParentno(cursor.getString(cursor.getColumnIndex(COLUMN_PARENTNO)));
                student.setAdd1(cursor.getString(cursor.getColumnIndex(COLUMN_ADD1)));
                student.setAdd2(cursor.getString(cursor.getColumnIndex(COLUMN_ADD2)));
                student.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
                student.setState(cursor.getString(cursor.getColumnIndex(COLUMN_STATE)));
                student.setEmergencyno(cursor.getString(cursor.getColumnIndex(COLUMN_EMERGENCY)));
                student.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));

                // Adding user record to list
                userList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }



}
