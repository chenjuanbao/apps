package com.mediwit.app.model.message;

import com.mediwit.app.constant.TableConstant;

public class MessageSql {
    public static final String updateNum="update "+TableConstant.TABLE_MESSAGE+" set num=? where id=?";
    public static final String updateTopTime="update "+TableConstant.TABLE_MESSAGE+" set topTime=? where id=?";
}
