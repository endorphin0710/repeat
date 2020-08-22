package com.behemoth.repeat.tasks;

import com.behemoth.repeat.auth.User;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.LogUtil;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RetrieveNaverUserInfo implements Runnable{

    private static final String TAG = "RetrieveNaverUserInfo";
    private String token;

    public RetrieveNaverUserInfo(String token){
        LogUtil.d(TAG, "token: " + token);
        this.token = token;
    }

    @Override
    public void run() {
        String header = "Bearer " + token;
        String apiURL = "https://openapi.naver.com/v1/nid/me";
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);
        LogUtil.d(TAG, "responseBody: " + responseBody);

        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject response = (JSONObject)jsonObject.get("response");
            String id = Constants.NAVER_ID_PREFIX + response.get("id").toString();
            LogUtil.d(TAG, "naverId: " + id);

            /** firebase **/
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("user").child(id).setValue(new User(id, Constants.USER_TYPE_SOCIAL));

            /** sharedPreference **/
            SharedPreference.getInstance().putString(Constants.LOGIN_TYPE, Constants.NAVER);
            SharedPreference.getInstance().putString(Constants.USER_ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

}
