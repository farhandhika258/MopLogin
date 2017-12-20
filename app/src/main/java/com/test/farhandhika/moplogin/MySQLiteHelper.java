package com.test.farhandhika.moplogin;

/**
 * Created by farhandhika on 20/12/17.
 */
import java.util.LinkedList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "BookDB";

    public MySQLiteHelper(MainActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "access_token STRING, " + ")";
                //"refresh_token STRING "

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_BOOKS = "books";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ACCTOKEN = "access_token";
   // private static final String KEY_EXPIRES = "expires_in";
    //private static final String KEY_TOKENTYPE = "token_type";
    //private static final String KEY_SCOPE = "scope";
    //private static final String KEY_REFTOKEN = "refresh_token";



    private static final String[] COLUMNS = {KEY_ACCTOKEN};

    public void addBook(Book book){
        Log.d("addBook", book.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ACCTOKEN, book.getAcctoken()); // get title
        //values.put(KEY_EXPIRES, book.getExpires()); // get author
        //values.put(KEY_TOKENTYPE, book.getTokentype()); // get author
        //values.put(KEY_SCOPE, book.getScope()); // get author
        //values.put(KEY_REFTOKEN, book.getReftoken()); // get author




        // 3. insert
        db.insert(TABLE_BOOKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Book getBook(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_BOOKS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setAcctoken(cursor.getString(1));
        //book.setExpires(cursor.getString(2));
        //book.setTokentype(cursor.getString(3));
        //book.setScope(cursor.getString(4));
        //book.setReftoken(cursor.getString(5));

        Log.d("getBook("+id+")", book.toString());

        // 5. return book
        return book;
    }

    // Get All Books
    public List<Book> getAllBooks() {
        List<Book> books = new LinkedList<Book>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BOOKS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setAcctoken(cursor.getString(1));
                //book.setExpires(cursor.getString(2));
                //book.setTokentype(cursor.getString(3));
                //book.setScope(cursor.getString(4));
                //book.setReftoken(cursor.getString(5));
                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        // return books
        return books;
    }

    // Updating single book
    public int updateBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("access_token", book.getAcctoken()); // get title
        //values.put("expires_in", book.getExpires());
        //values.put("token_type", book.getTokentype());
        //values.put("scope", book.getScope());
        //values.put("refresh_token", book.getReftoken());




        // 3. updating row
        int i = db.update(TABLE_BOOKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single book
    public void deleteBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOOKS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(book.getId()) });

        // 3. close
        db.close();

        Log.d("deleteBook", book.toString());

    }
}