package com.heart.ticket.service.mt;

import com.meituan.sdk.DefaultMeituanClient;
import com.meituan.sdk.MeituanClient;

/**
 * About:
 * Other:
 * Created: wfli on 2023/7/5 14:25.
 * Editored:
 */
public class MtSdkBaseService {

    public static final Long DEVELOPER_ID = -1L;
    public static final String SIGN_KEY = "signKey";
    public static final String APP_AUTH_TOKEN = "authToken";
    public static final MeituanClient meituanClient;

    static {
        meituanClient = DefaultMeituanClient.builder(DEVELOPER_ID, SIGN_KEY).build();
    }
}
