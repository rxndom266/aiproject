package com.example.aichatbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import org.jetbrains.annotations.NotNull;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChatBot.db";
    public static final String USERS = "USERS";
    //public static final String CONVERSATION = "CONVERSATION";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String MEMBER1 = "member1";
    public static final String MEMBER2 = "member2";
    public static final String REL1 = "relation1";
    public static final String REL2 = "relation2";
    public static final String PHONE1 = "phone1";
    public static final String PHONE2 = "phone2";
    public static final String PASSWORD = "password";
    //public static final String QUESTION = "question";
    //public static final String RESPONSE = "response";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.d("DBHelper", "Creating table");
        db.execSQL(
                "create table USERS " +
                        "(name text primary key, address text, phone text, member1 text, relation1 text, phone1 text, member2 text, relation2 text, phone2 text,  password text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+USERS);
    }

    public boolean addUser (String name, String address, String phone, String member1, String relation1, String phone1, String member2, String relation2 , String  phone2,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        {
            cv.put(NAME, name);
            cv.put(ADDRESS, address);
            cv.put(PHONE, phone);
            cv.put(MEMBER1, member1);
            cv.put(REL1, relation1);
            cv.put(PHONE1, phone1);
            cv.put(MEMBER2, member2);
            cv.put(REL2, relation2);
            cv.put(PHONE2, phone2);
            cv.put(PASSWORD, password);
        }
        Cursor cursor = db.query(
                USERS,   // The table to query
                new String[]{NAME},
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );
        if (cursor.moveToNext()) {
            return false;
        } else {
            db.insert("USERS", null, cv);
            return true;
        }
    }
    public boolean checkUniqueUser(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USERS,   // The table to query
                new String[]{NAME},
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );
        if (cursor.moveToNext()) {
            Log.d("USER ALREADY EXISTS ", cursor.getString(cursor.getColumnIndex(NAME)));
            return false;
        }
        else
            return true;
    }
    public boolean loginSuccess(String name, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USERS,   // The table to query
                new String[]{NAME, PASSWORD},
                "name = ? and password = ?",
                new String[]{name, password},
                null,
                null,
                null
        );
        if (cursor.moveToNext()) {
            Log.d("LOGIN SUCCESS ", cursor.getString(cursor.getColumnIndex(NAME)));
            return true;
        }
        else return false;
    }
    public String getAddress(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USERS,   // The table to query
                new String[]{ADDRESS},
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );
        cursor.moveToNext();
        String profession = cursor.getString(cursor.getColumnIndex(ADDRESS));
        return profession;
    }
    public String getMember(String name, String relation){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USERS,   // The table to query
                new String[]{MEMBER1, REL1, MEMBER2, REL2},
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );
        cursor.moveToNext();
        String relation1 = cursor.getString(cursor.getColumnIndex(REL1));
        String relation2 = cursor.getString(cursor.getColumnIndex(REL2));
        String member1 = cursor.getString(cursor.getColumnIndex(MEMBER1));
        String member2 = cursor.getString(cursor.getColumnIndex(MEMBER2));
        if(relation.equals(relation1)) return member1;
        else if(relation.equals(relation2)) return member2;
        else return "NOT FOUND";
    }
    public String getMemberPhone(String name, String member) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USERS,   // The table to query
                new String[]{MEMBER1, MEMBER2, PHONE1, PHONE2},
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );
        cursor.moveToNext();
        String phone2 = cursor.getString(cursor.getColumnIndex(PHONE2));
        String phone1 = cursor.getString(cursor.getColumnIndex(PHONE1));
        String member1 = cursor.getString(cursor.getColumnIndex(MEMBER1));
        String member2 = cursor.getString(cursor.getColumnIndex(MEMBER2));
        if (member.equals(member1)) return phone1;
        else if (member.equals(member2)) return phone2;
        else return "NOT FOUND";
    }
    /*public boolean addConv(String question, String response){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        {
            cv.put(QUESTION, question);
            cv.put(RESPONSE, response);
        }
        db.insert("USERS", null, cv); return true;
    }
    public ArrayList<String> getAllUsers () {
        ArrayList<String> userList = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from USERS", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            userList.add(res.getString(res.getColumnIndex(USERNAME)));
            res.moveToNext();
        }
        return userList;
    }


    //functions that work on categories
    public ArrayList<String> getCategories(String username){
        ArrayList<String> catList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "CATEGORIES",   // The table to query
                new String[]{USERNAME, CATEGORY},
                "username = ? ",
                new String[] {username},
                null,
                null,
                null
        );
        while(cursor.moveToNext())
            catList.add(cursor.getString(cursor.getColumnIndex(CATEGORY)));
        return catList;
    }
    public ArrayList<String> getAlikeCategories(String username, String query){
        ArrayList<String> catList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "CATEGORIES",   // The table to query
                new String[]{CATEGORY},
                "username = ? AND UPPER(category) LIKE ? ",
                new String[] {username, "%"+query.toUpperCase()+"%"},
                null,
                null,
                null
        );
        while(cursor.moveToNext())
            catList.add(cursor.getString(cursor.getColumnIndex(CATEGORY)));
        return catList;
    }
    public boolean addCategory(String username, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        {
            cv.put(USERNAME, username);
            cv.put(CATEGORY, category);
        }
        Cursor cursor = db.query(
                CATEGORIES,   // The table to query
                new String[]{CATEGORY},
                "username = ? AND category = ?",
                new String[] {username, category},
                null,
                null,
                null
        );
        if(cursor.moveToFirst())
            return false;
        else {
            db.insert(CATEGORIES, null, cv);
            return true;
        }
    }
    public boolean editCategory(String username, String newCategory, String oldCategory){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CATEGORIES", "username = ? AND category = ?", new String[]{username, oldCategory});
        boolean bool = addCategory(username, newCategory);
        return bool;
    }
    public boolean deleteCategory(String username, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CATEGORIES","username = ? AND category = ?",new String[] { username, category });
        String itemArray[] = getItemArray(username, category);
        for(String item: itemArray)
            deleteItem(username, category, item);
        return true;
    }

    //functions that work on items
    public boolean addItem(String username, String category, String item, double price, Integer qty, String unit, String imageUri){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        {
            cv.put(USERNAME, username);
            cv.put(CATEGORY, category);
            cv.put(ITEM, item);
            cv.put(PRICE, price);
            cv.put(QTY, qty);
            cv.put(UNIT, unit);
            cv.put(IMAGE, imageUri);
        }
        Cursor cursor = db.rawQuery("SELECT item FROM ITEMS WHERE username= ? AND category = ? AND item=?", new String[]{username, category, item});
        if(cursor.moveToFirst())
            return false;
        else {
            db.insert(ITEMS, null, cv);
            return true;
        }
    }
    public boolean deleteItem(String username, String category, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ITEMS,"username = ? AND item = ? AND category=?",new String[] { username, item, category });
        return true;
    }
    public ArrayList<String> getAlikeItemList(String username, String query){
        ArrayList<String> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                true,
                ITEMS,   // The table to query
                new String[]{ITEM},
                "username = ? AND UPPER(item) LIKE ?",
                new String[]{username, "%"+query.toUpperCase()+"%"},
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex(ITEM));
            Log.d("MATCHED ITEM", itemName);
            itemList.add(itemName);
        }
        return itemList;
    }
    public HashMap<String, String> getAlikeItemMap(String username, String query) {
        HashMap<String, String> itemMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ITEMS,   // The table to query
                new String[]{ITEM, CATEGORY},
                "username = ? AND UPPER(item) LIKE ?",
                new String[]{username, "%"+query.toUpperCase()+"%"},
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex(ITEM));
            String itemCat = cursor.getString(cursor.getColumnIndex(CATEGORY));
            itemMap.put(itemName, itemCat);
        }
        return itemMap;
    }
    public boolean editItem(String username, String category, String oldItem, String newItem, double price, Integer qty, String unit, String imageUri){
        SQLiteDatabase db = this.getWritableDatabase();
        deleteItem(username, category, oldItem);
        boolean bool = addItem(username, category, newItem, price, qty, unit, imageUri);
        return bool;
    }
    public ArrayList<String> getItemData(String username, String category, String item){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ITEMS,   // The table to query
                new String[]{PRICE, QTY, UNIT, IMAGE},
                "username = ? AND item = ? AND category = ?",
                new String[] {username, item, category},
                null,
                null,
                null
        );
        ArrayList<String> data = new ArrayList<>();
        while (cursor.moveToNext())
        {
            String price = cursor.getString(cursor.getColumnIndex(PRICE));
            String qty = cursor.getString(cursor.getColumnIndex(QTY));
            String unit = cursor.getString(cursor.getColumnIndex(UNIT));
            String image = cursor.getString(cursor.getColumnIndex(IMAGE));
            data.add(price);
            data.add(qty);
            data.add(unit);
            data.add(image);
        }
        return data;
    }
    public HashMap<String, ArrayList<String>> getItemMap(String username, String category) {
        HashMap<String, ArrayList<String>> itemMap = new HashMap<>();
        ArrayList<String> itemData;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                ITEMS,   // The table to query
                new String[]{ITEM},
                "username = ? AND category= ?",
                new String[]{username, category},
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex(ITEM));
            itemData = getItemData(username, category, itemName);
            itemMap.put(itemName, itemData);
        }
        return itemMap;
    }
    public String[] getItemArray(String username, String category){
        ArrayList<String> itemList = new ArrayList<>();
        String[] itemArray;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                true,
                ITEMS,   // The table to query
                new String[]{ITEM},
                "username = ? AND category = ?",
                new String[]{username, category},
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex(ITEM));
            itemList.add(itemName);
        }
        itemArray = new String[itemList.size()];
        itemList.toArray(itemArray);
        return itemArray;
    }


    //functions that work on reminders
    public boolean setReminder(String username, String item, String name, String time, String date, int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        {
            cv.put(USERNAME, username);
            cv.put(ITEM, item);
            cv.put(AL_NAME, name);
            cv.put(AL_TIME, time);
            cv.put(AL_DATE, date);
            cv.put(AL_ID, ID);
        }
        Cursor cursor = db.rawQuery("SELECT item FROM REMINDERS WHERE username = ? AND item = ?", new String[]{item, name});
        if(cursor.moveToFirst())
            return false;
        else {
            db.insert(REMINDERS, null, cv);
            return true;
        }
    }
    public ArrayList<String> getReminderData(String username, String item){
        ArrayList<String> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                true,
                REMINDERS,   // The table to query
                new String[]{AL_NAME, AL_TIME, AL_DATE, AL_ID},
                "username = ? AND item = ?",
                new String[]{item},
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(AL_NAME));
            dataList.add(name);
            String time = cursor.getString(cursor.getColumnIndex(AL_TIME));
            dataList.add(time);
            String date = cursor.getString(cursor.getColumnIndex(AL_DATE));
            dataList.add(date);
            int id = cursor.getInt(cursor.getColumnIndex(AL_ID));
            dataList.add(String.valueOf(id));
            Log.d("MATCHED NAME", name);
        }
        return dataList;
    }
    public ArrayList<String> getRemList(String username) {
        ArrayList<String> remList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                true,
                REMINDERS,   // The table to query
                new String[]{ITEM},
                "username = ?",
                new String[]{username},
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndex(ITEM));
            remList.add(item);
        }
        return remList;
    }
    public HashMap<String,ArrayList<String>> getReminderMap(String username){
        ArrayList<String> remList = new ArrayList<>();
        HashMap<String, ArrayList<String>> remMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                true,
                REMINDERS,   // The table to query
                new String[]{ITEM},
                "username = ? ",
                new String[]{username},
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndex(ITEM));
            remList.add(item);
            ArrayList<String> remData = getReminderData(username, item);
            remMap.put(item, remData);
        }
        return remMap;
    }*/
}