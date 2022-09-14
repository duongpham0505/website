package com.program.website.electronic.base.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebBase {
    private static final WebBase webBase = new WebBase();
    public static WebBase getInstance(){return webBase;}
    private static final Logger logger = LogManager.getLogger(WebBase.class);

    public String postRequest(String url, String body, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            StringEntity userEntity = new StringEntity(body, "UTF-8");
            httpPost.setEntity(userEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                return EntityUtils.toString(httpEntity);
            }
        }catch (Exception e) {
            logger.error(e);
            return null;
        }finally {
            try {
                httpClient.close();
            }catch (IOException e) {
                logger.error(e);
            }
        }
        return null;
    }

    public String putRequest(String url, String dataBody, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader("Content-type", "application/json");
            StringEntity userEntity = new StringEntity(dataBody, "UTF-8");
            httpPut.setEntity(userEntity);
            List<NameValuePair> listName = new ArrayList<>();
            if (params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    listName.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            URI uri = (new URIBuilder(httpPut.getURI())).addParameters(listName).build();

            httpPut.setURI(uri);
            HttpResponse httpResponse = httpClient.execute(httpPut);
            if (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                return EntityUtils.toString(httpEntity);
            }
        }catch (Exception e) {
            logger.error(e);
            return null;
        }finally {
            try {
                httpClient.close();
            }catch (IOException e) {
                logger.error(e);
            }
        }
        return null;
    }

    public String getRequest(String url, String dataBody, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-type", "application/json");

            List<NameValuePair> listName = new ArrayList<>();
            if (params != null && params.size() > 0) {

                for (String key : params.keySet()) {
                    listName.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            URI uri = (new URIBuilder(httpGet.getURI())).addParameters(listName).build();

            httpGet.setURI(uri);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                return EntityUtils.toString(httpEntity);
            }
        }catch (Exception e) {
            logger.error(e);
            return null;
        }finally {
            try {
                httpClient.close();
            }catch (IOException e) {
                logger.error(e);
            }
        }
        return null;
    }
}
