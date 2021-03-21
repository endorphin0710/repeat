package com.behemoth.repeat.util;

public class Constants {

    /** Constants **/
    public static final int LIMIT_RECENT_MARKS = 20;

    /** 로그인 **/
    public static final String LOGIN_TYPE = "login_type";
    public static final String NAVER = "NAVER";
    public static final String NAVER_ID_PREFIX = "naver";
    public static final String KAKAO = "KAKAO";
    public static final String KAKAO_ID_PREFIX = "kakao";
    public static final int USER_TYPE_SOCIAL = 0;

    /** REQUEST CODE **/
    public static final int REQUEST_CODE_GALLERY = 0;
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CROP_IMAGE = 2;
    public static final int REQUEST_CODE_SEARCH = 3;
    public static final int REQUEST_RELOAD = 4;
    public static final int REQUEST_NICKNAME_CHANGE = 5;
    public static final int REQUEST_MYPAGE = 6;

    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 3;
    public static final int PERMISSION_CAMERA = 4;

    /** SHARED PREFERENCE **/
    public static final String SHARED_PREFERENCE_NAME = "shared_preference_repeat";
    public static final String USER_ID = "user_id";
    public static final String USER_NICKNAME = "user_nickname";

    public static final String DATA_CHANGED = "data_changed";
    public static final String REFRESH_MAIN = "refersh_main";
    public static final String REFRESH_MARK = "refresh_mark";
    public static final String REFRESH_RECENTS = "refresh_recents";

    /** MAIN **/
    public static final int CARD_COLUMN_COUNT = 2;
    public static final long MAX_UPLOAD_RETRY_MILLIS = 10 * 1000;

    /** IMAGE **/
    public static final String LABEL_IMAGE_URI = "imageUri";
    public static final String LABEL_CROPPED_IMAGE_URI = "croppedUri";
    public static final String LABEL_SEARCHED_TITLE = "searched_title";
    public static final String LABEL_SEARCHED_THUMBNAIL = "searched_thumbnail";

    /** LABEL **/
    public static final String LABEL_AVERAGE_SCORE = "AVR_SCORE";
    public static final String LABEL_MIN_SCORE = "MIN_SCORE";
    public static final String LABEL_MAX_SCORE = "MAX_SCORE";

}
