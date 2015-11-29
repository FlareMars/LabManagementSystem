package com.libmanagement.common.utils;

import com.jayway.jsonpath.JsonPath;
import com.libmanagement.common.exception.LMSServerException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static Log logger = LogFactory.getLog(JsonUtils.class);


    public static JSONObject parseJsonObject(String jsonStr){
//            logger.debug(jsonStr);
            if (!StringUtils.INSTANCE.isEmpty(jsonStr)) {

                return (JSONObject) JSONValue.parse(jsonStr);
            } else {
                return new JSONObject();
            }
    }

    public static JSONArray parseJsonArray(String jsonStr){
        if (!StringUtils.INSTANCE.isEmpty(jsonStr)) {
            return (JSONArray) JSONValue.parse(jsonStr);
        } else {
            return new JSONArray();
        }
    }

    public static String toJsonString(List list){
        if(list==null) {
            return null;
        }
        else {
            return JSONArray.toJSONString(list);
        }
    }

    public static String toJsonString(Map map){
        if(map==null) {
            return null;
        }
        else {
            return JSONObject.toJSONString(map);
        }
    }

    public static <T> T getAsObject(Object json, String name) {
       return JsonPath.read(json, name);
    }

    public static <T> T getAsObject(String json, String name) {
        return JsonPath.read(json, name);
    }

    public static Long getAsLong(String json, String name) {
        if(StringUtils.INSTANCE.isEmpty(json) || StringUtils.INSTANCE.isEmpty(name)){
            return null;
        }

        Object object = JsonPath.read(json, name);

        if(object == null) return null;

        if(object instanceof String){
            return Long.parseLong((String) object);
        }else if(object instanceof Integer){
            return ((Integer)object).longValue();
        }else if(object instanceof Long){
            return (Long)object;
        }else {
            throw new LMSServerException("参数错误");
        }
    }

    public static Long getAsLong(Object json, String name) {
        if(StringUtils.INSTANCE.isEmpty(json) || StringUtils.INSTANCE.isEmpty(name)){
            return null;
        }

        Object object = JsonPath.read(json, name);

        if(object == null) return null;

        if(object instanceof String){
            return Long.parseLong((String) object);
        }else if(object instanceof Integer){
            return ((Integer)object).longValue();
        }else if(object instanceof Long){
            return (Long)object;
        }else {
            throw new LMSServerException("参数错误");
        }
    }

    public static Integer getAsInteger(String json, String name) {
        if(StringUtils.INSTANCE.isEmpty(json) || StringUtils.INSTANCE.isEmpty(name)){
            return null;
        }

        Object object = JsonPath.read(json, name);

        if(object == null) return null;

        if(object instanceof String){
            return Integer.parseInt((String) object);
        }else if(object instanceof Integer){
            return (Integer)object;
        }else if(object instanceof Long){
            return ((Long)object).intValue();
        }else {
            throw new LMSServerException("参数错误");
        }
    }

    public static Integer getAsInteger(Object json, String name) {
        if(StringUtils.INSTANCE.isEmpty(json) || StringUtils.INSTANCE.isEmpty(name)){
            return null;
        }

        Object object = JsonPath.read(json, name);

        if(object == null) return null;

        if(object instanceof String){
            return Integer.parseInt((String) object);
        }else if(object instanceof Integer){
            return (Integer)object;
        }else if(object instanceof Long){
            return ((Long)object).intValue();
        }else {
            throw new LMSServerException("参数错误");
        }
    }

    public static String getAsString(Object json, String name) {

        if(StringUtils.INSTANCE.isEmpty(json) || StringUtils.INSTANCE.isEmpty(name)){
            return null;
        }

        Object object = JsonPath.read(json, name);

        if(object == null) return null;

        if(object instanceof String){
            return (String)object;
        }else if(object instanceof JSONObject){
            return ((JSONObject)object).toJSONString();
        }else if(object instanceof JSONArray){
            return ((JSONArray)object).toJSONString();
        }else return object.toString();
    }

    public static List<String> getAsListString(Object json, String name) {
        List<String> stringList = new ArrayList<>();

        try {
            Object object = JsonPath.read(json, name);

            if (object == null) return null;

            if (object instanceof String) {
                stringList.add((String) object);
            } else if (object instanceof JSONObject) {
                stringList.add(((JSONObject) object).toString());
            } else if (object instanceof JSONArray) {
                for (Object o : (JSONArray) object) {
                    stringList.add(o.toString());
                }
            }
        }catch (Exception e){}

        return stringList;
    }

    public static List<String> getAsListString(String json, String name) {
        List<String> stringList = new ArrayList<>();



        try {
            Object object = JsonPath.read(json, name);
            String s = null;
            if (object == null){

            }else if(object instanceof String) {
                s = (String)object;
                if(StringUtils.INSTANCE.notEmpty(s)) {
                    stringList.add(s);
                }
            } else if (object instanceof JSONObject) {
                s = ((JSONObject)object).toString();
                if(StringUtils.INSTANCE.notEmpty(s)) {
                    stringList.add(s);
                }
            } else if (object instanceof JSONArray) {
                for (Object o : (JSONArray) object) {
                    if(StringUtils.INSTANCE.notEmpty(o)) {
                        stringList.add(o.toString());
                    }
                }
            }
        }catch (Exception e){

        }

        return stringList;
    }


    public static String getAsString(String json, String name) {

        Object object = JsonPath.read(json, name);

        if(object == null) return null;

        if(object instanceof String){
            return (String)object;
        }else if(object instanceof JSONObject){
            return ((JSONObject)object).toJSONString();
        }else if(object instanceof JSONArray){
            return ((JSONArray)object).toJSONString();
        }else return object.toString();
    }

    public static void main(String[] args){
        String json = "{\n" +
                "    \"dockerImage\": \"rna:v1.5\",\n" +
                "    \"fileMappings\": [\n" +
                "        {\n" +
                "            \"source\": \"hdfs\",\n" +
                "            \"name\": \"RING_GIT_thout.bam\",\n" +
                "            \"path\": \"/test/RING_GIT_thout.bam\",\n" +
                "            \"size\": 641405627,\n" +
                "            \"fileType\": \"1f42aad018b14157aa8b996d724c2552\",\n" +
                "            \"tags\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"source\": \"hdfs\",\n" +
                "            \"name\": \"HA_thout.bam\",\n" +
                "            \"path\": \"/test/HA_thout.bam\",\n" +
                "            \"size\": 519827115,\n" +
                "            \"fileType\": \"1f42aad018b14157aa8b996d724c2552\",\n" +
                "            \"tags\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"source\": \"hdfs\",\n" +
                "            \"name\": \"Gmax_275_Wm82.a2.v1.gene.gtf\",\n" +
                "            \"path\": \"/pubdata/Glycine_max/275v1/Gmax_275_Wm82.a2.v1.gene.gtf\",\n" +
                "            \"size\": 168650977,\n" +
                "            \"fileType\": \"41fdd5c266584be78f74ab8d6bf0d3ad\",\n" +
                "            \"tags\": null\n" +
                "        }\n" +
                "    ],\n" +
                "    \"paramsFile\": \"task.gacfg\",\n" +
                "    \"dockerImageUri\": \"/docker/rna/rna_bate1.5.tar\",\n" +
                "    \"command\": \"perl /script/GeneExp.pl\",\n" +
                "    \"resultsFile\": \"result.cfg\",\n" +
                "    \"params\": {\n" +
                "        \"taskId\": \"df9fc1329cbd4ab9bc890055b75f4c7f\",\n" +
                "        \"sample_names\": [\n" +
                "            {\n" +
                "                \"bam\": \"RING_GIT_thout.bam\",\n" +
                "                \"sample_name\": \"RING_GIT\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"bam\": \"HA_thout.bam\",\n" +
                "                \"sample_name\": \"HA\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"taskStep\": 1,\n" +
                "        \"anno_gtf\": \"Gmax_275_Wm82.a2.v1.gene.gtf\",\n" +
                "        \"taskName\": \"基因表达水平分析\"\n" +
                "    },\n" +
                "    \"outputs\": [\n" +
                "        {\n" +
                "            \"source\": null,\n" +
                "            \"name\": \"readcount\",\n" +
                "            \"path\": \"4.GeneExprQuatification/readcount.xls\",\n" +
                "            \"size\": null,\n" +
                "            \"fileType\": null,\n" +
                "            \"tags\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"source\": null,\n" +
                "            \"name\": \"rpkmstat\",\n" +
                "            \"path\": \"4.GeneExprQuatification/rpkm.stat.xls\",\n" +
                "            \"size\": null,\n" +
                "            \"fileType\": null,\n" +
                "            \"tags\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"source\": null,\n" +
                "            \"name\": \"rpkm\",\n" +
                "            \"path\": \"4.GeneExprQuatification/rpkm.xls\",\n" +
                "            \"size\": null,\n" +
                "            \"fileType\": null,\n" +
                "            \"tags\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";
//        JSONArray jsonArray = JsonPath.read(json, "$.sample_names");
        JSONArray outPuts = JsonUtils.getAsObject(json, "$.outputs[*]");
//        Object[] objects = outPuts.toArray();
        for(Object o:outPuts){
            System.out.println(JsonUtils.getAsString(o,"$.path"));
        }

//            for (Object value : outPuts) {
//                System.out.println(outPuts.toString());
//                logger.debug(value.toString());
//                String path = JsonUtils.getAsString(value, "$.path");
//                logger.info("try to upload file " + path + " to hdfs workdir");
//
//            }

    }



}
