package com.common.http;

import com.common.exception.BusinessException;
import com.common.errorcode.DefaultErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


@Service("httpClientUtil")
public class HttpClientUtil {

    /**
     * 连接超时时间
     */
    private static int CONNECT_TIMEOUT = 10 * 1000;

    /**
     * 短时间连接超时时间
     */
    private static int SHORT_TIMEOUT = 5 * 1000;

    /**
     * 套接字超时时间
     */
    private static int SOCKET_TIMEOUT = 35 * 1000;

    /**
     * 连接池中 连接请求执行被阻塞的超时时间
     */

    private static int CONN_MANAGER_TIMEOUT = 10 * 1000;

    private static String UTF_8 = "utf-8";

    private CloseableHttpClient getHttpClient(boolean isShortTime) {
        int ctime = CONNECT_TIMEOUT;
        int stime = SOCKET_TIMEOUT;
        int cmtime = CONN_MANAGER_TIMEOUT;
        if (isShortTime) {
            // 短时间快速响应
            ctime = SHORT_TIMEOUT;
            stime = SHORT_TIMEOUT;
            cmtime = SHORT_TIMEOUT;
        }
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接到第三方服务的超时时间
                .setConnectTimeout(ctime)
                // 设置第三方服务传输数据的超时时间
                .setSocketTimeout(stime)
                // 设置从连接池获取连接超时时间
                .setConnectionRequestTimeout(cmtime).build();
        // 设置重定向策略 //为大连接量考虑，设置重试次数为0，默认为3
        CloseableHttpClient httpclient = HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).setDefaultRequestConfig(requestConfig).build();
        return httpclient;
    }

    public String postHttplientJson(String url, String info) throws BusinessException {
        return postJson(url, info, false);
    }

    /**
     * 增加header头
     *
     * @param url
     * @param info
     * @param header
     *
     * @return
     *
     * @throws BusinessException
     */
    public String postHttplientJsonHeard(String url, String info, Map<String, String> header) throws BusinessException {
        return postJson(url, info, false, header, "");
    }


    /**
     * post方式处理文件和图片上传
     *
     * @param url 服务器地址
     *
     * @return
     *
     * @throws Exception
     */
    public String postData(String url) throws Exception {
        CloseableHttpClient client = new SSLClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String info = EntityUtils.toString(entity, "UTF-8");
            return info;
        } catch (ClientProtocolException e) {
            throw new ClientProtocolException(url + "postMultipartData exception：", e);
        } catch (IOException e) {
            throw new IOException(url + "postMultipartData exception：", e);
        } catch (Exception e) {
            throw new Exception(url + "postMultipartData exception：", e);
        } finally {
            httpPost.releaseConnection();
            client.close();
        }
    }

    /**
     * 短时间快速响应，不然就超时断开，默认5秒
     *
     * @param url
     * @param info
     *
     * @return
     *
     * @throws BusinessException
     */
    public String postHttplientJsonShortTime(String url, String info) throws BusinessException {
        return postJson(url, info, true);
    }

    public String postJson(String url, String info, boolean isShortTime) throws BusinessException {
        return postJson(url, info, isShortTime, null, "");
    }

    public String postJson(String url, String info, boolean isShortTime, Map<String, String> headers, String contentType) throws BusinessException {
        return postJson(url, info, isShortTime, headers, contentType, UTF_8, UTF_8);
    }

    public String postJson(String url, String info, boolean isShortTime, Map<String, String> headers, String contentType, String reqCharset, String resCharset) throws BusinessException {
        CloseableHttpResponse resp = null;
        InputStream instream = null;
        BufferedReader reader = null;
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient(isShortTime);
            HttpPost httpPost = new HttpPost(url);
            StringEntity sentity = new StringEntity(info, reqCharset);// 解决中文乱码问题
            if (StringUtils.isNotBlank(info)) {
                httpPost.setEntity(sentity);
            }
            httpPost.setHeader("ContentType", "text/plain; charset=UTF-8");
            sentity.setContentType("application/json;charset=UTF-8");
            if (StringUtils.isNotBlank(contentType)) {
                httpPost.setHeader("ContentType", contentType);
                sentity.setContentType(contentType);
            }
            if (headers != null) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    Header header = new BasicHeader(entry.getKey(), entry.getValue());
                    httpPost.addHeader(header);
                }
            }

            resp = httpclient.execute(httpPost);
            if (resp == null) {
                throw new BusinessException(DefaultErrorCode.HTTP_RES_NO_ERROR.code(), DefaultErrorCode.HTTP_RES_NO_ERROR.msg());
            }
            HttpEntity entity = resp.getEntity();
            if (entity == null) {
                return "";
            }
            instream = entity.getContent();
            reader = new BufferedReader(new InputStreamReader(instream, resCharset));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_CLIENT_ERROR, e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_IO_ERROR, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_EXCEP__ERROR, e);
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (resp != null) {
                    resp.close();
                }
                if (instream != null) {
                    instream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String get(String url, boolean isShortTime) throws BusinessException {
        return get(url, isShortTime, UTF_8, null);
    }

    public String get(String url, boolean isShortTime, String resCharset) {
        return get(url, isShortTime, resCharset, null);
    }

    public String get(String url, boolean isShortTime, String resCharset, Map<String, String> headMap) throws BusinessException{
        CloseableHttpResponse resp = null;
        InputStream instream = null;
        BufferedReader reader = null;
        CloseableHttpClient httpclient = null;
        if (StringUtils.isBlank(resCharset)) {
            resCharset = UTF_8;
        }
        try {
            httpclient = getHttpClient(isShortTime);
            HttpGet httpGet = new HttpGet(url);
            if (null != headMap) {
                for (String headKey : headMap.keySet()) {
                    httpGet.addHeader(headKey, headMap.get(headKey));
                }
            }
            resp = httpclient.execute(httpGet);
            if (resp == null) {
                throw new BusinessException(DefaultErrorCode.HTTP_RES_NO_ERROR);
            }
            HttpEntity entity = resp.getEntity();
            if (entity == null) {
                return "";
            }
            instream = entity.getContent();
            reader = new BufferedReader(new InputStreamReader(instream, resCharset));
            StringBuilder stringBuilder = new StringBuilder();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_CLIENT_ERROR, e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_IO_ERROR, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_EXCEP__ERROR, e);
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (resp != null) {
                    resp.close();
                }
                if (instream != null) {
                    instream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取GET请求的状态 200 成功
     *
     * @param url
     * @param isShortTime
     *
     * @return
     *
     * @throws BusinessException
     */
    public Integer getRetCode(String url, boolean isShortTime) throws BusinessException {
        CloseableHttpResponse resp = null;
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient(isShortTime);
            HttpGet httpGet = new HttpGet(url);
            resp = httpclient.execute(httpGet);
            if (resp == null) {
                throw new BusinessException(DefaultErrorCode.HTTP_RES_NO_ERROR);
            }
            StatusLine statusLine = resp.getStatusLine();
            if (null == statusLine) {
                return null;
            }
            return statusLine.getStatusCode();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_CLIENT_ERROR, e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_IO_ERROR, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_EXCEP__ERROR, e);
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (resp != null) {
                    resp.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String postMap(String url, Map<String, String> headers, Map<String, String> params) throws BusinessException {

        return postMap(url, false, headers, params);
    }

    /**
     * 参数为 key-value
     *
     * @param url
     * @param isShortTime
     * @param headers
     * @param params
     *
     * @return
     *
     * @throws BusinessException
     */
    public String postMap(String url, boolean isShortTime, Map<String, String> headers, Map<String, String> params) throws BusinessException {
        return postMap(url, isShortTime, headers, params, UTF_8, UTF_8);
    }

    /**
     * 参数为 key-value
     *
     * @param url
     * @param isShortTime
     * @param headers
     * @param params
     *
     * @return
     *
     * @throws BusinessException
     */
    public String postMap(String url, boolean isShortTime, Map<String, String> headers, Map<String, String> params, String reqCharset, String resCharset) throws BusinessException {
        CloseableHttpResponse resp = null;
        InputStream instream = null;
        BufferedReader reader = null;
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getHttpClient(isShortTime);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("ContentType", "text/plain; charset=UTF-8");
            if (headers != null) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    Header header = new BasicHeader(entry.getKey(), entry.getValue());
                    httpPost.addHeader(header);
                }
            }
            // 装填参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (params != null) {
                for (String key : params.keySet()) {
                    nvps.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            // 设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, reqCharset));
            resp = httpclient.execute(httpPost);
            if (resp == null) {
                throw new BusinessException(DefaultErrorCode.HTTP_RES_NO_ERROR);
            }
            HttpEntity entity = resp.getEntity();
            if (entity == null) {
                return "";
            }
            instream = entity.getContent();
            reader = new BufferedReader(new InputStreamReader(instream, resCharset));
            StringBuilder stringBuffer = new StringBuilder();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_CLIENT_ERROR, e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_IO_ERROR, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(DefaultErrorCode.HTTP_EXCEP__ERROR, e);
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (resp != null) {
                    resp.close();
                }
                if (instream != null) {
                    instream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
