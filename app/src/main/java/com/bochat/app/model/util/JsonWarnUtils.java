package com.bochat.app.model.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 2019/6/13
 * Author LDL
 **/
public class JsonWarnUtils {

    private static List<String> keys=new ArrayList<>();
    private static StringBuffer stringBuffer=new StringBuffer();

    public static void analysisJson(boolean detailPrint,String json){
        try{
            stringBuffer.append(",\nJson模型开始===========================================================\n");
            firstAnalysis(detailPrint,json);
            stringBuffer.append("Json模型结束===========================================================\n");
            Log.i("=====", stringBuffer.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void firstAnalysis(boolean detailPrint,String json){
        try{
            JSONObject jsonObject=new JSONObject(json);
            Iterator iterator=jsonObject.keys();

            while (iterator.hasNext()){
                String key= (String) iterator.next();
                if(key.equals("msg")){
                    Log.i("=====","======msg");
                }
                keys.add(key);
                stringBuffer.append("key ："+key);
                Object object=jsonObject.opt(key);
                analysisObject(detailPrint,object);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void analysisObject(boolean detailPrint,Object object){
        if(object instanceof Integer){
            stringBuffer.append("\t\tvalue(Integer)："+object.toString()+"\n");
        }else if(object instanceof Long){
            stringBuffer.append("\t\tvalue(Long)："+object.toString()+"\n");
        }else if(object instanceof String){
            stringBuffer.append("\t\tvalue(String)："+object.toString()+"\n");
        }else if(object instanceof Boolean){
            stringBuffer.append("\t\tvalue(Boolean)："+object.toString()+"\n");
        }else if(object instanceof Double){
            stringBuffer.append("\t\tvalue(Double)："+object.toString()+"\n");
        }else if(object instanceof Float){
            stringBuffer.append("\t\tvalue(Float)："+object.toString()+"\n");
        }else if(object instanceof BigDecimal){
            stringBuffer.append("\t\tvalue(BigDecimal)："+object.toString()+"\n");
        }else if(object instanceof JSONArray){
            if(detailPrint){
                stringBuffer.append("\t\t该value为数组(\t" + object.toString() + "\t)\n数组解析后模型：\n");
            }else {
                stringBuffer.append("\t\t该value为数组\n数组解析后模型：\n");
            }
            JSONArray jsonArray= (JSONArray) object;
            for(int i=0;i<jsonArray.length();i++){
                stringBuffer.append("\t\t(第"+(i+1)+"个Entity)\n");
                JSONObject jsonObject=jsonArray.optJSONObject(i);
                firstAnalysis(detailPrint,jsonObject.toString());
            }
        }else if(object instanceof JSONObject){
            if(detailPrint){
                stringBuffer.append("\t\tvalue(Entity)：" + object.toString() + "\n");
            }else {
                stringBuffer.append("\t\tvalue(Entity)：\n");
            }
            JSONObject jsonObject= (JSONObject) object;
            firstAnalysis(detailPrint,jsonObject.toString());
        }else{
            stringBuffer.append("\t\tvalue(未知类型)："+object.toString()+"\n");
        }
    }

}
