package com.sunhan.sharing_teaching_parents.util;

/**
 * Created by 孙汉 on 2019-07-16/22/46
 */
public class Constant {
    public static int IS_LOGIN = 0;//0表示未登录，1表示已登录
    public static String TOKEN;

    public static String USER_NAME;

    public final static int PERSONAL_LOGIN = 0;
    public final static int ORGANIZATION_LOGIN = 1;

    public static String LOGIN_URL = "http://172.31.3.137:8080/SharingTeaching/LoginServlet";//需要根据自己的情况进行更改
    public static String GET_TEACHER_LIST_URL = "http://172.31.3.201:8080/SharingTeaching/TeacherListServlet";//老师列表

    public static final String ImageUpload_URL = "http://172.31.3.137:8080/SharingTeaching";
}
