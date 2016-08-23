package com.example.mimi.onpounchgoal;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mimi.onpounchgoal.Fragment.AddItemFragment;
import com.example.mimi.onpounchgoal.Fragment.MyGoalFragment;
import com.example.mimi.onpounchgoal.data.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends BaseActivity
        implements MyGoalFragment.OnFragmentInteractionListener, AddItemFragment.OnFragmentInteractionListener {

    LinearLayout lv_main;
    Button btnToMainPage, btnToMyGoal;
    public static SQLiteDB myDB;
    FragmentTransaction transaction;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initSqlite();
        findViews();

    }

    private void initSqlite() {

        myDB = new SQLiteDB(this);
        int itemCount = myDB.getCount();
        if (itemCount == 0) {
            Item item = new Item("伏地挺身", "100", "下", new Date().toString(), 0);
//            item.setId(++itemId);
            String result = myDB.insert(item);
            if (result == "Success") {
                Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        } else {
//            Calendar c = Calendar.getInstance();
            Date today = new Date();
            String day = (String) android.text.format.DateFormat.format("dd", today);
            String mon = (String) android.text.format.DateFormat.format("MM", today);

            List<Item> items = new ArrayList<Item>();
            items = myDB.getAll();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                Date date = new Date(item.getUpdate_time());
                String i_day = (String) android.text.format.DateFormat.format("dd", date);
                String i_mon = (String) android.text.format.DateFormat.format("MM", date);
                if (date.before(today)) {
                    if (i_day.equals(day) && i_mon.equals(mon)) {
                        continue;
                    }
                    item.setDone(0);
                    item.setUpdate_time(today.toString());
                    myDB.update(item);
                }
//                if((String) android.text.format.DateFormat.format("dd", ))
            }

        }
    }

    private void findViews() {

        lv_main = (LinearLayout) findViewById(R.id.lv_main);

        btnToMainPage = (Button) findViewById(R.id.button1);
        btnToMyGoal = (Button) findViewById(R.id.button2);

        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button1:
                    case R.id.button2:
//                        Intent intent = new Intent();
//                        intent.setClass(MainActivity.this, MyGoalActivity.class);
//                        startActivityForResult(intent,100);
                    default:
                }
            }
        };

        btnToMainPage.setOnClickListener(btnClick);
        btnToMyGoal.setOnClickListener(btnClick);

        goHomePage();
    }

    public void goHomePage() {
        lv_main.setBackgroundResource(R.color.White);
        fm = this.getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Fragment newFragment = new MyGoalFragment();
        transaction = this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, newFragment);
        transaction.commit();
    }

    public void addFragment(Fragment newFragment) {
//        lv_main.setBackgroundResource(R.color.OpacityGray);

        transaction = this.getFragmentManager().beginTransaction();
        transaction.add(R.id.FragmentLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFragment(Fragment newFragment) {
//        lv_main.setBackgroundResource(R.color.White);
        transaction = this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, newFragment);
        //ft.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fm.getBackStackEntryCount() == 0) {
            lv_main.setBackgroundResource(R.color.white);
        }
    }

    public class MyDBHelper extends SQLiteOpenHelper {

        // 資料庫名稱
        public static final String DATABASE_NAME = "mydata.db";
        // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
        public static final int VERSION = 1;
        // 資料庫物件，固定的欄位變數
        private SQLiteDatabase database;

        // 建構子，在一般的應用都不需要修改
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
            super(context, name, factory, version);
        }

        // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
        public SQLiteDatabase getDatabase(Context context) {
            if (database == null || !database.isOpen()) {
                database = new MyDBHelper(context, DATABASE_NAME,
                        null, VERSION).getWritableDatabase();
            }

            return database;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQLiteDB.create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // 刪除原有的表格
            db.execSQL("DROP TABLE IF EXISTS " + SQLiteDB.tableName);
            // 呼叫onCreate建立新版的表格
            onCreate(db);
        }

    }

    public class SQLiteDB {

        private SQLiteDatabase db;
        private final int dbVersion = 1;
        public static final String tableName = "MyTable";

        public static final String create = "CREATE TABLE IF NOT EXISTS "
                + tableName + " ("
//                + "id" + " INTEGER PRIMARY KEY, "
                + "name" + " TEXT NOT NULL, "
                + "value" + " INTEGER NOT NULL, "
                + "unit" + " TEXT NOT NULL, "
                + "update_time" + " TEXT, "
                + "done" + " INTEGER "
                + ")";

        public SQLiteDB(Context context) {
            MyDBHelper dbHelper = new MyDBHelper(context, "DBHelper", null, dbVersion);
            db = dbHelper.getDatabase(context);
        }

        public Item getItem(String name) {

            Cursor cursor = db.query(
                    tableName, null, "name=?", new String[]{name}, null, null, null, null);

            cursor.moveToFirst();
            Item item = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(4));

            return item;
        }

        // 新增參數指定的物件
        public String insert(Item item) {
            try {
                String result = "";
                Cursor cursor = db.query(
                        tableName, null, "name=?", new String[]{item.getName()}, null, null, null, null);

                if (cursor.getCount() > 0) {
                    result = "已有此項目";
                    return result;
                }

                // 建立準備新增資料的ContentValues物件
                ContentValues cv = new ContentValues();

                // 加入ContentValues物件包裝的新增資料
                // 第一個參數是欄位名稱， 第二個參數是欄位的資料
//                cv.put("id", item.getId());
                cv.put("name", item.getName());
                cv.put("value", item.getValue());
                cv.put("unit", item.getUnit());
                cv.put("update_time", item.getUpdate_time());
                cv.put("done", item.getDone());

                long dbresult = db.insert(tableName, null, cv);
                if (dbresult != -1)
                    result = "Success";

                return result;
            } catch (Exception e) {

                return e.getMessage();
            }
        }

        public Boolean update(Item item) {
            try {
                ContentValues cv = new ContentValues();

                cv.put("value", item.getValue());
                cv.put("update_time", item.getUpdate_time());
                cv.put("done", item.getDone());
                long result = db.update(tableName, cv, "name='" + item.getName() + "'", null);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        // 讀取所有記事資料
        public List<Item> getAll() {
            List<Item> result = new ArrayList<>();
            Cursor cursor = db.query(
                    tableName, null, null, null, null, null, null, null);

            int row = cursor.getCount();

            cursor.moveToFirst();
            try {
                for (int i = 0; i < row; i++) {
                    Item item = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getInt(4));
//                item.setId(cursor.getInt(0));
                    result.add(item);
                    cursor.moveToNext();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            cursor.close();
            return result;
        }

        //刪除資料
        public boolean deleteItem(String name) {
            String where = "name = " + name;
            return db.delete(tableName, where, null) > 0;

        }

        // 取得資料數量
        public int getCount() {
            int result = 0;
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);

            if (cursor.moveToNext()) {
                result = cursor.getInt(0);
            }

            return result;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }


}
