数据库访问链接服务
//================================获取数据库操作对象方法============================================
DatabaseHelper dataBaseHelper=DatabaseHelper.getInst(this.getApplicationContext());
SQLiteDatabase db = dataBaseHelper.getWritableDatabase();