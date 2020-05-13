package com.inqube.aamarmedic.util;

import android.content.Context;

import java.util.HashMap;

public class ResponseCategory {

    private Context context;
    private String proper_message;
    private HashMap<Integer, String> map;

    public ResponseCategory(Context context) {
        this.context = context;
        setErrorMap();
    }

    private void setErrorMap(){
        map=new HashMap<Integer, String>();
        map.put(400,"Bad Request");
        map.put(401,"Unauthorized");
        map.put(402,"Payment Required");
        map.put(403,"Forbidden");
        map.put(404,"Not Found");
        map.put(405,"Method Not Allowed");
        map.put(406,"Not Acceptable");
        map.put(407,"Proxy Authentication Required");
        map.put(408,"Request Timeout");
        map.put(409,"Conflict");
        map.put(410,"Gone");
        map.put(411,"Length Required");
        map.put(412,"Precondition Failed");
        map.put(413,"Payload Too Large");
        map.put(414,"Request-URI Too Long");
        map.put(415,"Unsupported Media Type");
        map.put(416,"Requested Range Not Satisfiable");
        map.put(417,"Expectation Failed");
        map.put(418,"I'm a teapot");
        map.put(421,"Misdirected Request");
        map.put(422,"Unprocessable Entity");
        map.put(423,"Locked");
        map.put(424,"Failed Dependency");
        map.put(426,"Upgrade Required");
        map.put(428,"Precondition Required");
        map.put(429,"Too Many Requests");
        map.put(431,"Request Header Fields Too Large");
        map.put(444,"Connection Closed Without Response");
        map.put(451,"Unavailable For Legal Reasons");
        map.put(499,"Client Closed Request");

        map.put(500,"Internal Server Error");
        map.put(501,"Not Implemented");
        map.put(502," Bad Gateway");
        map.put(503,"Service Unavailable");
        map.put(504,"Gateway Timeout");
        map.put(505,"HTTP Version Not Supported");
        map.put(506,"Variant Also Negotiates");
        map.put(507,"Insufficient Storage");
        map.put(508,"Loop Detected");
        map.put(510,"Not Extended");
        map.put(511,"Network Authentication Required");
        map.put(599,"Network Connect Timeout Error");

        map.put(200,"OK");
        map.put(201,"Created");
        map.put(202,"Accepted");
        map.put(203,"Non-authoritative Information");
        map.put(204,"No Content");
        map.put(205,"Reset Content");
        map.put(206,"Partial Content");
        map.put(207,"Multi-Status");
        map.put(208,"Already Reported");
        map.put(226,"IM Used");
        map.put(999,"CHAYAN JANE");

    }


    public ReturnResponse checkErrorCode(int response_key) {
        ReturnResponse returnResponse = null;
        String message=map.get(response_key);
        if(response_key>=200 && response_key<=226){
           returnResponse=new ReturnResponse(true,message,response_key);
        }else{
            returnResponse=new ReturnResponse(false,message,response_key);
        }
        return returnResponse;
        }
    }



