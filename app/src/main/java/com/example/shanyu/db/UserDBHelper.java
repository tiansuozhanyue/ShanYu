package com.example.shanyu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.shanyu.utils.LogUtil;
import com.hyphenate.chat.EMUserInfo;

import java.util.ArrayList;


public class UserDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "UserDBHelper";
    private static final String DB_NAME = "user.db";
    private static final int DB_VERSION = 2;
    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    private static final String TABLE_NAME = "emuser_info";

    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private UserDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    /**
     * 单例获取Helper对象
     *
     * @param context
     * @return
     */
    public static UserDBHelper getInstance(Context context) {
        if (mHelper == null)
            mHelper = new UserDBHelper(context);
        return mHelper;
    }

    /**
     * 通过读链接获取SQLiteDatabase
     *
     * @return
     */
    public SQLiteDatabase openReadLink() {
        if (mDB == null || mDB.isOpen() != true) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    /**
     * 通过写链接获取SQLiteDatabase
     *
     * @return
     */
    public SQLiteDatabase openWriteLink() {
        if (mDB == null || mDB.isOpen() != true) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    /**
     * 关闭数据库
     */
    public void closeLink() {
        if (mDB != null && mDB.isOpen() == true) {
            mDB.close();
            mDB = null;
        }
    }

    /**
     * 获取数据库名称
     *
     * @return
     */
    public String getDbName() {
        if (mHelper != null) {
            return mHelper.getDatabaseName();
        } else {
            return DB_NAME;
        }
    }

    /**
     * 只在第一次打开数据库时执行，再此可进行表结构创建的操作
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("UserDBHelper", "---->onCreate");

        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "nickName VARCHAR ," + "avatarUrl VARCHAR,"
                + "email VARCHAR ," + "phoneNumber VARCHAR ,"
                + "userId VARCHAR NOT NULL"
                + ");";
        db.execSQL(create_sql);
    }

    /**
     * 在数据库版本升高时执行，可以根据新旧版本号进行表结构变更处理
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //其实这里是根据oldVersion的不同进行字段的添加
        if (oldVersion == 1) {
            // TODO: 2018/11/20 这里一会儿进行测试，先创建的表没有phone和password字段，然后添加
            //Android 的 ALTER 命令不支持一次添加多列，只能分多次添加
            String alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "phone VARCHAR;";
            db.execSQL(alter_sql);
            alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "password VARCHAR;";
            db.execSQL(alter_sql);
        }
    }

    /**
     * 根据条件删除数据
     *
     * @param condition
     * @return
     */
    public int delete(String condition) {
        int count = mDB.delete(TABLE_NAME, condition, null);
        //null的位置，如果condition中用了？作为数值占位，比如"id>?",null要给new String[]{"2"}
        return count;
    }

    /**
     * 删除全部数据
     *
     * @return
     */
    public int deleteAll() {
        int count = mDB.delete(TABLE_NAME, "1=1", null);
        return count;
    }

    /**
     * 增加单条数据，其实就是包装了一下调用增加多条数据
     *
     * @param info
     * @return
     */
    public long insert(EMUserInfo info) {
        ArrayList<EMUserInfo> infoArray = new ArrayList<>();
        infoArray.add(info);
        return insert(infoArray);
    }

    /**
     * 根据条件进行查询
     *
     * @param userId
     * @return
     */
    public ArrayList<EMUserInfo> query(String userId) {
        String sql = String.format("select nickName,avatarUrl,email,phoneNumber,userId from %s where userId=%s;", TABLE_NAME, userId);
        ArrayList<EMUserInfo> infoArray = new ArrayList<>();
        Cursor cursor = mDB.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            for (; ; cursor.moveToNext()) {
                EMUserInfo info = new EMUserInfo();
                info.setNickName(cursor.getString(0));
                info.setAvatarUrl(cursor.getString(1));
                info.setEmail(cursor.getString(2));
                info.setPhoneNumber(cursor.getString(3));
                info.setUserId(cursor.getString(4));
                infoArray.add(info);
                if (cursor.isLast() == true) {
                    break;
                }
            }
        }
        cursor.close();
        return infoArray;
    }

    /**
     * 更新多条数据
     *
     * @param info
     * @param condition
     * @return
     */
    public int update(EMUserInfo info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("nickName", info.getNickName());
        cv.put("avatarUrl", info.getAvatarUrl());
        cv.put("email", info.getEmail());
        cv.put("phoneNumber", info.getPhoneNumber());
        cv.put("userId", info.getUserId());
        int count = mDB.update(TABLE_NAME, cv, condition, null);
        return count;
    }

    /**
     * 更新指定的数据条目
     *
     * @param info
     * @return
     */
    public int update(EMUserInfo info) {
        return update(info, "userId=" + info.getUserId());
    }

    /**
     * 增加多条数据
     *
     * @param infoArray
     * @return
     */
    public long insert(ArrayList<EMUserInfo> infoArray) {
        long result = -1;
        //循环全部数据
        for (int i = 0; i < infoArray.size(); i++) {
            EMUserInfo info = infoArray.get(i);
            //如果存在同名记录，就更新记录。注意条件语句的等号后面要用单引号括起来
            if (info.getUserId() != null && info.getUserId().length() > 0) {
                String condition = String.format("userId='%s'", info.getUserId());
                ArrayList<EMUserInfo> tempArray = query(info.getUserId());
                if (tempArray.size() > 0) {
                    update(info, condition);
//                    result = tempArray.get(0).getRowid();
                    continue;
                }
            }
            //如果不存在唯一性重复的记录，就插入新记录
            ContentValues cv = new ContentValues();
            cv.put("nickName", info.getNickName());
            cv.put("avatarUrl", info.getAvatarUrl());
            cv.put("email", info.getEmail());
            cv.put("phoneNumber", info.getPhoneNumber());
            cv.put("userId", info.getUserId());
            result = mDB.insert(TABLE_NAME, "", cv);
            //添加成功后返回行号，失败则返回-1
            if (result == -1) {
                return result;
            }
        }
        return result;
    }
}
