package com.heart.ticket.demo;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * About:
 * Other:
 * Created: wfli on 2023/6/12 17:21.
 * Editored:
 */
public class JsonStringDemo {

    public static final Logger log = LoggerFactory.getLogger(JsonStringDemo.class);

    public static void main(String[] args) {

        JSONObject request = new JSONObject();
        request.put("appId","UMS8btv3Uc5");
        request.put("method","com.ums. order. orderDispatch");
        request.put("requestId","2023061217300");
        request.put("sign","7E6D1CBF30670F6274EF0B1665D7F3D84A2FA32CE37DAD50B0AE92200E361747");
        request.put("timestamp","1686562270277");
        //request.put("reqDetail","");

        JSONObject reqDetail = new JSONObject();
        reqDetail.put("tuId", "cs1");
        reqDetail.put("channel", 1);
        reqDetail.put("orderId", "1100582860608869126");
        reqDetail.put("dispatchType", "000");
        //reqDetail.put("dispatchData", "111");


        JSONObject dispatchType = new JSONObject();
        dispatchType.put("logisticsStatus", "15");

        reqDetail.put("dispatchData", dispatchType);

        request.put("reqDetail", reqDetail);

        log.info("{}",request);


    }
}
