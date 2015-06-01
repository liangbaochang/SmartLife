package com.laughingFace.microWash.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.laughingFace.microWash.MyApplication;

/**
 * Created by mathcoder23 on 15-5-29.
 */
public class FileSharePreference {

    public static void saveData(Context context,String filename,String content)
    {
        //指定操作的文件名称
        SharedPreferences share = context.getSharedPreferences(filename,Context.MODE_APPEND);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("data", content);
        edit.commit();  //保存数据信息

    }
    public static String LoadData(Context context,String filename) {
        //指定操作的文件名称
        SharedPreferences share = context.getSharedPreferences(filename, Context.MODE_APPEND);
       return share.getString("data","");
    }
}
