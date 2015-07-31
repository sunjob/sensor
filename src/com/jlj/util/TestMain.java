package com.jlj.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;

import com.jlj.vo.UserJson;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("系统管理".contains("超级"));
//		System.out.println(NumberUtils.isNumber("adfadf"));
		String ADD_URL="http://localhost:8088/sensor/cxf/rest/userservice/userlogin.json";
		System.out.println(TestMain.dopostit(ADD_URL));
	}
	
	
	public static String dopostit(String ADD_URL){
		String result="";
		try { 
            //创建连接 
            URL url = new URL(ADD_URL); 
            HttpURLConnection connection = (HttpURLConnection) url 
                    .openConnection(); 
            connection.setDoOutput(true); 
            connection.setDoInput(true); 
            connection.setRequestMethod("POST"); 
            connection.setUseCaches(false); 
            connection.setInstanceFollowRedirects(true); 
            connection.setRequestProperty("Content-Type", 
                    "application/json"); 

            connection.connect(); 

            //POST请求 
            DataOutputStream out = new DataOutputStream( 
                    connection.getOutputStream()); 
            JSONObject obj = new JSONObject(); 
            UserJson userJson = new UserJson();
            userJson.setUsername("admin");
            userJson.setPassword("admin");
//            obj.element("username", "admin"); 
//            obj.element("password", "admin"); 
            obj.element("userJson", userJson); 
            System.out.println(obj.toString());
            out.writeBytes(obj.toString()); 
            out.flush(); 
            out.close(); 

            //读取响应 
            BufferedReader reader = new BufferedReader(new InputStreamReader( 
                    connection.getInputStream())); 
            String lines; 
            StringBuffer sb = new StringBuffer(""); 
            while ((lines = reader.readLine()) != null) { 
                lines = new String(lines.getBytes(), "utf-8"); 
                sb.append(lines); 
            } 
            System.out.println(sb); 
            result = sb.toString();
            reader.close(); 
            // 断开连接 
            connection.disconnect(); 
        } catch (Exception e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
            return "";
        } 
        return result;

	}

}
