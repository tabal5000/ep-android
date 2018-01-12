package com.example.tabs.ep_rest;

import com.example.tabs.ep_rest.api.resource.MyAppService;
import com.example.tabs.ep_rest.client.RetroFitClient;

/**
 * Created by Tabs on 12/01/2018.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://10.0.2.2:80/api/";

    public static MyAppService getMyAppService() {
        return RetroFitClient.getClient(BASE_URL).create(MyAppService.class);
    }

}
