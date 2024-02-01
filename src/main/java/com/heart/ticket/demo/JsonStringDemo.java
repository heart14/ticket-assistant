package com.heart.ticket.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * About:
 * Other:
 * Created: wfli on 2023/6/12 17:21.
 * Editored:
 */
@Slf4j
public class JsonStringDemo {

    public static void main(String[] args) {

//        JSONObject request = new JSONObject();
//        request.put("appId","UMS8btv3Uc5");
//        request.put("method","com.ums. order. orderDispatch");
//        request.put("requestId","2023061217300");
//        request.put("sign","7E6D1CBF30670F6274EF0B1665D7F3D84A2FA32CE37DAD50B0AE92200E361747");
//        request.put("timestamp","1686562270277");
//        //request.put("reqDetail","");
//
//        JSONObject reqDetail = new JSONObject();
//        reqDetail.put("tuId", "cs1");
//        reqDetail.put("channel", 1);
//        reqDetail.put("orderId", "1100582860608869126");
//        reqDetail.put("dispatchType", "000");
//        //reqDetail.put("dispatchData", "111");
//
//
//        JSONObject dispatchType = new JSONObject();
//        dispatchType.put("logisticsStatus", "15");
//
//        reqDetail.put("dispatchData", dispatchType);
//
//        request.put("reqDetail", reqDetail);
//
//        log.info("{}",request);


//        JSONObject o1 = new JSONObject();
//
//        JSONObject o2 = new JSONObject();
//        o2.put("groupId", "aaa");
//
//        JSONArray data = new JSONArray();
//        JSONObject d1 = new JSONObject();
//        d1.put("title", "weixin");
//        d1.put("index", "1");
//        JSONObject d2 = new JSONObject();
//        d2.put("title", "mali");
//        d2.put("index", "2");
//
//        data.add(d1);
//        data.add(d2);
//
//        o2.put("data", data);
//
//
//        o1.put("transData", o2.toJSONString());
//        System.out.println(o1);
//
//
//        JSONObject j = new JSONObject();
//        j.put("groupId", "aa");
//        j.put("data", data);
//        System.out.println(j);


        List<List<Map<String, String>>> l1 = new ArrayList<>();
        List<Map<String, String>> l2 = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("k1", "aaa");
        map.put("k2", "bbb");
        l2.add(map);

        l1.add(l2);

        log.info("{}", l1);

        List<Map<String, String>> mapList = l1.get(0);
        Map<String, String> stringMap = mapList.get(0);
        stringMap.put("k1", "666");

        log.info("{}", l1);

    }
}
