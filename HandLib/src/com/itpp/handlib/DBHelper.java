package com.itpp.handlib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "elibrary.db";

	public DBHelper(Context ctx) {
		super(ctx, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);

	}

	private void createTables(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		String category_table_sql = "create table " + "category" + " ( "
				+ "_id" + " integer  primary key autoincrement ," + "cat_name"
				+ " TEXT," + "cat_abbr" + " TEXT)";

		String books_table_sql = "create table " + "books" + "(" + "_id"
				+ " integer  primary key autoincrement , " + "category"
				+ " INTEGER," + "book_name" + " TEXT ," + "book_abbr"
				+ " TEXT," + " book_avl " + " INTEGER," + "loc_url" + " TEXT,"
				+ "rem_url " + " TEXT)";

		String insert_into_category_row_i = "insert into " + "category  "
				+ " values( null," + "\'Mazgines\', " + "\'Magzines\') ";
		String insert_into_category_row_ii = "insert into " + "category  "
				+ " values( null," + "\'Kabita\', " + "\'Kabita\') ";
		String insert_into_category_row_iii = "insert into " + "category  "
				+ " values( null," + "\'Story\', " + "\'Story\') ";
		String insert_into_category_row_iv = "insert into " + "category  "
				+ " values( null," + "\'Jibanica\', " + "\'Jibanica\') ";

		String insert_into_books_uc_i = "insert into books " + "values( "
				+ "null," + "1," + "\'Sansar\'," + "\'Feb Edition-2014\',"
				+ "0," + "\'/sdcard/OP/Sansar_Feb2014.pdf\',"
				+ "\'http://odishapublication.com/Sansar/Sansar_Feb2014.pdf\')";
		String insert_into_books_uc_ii = "insert into books " + "values( "
				+ "null," + "1," + "\'Sansar\'," + "\'Jan Edition-2014\',"
				+ "0," + "\'/sdcard/OP/Sansar_Jan2014.pdf\',"
				+ "\'http://odishapublication.com/Sansar/Sansar_Jan2014.pdf\')";

		String insert_into_books_i = "insert into books " + "values( "
				+ "null," + "1," + "\'Sansar\'," + "\'Dec Edition-2013\',"
				+ "0," + "\'/sdcard/OP/Sansar_Dec2013.pdf\',"
				+ "\'http://odishapublication.com/Sansar/Sansar_Dec2013.pdf\')";
		String insert_into_books_ii = "insert into books " + "values( "
				+ "null," + "1," + "\'Sansar\'," + "\'Nov Edition-2013\',"
				+ "0," + "\'/sdcard/OP/Sansar_Nov2013.pdf\',"
				+ "\'http://odishapublication.com/Sansar/Sansar_Dec2013.pdf\')";
		String insert_into_books_iii = "insert into books " + "values( "
				+ "null," + "1," + "\'Sansar\'," + "\'Oct Edition-2013\',"
				+ "0," + "\'/sdcard/OP/Sansar_Oct2013.pdf\',"
				+ "\'http://odishapublication.com/Sansar/Sansar_Oct2013.pdf\')";
		String insert_into_books_iv = "insert into books "
				+ "values( "
				+ "null,"
				+ "1,"
				+ "\'Sansar\',"
				+ "\'Sept Edition-2013\',"
				+ "0,"
				+ "\'/sdcard/OP/Sansar_Sept2013.pdf\',"
				+ "\'http://odishapublication.com/Sansar/Sansar_Sept2013.pdf\')";

		String insert_into_books_v = "insert into books "
				+ "values( "
				+ "null,"
				+ "2,"
				+ "\'Jharapatra Ra Silalipi\',"
				+ "\'Jharapatra Ra Silalipi\',"
				+ "0,"
				+ "\'/sdcard/OP/Jharapatra_Ra_Silalipi.pdf\',"
				+ "\'http://odishapublication.com/Books/Jharapatra_Ra_Silalipi.pdf\')";
		String insert_into_books_vi = "insert into books "
				+ "values( "
				+ "null,"
				+ "2,"
				+ "\'Chandadhua Akash\',"
				+ "\'Chandadhua Akash\',"
				+ "0,"
				+ "\'/sdcard/OP/Jharapatra_Ra_Silalipi.pdf\',"
				+ "\'http://odishapublication.com/Books/Chandadhua_Akash.pdf\')";
		
		String insert_into_books_vii = "insert into books " + "values( "
				+ "null," + "3," + "\'Purnahuti\'," + "\'Purnahuti\'," + "0,"
				+ "\'/sdcard/OP/Purnahuti.pdf\',"
				+ "\'http://odishapublication.com/Books/Purnahuti.pdf\')";

		String insert_into_books_viii = "insert into books " + "values( "
				+ "null," + "4," + "\'Matiru Akash\'," + "\'Matiru Akash\',"
				+ "0," + "\'/sdcard/OP/Matiru_Akash.pdf\',"
				+ "\'http://odishapublication.com/Books/Matiru_Akash.pdf\')";

		String insert_into_books_ix = "insert into books " + "values( "
				+ "null," + "4," + "\'Patha Asaranti\',"
				+ "\'Patha Asaranti\'," + "0,"
				+ "\'/sdcard/OP/Patha_Asaranti.pdf\',"
				+ "\'http://odishapublication.com/Books/Patha_Asaranti.pdf\')";

		try {
			database.execSQL(category_table_sql);
			database.execSQL(books_table_sql);
			database.execSQL(insert_into_category_row_i);
			database.execSQL(insert_into_category_row_ii);
			database.execSQL(insert_into_category_row_iii);
			database.execSQL(insert_into_category_row_iv);

			database.execSQL(insert_into_books_uc_i);
			database.execSQL(insert_into_books_uc_ii);
			database.execSQL(insert_into_books_i);
			database.execSQL(insert_into_books_ii);
			database.execSQL(insert_into_books_iii);
			database.execSQL(insert_into_books_iii);
			database.execSQL(insert_into_books_iv);

			database.execSQL(insert_into_books_v);
			database.execSQL(insert_into_books_vi);
			
			database.execSQL(insert_into_books_vii);

			database.execSQL(insert_into_books_viii);
			database.execSQL(insert_into_books_ix);

			Log.d("table created ", "Table created!");

		} catch (Exception ex) {
			Log.d("string", "Error in DBHelper.onCreate() : " + ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
}
