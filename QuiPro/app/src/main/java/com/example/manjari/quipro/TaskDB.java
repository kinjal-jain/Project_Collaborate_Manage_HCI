package com.example.manjari.quipro;

/**
 * Created by Manjari on 4/19/2016.
 */
import android.provider.BaseColumns;

import java.util.Date;

public class TaskDB {

    public static final String DB_NAME = "com.example.task.db.dbt1";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "TaskDetails";

    public class Columns {
        public static final String TASKNAME= "task";
        public static final String TASKDESC="description";
        public static final String TASKDATE="date";
        public static final String _ID = BaseColumns._ID;
    }
}