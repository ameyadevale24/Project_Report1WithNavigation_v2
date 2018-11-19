package com.example.a300288675.project_report1;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "MoneyManager.db";
    final static int DATABASE_VERSION = 1;

    final static String TABLE_USER = "User";
    final static String TABLE_USER_COL1 = "LoginID";
    final static String TABLE_USER_COL2 = "FName";
    final static String TABLE_USER_COL3 = "LName";
    final static String TABLE_USER_COL4 = "Email";
    final static String TABLE_USER_COL5 = "Phone";
    final static String TABLE_USER_COL6 = "Password";

    final static String TABLE_MONTHLY_INCOME_TRACKING = "MonthlyIncomeTracking";
    final static String TABLE_MONTHLY_INCOME_TRACKING_COL1 = "LoginID";
    final static String TABLE_MONTHLY_INCOME_TRACKING_COL2 = "StartMonth";
    final static String TABLE_MONTHLY_INCOME_TRACKING_COL3 = "Amount";

    final static String TABLE_EXPENSE_CATEGORY = "ExpenseCategory";
    final static String TABLE_EXPENSE_CATEGORY_COL1 = "ECategoryID";
    final static String TABLE_EXPENSE_CATEGORY_COL2 = "ECategoryName";

    final static String TABLE_DAILY_SAVING = "DailySaving";
    final static String TABLE_DAILY_SAVING_COL1 = "LoginID";
    final static String TABLE_DAILY_SAVING_COL2 = "Date";
    final static String TABLE_DAILY_SAVING_COL3 = "AmountSaved";

    final static String TABLE_EVENTWISE_SAVING = "EventwiseSaving";
    final static String TABLE_EVENTWISE_SAVING_COL1 = "LoginID";
    final static String TABLE_EVENTWISE_SAVING_COL2 = "EventName";
    final static String TABLE_EVENTWISE_SAVING_COL3 = "EventDescription";
    final static String TABLE_EVENTWISE_SAVING_COL4 = "AmountRequired";
    final static String TABLE_EVENTWISE_SAVING_COL5 = "AmountSaved";

    final static String TABLE_TRANSACTIONS = "Transactions";
    final static String TABLE_TRANSACTIONS_COL1 = "TransactionID";
    final static String TABLE_TRANSACTIONS_COL2 = "LoginID";
    final static String TABLE_TRANSACTIONS_COL3 = "ECategoryID";
    final static String TABLE_TRANSACTIONS_COL4 = "Date";
    final static String TABLE_TRANSACTIONS_COL5 = "Amount";
    final static String TABLE_TRANSACTIONS_COL6 = "Memo";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;

        //CREATION OF User TABLE
        query = "CREATE TABLE " + TABLE_USER +
                "(" + TABLE_USER_COL1 + " VARCHAR(25) PRIMARY KEY, " +
                TABLE_USER_COL2 + " VARCHAR(25), " +
                TABLE_USER_COL3 + " VARCHAR(25), " +
                TABLE_USER_COL4 + " VARCHAR(25), " +
                TABLE_USER_COL5 + " VARCHAR(25), " +
                TABLE_USER_COL6 + " VARCHAR(25) " +
                ");";
        db.execSQL(query);

        //CREATION OF MonthlyIncomeTracking TABLE
        //SQLite does not allow multiple primary key columns
        query = "CREATE TABLE " + TABLE_MONTHLY_INCOME_TRACKING +
                "(" + TABLE_MONTHLY_INCOME_TRACKING_COL1 + " VARCHAR(25) PRIMARY KEY, " +
                TABLE_MONTHLY_INCOME_TRACKING_COL2 + " DATE , " +
                TABLE_MONTHLY_INCOME_TRACKING_COL3 + " DECIMAL(8,2)" +
                ");";
        db.execSQL(query);

        //CREATION OF ExpenseCategory TABLE
        query = "CREATE TABLE " + TABLE_EXPENSE_CATEGORY +
                "(" + TABLE_EXPENSE_CATEGORY_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_EXPENSE_CATEGORY_COL2 + " VARCHAR(25) " +
                ");";
        db.execSQL(query);

        //CREATION OF DailySaving TABLE
        //SQLite does not allow multiple primary key columns
        query = "CREATE TABLE " + TABLE_DAILY_SAVING +
                "(" + TABLE_DAILY_SAVING_COL1 + " VARCHAR(25), " +
                TABLE_DAILY_SAVING_COL2 + " DATE PRIMARY KEY, " +
                TABLE_DAILY_SAVING_COL3 + " DECIMAL(8,2) " +
                ");";
        db.execSQL(query);

        //CREATION OF EventwiseSaving TABLE
        //SQLite does not allow multiple primary key columns
        query = "CREATE TABLE " + TABLE_EVENTWISE_SAVING +
                "(" + TABLE_EVENTWISE_SAVING_COL1 + " VARCHAR(25) PRIMARY KEY, " +
                TABLE_EVENTWISE_SAVING_COL2 + " VARCHAR(25) , " +
                TABLE_EVENTWISE_SAVING_COL3 + " TEXT, " +
                TABLE_EVENTWISE_SAVING_COL4 + " DECIMAL(8,2), " +
                TABLE_EVENTWISE_SAVING_COL5 + " DECIMAL(8,2)" +
                ");";
        db.execSQL(query);

        //CREATION OF Transaction TABLE
        query = "CREATE TABLE " + TABLE_TRANSACTIONS +
                "(" + TABLE_TRANSACTIONS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_TRANSACTIONS_COL2 + " VARCHAR(25), " +
                TABLE_TRANSACTIONS_COL3 + " INTEGER, " +
                TABLE_TRANSACTIONS_COL4 + " DATETIME, " +
                TABLE_TRANSACTIONS_COL5 + " DECIMAL(8,2), " +
                TABLE_TRANSACTIONS_COL6 + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTHLY_INCOME_TRACKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_SAVING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTWISE_SAVING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }


    public boolean addUser(String loginID, String fName, String lName, String email, String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_USER_COL1,loginID);
        cv.put(TABLE_USER_COL2,fName);
        cv.put(TABLE_USER_COL3,lName);
        cv.put(TABLE_USER_COL4,email);
        cv.put(TABLE_USER_COL5,phone);
        cv.put(TABLE_USER_COL6,password);
        long r = db.insert(TABLE_USER,null, cv);
        if (r==-1)
            return false;
        else
            return true;
    }


    public boolean addMonthlyIncomeTracking(String loginID, int startMonth, int year, Double amount){
        SQLiteDatabase db = this.getWritableDatabase();

        Calendar cal = Calendar.getInstance();
        cal.set(year, startMonth-1, 01);
        //Date date = (Date)cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        ContentValues cv = new ContentValues();
        cv.put(TABLE_MONTHLY_INCOME_TRACKING_COL1,loginID);
        cv.put(TABLE_MONTHLY_INCOME_TRACKING_COL2,format1.format(cal.getTime()));
        cv.put(TABLE_MONTHLY_INCOME_TRACKING_COL3,amount);
        long r = db.insert(TABLE_MONTHLY_INCOME_TRACKING,null, cv);
        if (r==-1)
            return false;
        else
            return true;
    }

    public boolean addExpenseCategory(String eCategoryName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_EXPENSE_CATEGORY_COL2,eCategoryName);
        long r = db.insert(TABLE_EXPENSE_CATEGORY,null, cv);
        if (r==-1)
            return false;
        else
            return true;
    }

    public boolean addDailySaving(String loginID, String date, double amountSaved){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_DAILY_SAVING_COL1,loginID);
        cv.put(TABLE_DAILY_SAVING_COL2,date);
        cv.put(TABLE_DAILY_SAVING_COL3,amountSaved);
        long r = db.insert(TABLE_DAILY_SAVING,null, cv);
        if (r==-1)
            return false;
        else
            return true;
    }


    public boolean addEventwiseSaving(String loginID, String eventName, String eventDescription, double amountRequired, double amountSaved){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_EVENTWISE_SAVING_COL1,loginID);
        cv.put(TABLE_EVENTWISE_SAVING_COL2,eventName);
        cv.put(TABLE_EVENTWISE_SAVING_COL3,eventDescription);
        cv.put(TABLE_EVENTWISE_SAVING_COL4,amountRequired);
        cv.put(TABLE_EVENTWISE_SAVING_COL5,amountSaved);
        long r = db.insert(TABLE_EVENTWISE_SAVING,null, cv);
        if (r==-1)
            return false;
        else
            return true;
    }

    public boolean addTransaction(String loginID, int eCategoryID, String date, double amount, String memo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_TRANSACTIONS_COL2,loginID);
        cv.put(TABLE_TRANSACTIONS_COL3,eCategoryID);
        cv.put(TABLE_TRANSACTIONS_COL4,date);
        cv.put(TABLE_TRANSACTIONS_COL5,amount);
        cv.put(TABLE_TRANSACTIONS_COL6,memo);
        long r = db.insert(TABLE_TRANSACTIONS,null, cv);
        if (r==-1)
            return false;
        else
            return true;
    }


    public boolean updateUser(String loginID, String fName, String lName, String email, String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        query = "UPDATE " + TABLE_USER +
                " SET " + TABLE_USER_COL2 + "='" + fName + "'" +
                "," + TABLE_USER_COL3 + "='" + lName + "'" +
                "," + TABLE_USER_COL4 + "='" + email + "'" +
                "," + TABLE_USER_COL5 + "='" + phone + "'" +
                "," + TABLE_USER_COL6 + "='" + password + "'" +
                " WHERE " + TABLE_USER_COL1 + "='" + loginID + "'";

        db.execSQL(query);
        return true;
    }


    public boolean updateMonthlyIncome(String loginID, String startMonth, double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        query = "UPDATE " + TABLE_MONTHLY_INCOME_TRACKING +
                " SET " + TABLE_MONTHLY_INCOME_TRACKING_COL3 + "=" + amount +
                " WHERE " + TABLE_MONTHLY_INCOME_TRACKING_COL1 + "='" + loginID + "' AND " + TABLE_MONTHLY_INCOME_TRACKING_COL2 + "='" + startMonth + "'";

        db.execSQL(query);
        return true;
    }

    public boolean updateDailySaving(String loginID, String date, double amountSavedForDay){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        query = "UPDATE " + TABLE_DAILY_SAVING +
                " SET " + TABLE_DAILY_SAVING_COL3 + "=" + TABLE_DAILY_SAVING_COL3 + "+" + amountSavedForDay +
                " WHERE " + TABLE_DAILY_SAVING_COL1 + "='" + loginID + "' AND " + TABLE_DAILY_SAVING_COL2 + "='" + date + "'";
        db.execSQL(query);
        return true;
    }

    public boolean updateEventwiseSaving(String loginID, String eventName, String eventDescription, double amountRequired, double amountSaved){
        // this function is called in general when an user wants to update/edit the created events
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        query = "UPDATE " + TABLE_EVENTWISE_SAVING +
                " SET " + TABLE_EVENTWISE_SAVING_COL3 + "='" + eventDescription + "'" +
                "," + TABLE_EVENTWISE_SAVING_COL4 + "=" + amountRequired +
                "," + TABLE_EVENTWISE_SAVING_COL5 + "=" + amountSaved +
                " WHERE " + TABLE_EVENTWISE_SAVING_COL1 + "='" + loginID + "' AND " + TABLE_DAILY_SAVING_COL2 + "='" + eventName + "'";
        db.execSQL(query);
        return true;
    }

    public boolean updateSavedAmountEventwiseSaving(String loginID, String eventName,double additionalAmountSaved){
        // this fucntion is called when user selects specific amount from his saved wallet and adds it in for an event
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        query = "UPDATE " + TABLE_EVENTWISE_SAVING +
                " SET " + TABLE_EVENTWISE_SAVING_COL5 + "=" + TABLE_EVENTWISE_SAVING_COL5 + "+" + additionalAmountSaved +
                " WHERE " + TABLE_EVENTWISE_SAVING_COL1 + "='" + loginID + "' AND " + TABLE_DAILY_SAVING_COL2 + "='" + eventName + "'";
        db.execSQL(query);
        return true;
    }



    public boolean updateTransaction(int transactionID, String loginID, int eCategoryID, String date, double amount, String memo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        query = "UPDATE " + TABLE_TRANSACTIONS +
                " SET " + TABLE_TRANSACTIONS_COL3 + "=" + eCategoryID +
                "," + TABLE_TRANSACTIONS_COL4 + "='" + date + "'" +
                "," + TABLE_TRANSACTIONS_COL5 + "=" + amount +
                "," + TABLE_TRANSACTIONS_COL6 + "='" + memo + "'" +
                " WHERE " + TABLE_TRANSACTIONS_COL1 + "=" + transactionID;

        db.execSQL(query);
        return true;
    }


    public boolean deleteUser(String loginID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER + " WHERE " + TABLE_USER_COL1 + "='" + loginID + "'");
        return true;
    }

    public boolean deleteEvent(String loginID, String eventName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EVENTWISE_SAVING + " WHERE " + TABLE_EVENTWISE_SAVING_COL1 + "='" + loginID  + "'" +
                " AND " + TABLE_EVENTWISE_SAVING_COL2 + "='" + eventName + "'");
        return true;
    }

    public boolean deleteTransaction(String transactionID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " + TABLE_TRANSACTIONS_COL1+ "=" + transactionID);
        return true;
    }

    public boolean checkUserExists(String loginID){
        //check whether a user exists or not. returns true if user exists and false if user does not exist
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + TABLE_USER_COL1 + "='" + loginID + "'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount() == 0)
            return false;
        else
            return true;
    }


    public boolean validateUser(String loginID, String password){
        //validates a user based on loginID and password. returns true if correct credentials are entered and false for wrong loginID or password
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + TABLE_USER_COL1 + "='" + loginID + "' AND " + TABLE_USER_COL6 + "='" + password + "'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount() == 0)
            return false;
        else
            return true;
    }


    public String getExpenseCategoryName(int eCategoryID){
        //returns the Name of the expense category based on the expense category id passed
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TABLE_EXPENSE_CATEGORY_COL2 + " FROM " + TABLE_EXPENSE_CATEGORY + " WHERE " + TABLE_EXPENSE_CATEGORY_COL1 + "=" + eCategoryID;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor.getString(1);
        else
            return "(Id not found)";
    }


    public double getMonthlyIncomeAmount(String loginID, int month, int year){
        //returns the monthly income of user based on the month. returns -1 if no record found for that user for that month
        SQLiteDatabase db = this.getWritableDatabase();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 01);         //StartMonth in MonthlyTracking table will always have day 1 of the respective month
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String query = "SELECT " + TABLE_MONTHLY_INCOME_TRACKING_COL3 + " FROM " + TABLE_MONTHLY_INCOME_TRACKING +
                " WHERE " + TABLE_MONTHLY_INCOME_TRACKING_COL1 + "='" + loginID + "' AND " +
                TABLE_MONTHLY_INCOME_TRACKING_COL2 + "<= '" + format1.format(cal.getTime()) + "'" +
                " ORDER BY " + TABLE_MONTHLY_INCOME_TRACKING_COL2 + " DESC " +
                " LIMIT 1;";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor.getDouble(0);
        else
            return -1;
    }

    public double getLatestMonthlyIncomeAmount(String loginID){
        //returns the latest monthly income for a user. returns -1 if no record found for that user
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TABLE_MONTHLY_INCOME_TRACKING_COL3 + " FROM " + TABLE_MONTHLY_INCOME_TRACKING +
                " WHERE " + TABLE_MONTHLY_INCOME_TRACKING_COL1 + "='" + loginID + "' AND " +
                " ORDER BY " + TABLE_MONTHLY_INCOME_TRACKING_COL2 + " DESC " +
                " LIMIT 1;";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor.getDouble(1);
        else
            return -1;
    }

    public Cursor getTransactions(String loginID, String date){
        //returns all the transactions for a day
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE " + TABLE_TRANSACTIONS_COL2 + "='" + loginID + "' AND " +
                TABLE_TRANSACTIONS_COL4 + "='" + Date.valueOf(date) + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor;
        else
            return null;
    }

    public double getSumOfTransactionsForDay(String loginID, String date){
        //returns all the transactions for a day
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT SUM(" + TABLE_TRANSACTIONS_COL5 + ") FROM " + TABLE_TRANSACTIONS +
                " WHERE " + TABLE_TRANSACTIONS_COL2 + "='" + loginID + "' AND " +
                TABLE_TRANSACTIONS_COL4 + "='" + date + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor.getDouble(0);
        else
            return 0;
    }

    public Cursor getTransactionsForMonth(String loginID, int month, int year){
        //returns all transactions(spendings) done in a month
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE " + TABLE_TRANSACTIONS_COL2 + "='" + loginID + "' AND " +
                " strftime('%m',datetime(" + TABLE_TRANSACTIONS_COL4 + ",'unixepoch'))=" + month + " AND " +
                " strftime('%Y',datetime(" + TABLE_TRANSACTIONS_COL4 + ",'unixepoch'))=" + year;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor;
        else
            return null;
    }

    public Cursor getTransactionsForCategoryForMonth(String loginID, int month, int year, int eCategoryID){
        //returns transactions for a specific category for a given month
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE " + TABLE_TRANSACTIONS_COL2 + "='" + loginID + "' AND " +
                " strftime('%m',datetime(" + TABLE_TRANSACTIONS_COL4 + ",'unixepoch'))=" + month + " AND " +
                " strftime('%Y',datetime(" + TABLE_TRANSACTIONS_COL4 + ",'unixepoch'))=" + year + " AND " +
                TABLE_TRANSACTIONS_COL3 + "=" + eCategoryID;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor;
        else
            return null;
    }

    public Cursor getAllTransactionsForCategory(String loginID, int eCategoryID){
        //returns all the transactions till date for a category
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE " + TABLE_TRANSACTIONS_COL2 + "='" + loginID + "' AND " +
                TABLE_TRANSACTIONS_COL3 + "=" + eCategoryID;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast() && cursor.getCount() > 0)
            return cursor;
        else
            return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public DailyExpenseReport getSavingDebtForMonth(String loginID , int month, int year){
        //this function is for report 1
        double[] dailyBalance = new double[31];
        double allowedExpense = 0;
        String[] days = new String[31];
        double expensesDone[] = new double[31];

        //get date formats and calendar instance
        SimpleDateFormat simpleDateFormatWithYear =  new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatWithoutYear = new SimpleDateFormat("dd-mmm");
        Calendar cal = Calendar.getInstance();

        //get daily allowed expense for that month
        allowedExpense = getMonthlyIncomeAmount(loginID,month,year);

        //generate report only if allowed expense is found (that is only when there is an entry for monthly expenses)
        if (allowedExpense!=-1){

            YearMonth yearMonthObject = YearMonth.of(year, month);
            int daysInMonth = yearMonthObject.lengthOfMonth();

            allowedExpense = allowedExpense / daysInMonth;          //divide by number of days in the month

            //get total expenses for each day in an array
            for (int i=0; i<expensesDone.length; i++){
                cal.set(year, month-1, i+1);
                expensesDone[i] = getSumOfTransactionsForDay(loginID, simpleDateFormatWithYear.format(cal.getTime()));
                days[i] = simpleDateFormatWithoutYear.format(cal.getTime());
            }

            //calculate daily savings/debt for the entire month
            for (int i=0; i<dailyBalance.length; i++){
                dailyBalance[i] = allowedExpense - expensesDone[i];
            }
        }

        //return the daily savings/debt array
        return new DailyExpenseReport(dailyBalance,days);
    }



}

