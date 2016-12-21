package com.liangmayong.base.support.http;

import android.annotation.SuppressLint;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * HttpPost
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("HandlerLeak")
class HttpPost {
    private static String boundary = UUID.randomUUID().toString();
    private static String prefix = "--";
    private static String lineEnd = "\r\n";
    private static final int SUCCESS = 1;
    private static final int ERROR = 2;

    private static class PostRequest {
        private OnHttpListener netListener;
        private byte[] data;
        private Exception ex;
        private String encode = "utf-8";
        private String cookie;
        private Map<String, HttpFile> files = new HashMap<String, HttpFile>();
        public Map<String, String> params = new HashMap<String, String>();
        public Map<String, String> headers = new HashMap<String, String>();

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Map<String, HttpFile> getFiles() {
            return files;
        }

        public void setHeaders(Map<String, String> headers) {
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                this.headers.remove(entry.getKey());
            }
            if (headers == null) {
                return;
            }
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                this.headers.put(entry.getKey(), entry.getValue());
            }
        }

        public void setFiles(Map<String, HttpFile> files) {
            for (Map.Entry<String, HttpFile> entry : this.files.entrySet()) {
                this.files.remove(entry.getKey());
            }
            if (files == null) {
                return;
            }
            for (Map.Entry<String, HttpFile> entry : files.entrySet()) {
                this.files.put(entry.getKey(), entry.getValue());
            }
        }

        public PostRequest(OnHttpListener netListener) {
            this.netListener = netListener;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            for (Map.Entry<String, String> entry : this.params.entrySet()) {
                this.params.remove(entry.getKey());
            }
            if (params == null) {
                return;
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                this.params.put(entry.getKey(), entry.getValue());
            }
        }

        public void setEncode(String encode) {
            this.encode = encode;
        }

        public void setCookie(String cookie) {
            this.cookie = cookie;
        }

        public String getEncode() {
            return encode;
        }

        public String getCookie() {
            return cookie;
        }

        public byte[] getBody() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                if ((getParams() != null && getParams().size() > 0)
                        || (getFiles() != null && getFiles().size() > 0)) {
                    // post params
                    if (getParams() != null && getParams().size() > 0) {
                        for (Map.Entry<String, String> entry : getParams().entrySet()) {
                            StringBuilder psb = new StringBuilder();
                            psb.append(prefix);
                            psb.append(boundary);
                            psb.append(lineEnd);
                            psb.append("Content-Disposition:form-data;name=\"" + entry.getKey() + "\"" + lineEnd
                                    + lineEnd);
                            psb.append(entry.getValue());
                            psb.append(lineEnd);
                            outputStream.write(psb.toString().getBytes());
                        }
                    }
                    // post files
                    if (getFiles() != null && getFiles().size() > 0) {
                        for (Map.Entry<String, HttpFile> entry : getFiles().entrySet()) {
                            File item = new File(entry.getValue().getPath());
                            long totallenght = item.length();
                            int bufferSize = (int) (totallenght / 10);
                            StringBuilder sb = new StringBuilder();
                            sb.append(prefix);
                            sb.append(boundary);
                            sb.append(lineEnd);
                            sb.append("Content-Disposition: form-data;name=\"" + entry.getKey() + "\";filename=\""
                                    + entry.getValue().getName() + "\"" + lineEnd);
                            FileInputStream in = new FileInputStream(item);
                            sb.append("Content-Length:" + in.available() + lineEnd);
                            sb.append("Content-Type:" + entry.getValue().getContentType() + lineEnd + lineEnd);
                            outputStream.write(sb.toString().getBytes());
                            int bytes = 0;
                            byte[] bufferOut = new byte[Math.max(20 * 1024, Math.min(512 * 1024, bufferSize))];
                            while ((bytes = in.read(bufferOut)) != -1) {
                                outputStream.write(bufferOut, 0, bytes);
                            }
                            outputStream.write(lineEnd.getBytes());
                            in.close();
                        }
                    }
                    byte[] end_data = (lineEnd + prefix + boundary + prefix + lineEnd).getBytes();
                    outputStream.write(end_data);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
            }
            byte[] bytes = outputStream.toByteArray();
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
            }
            return bytes;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public void setException(Exception ex) {
            this.ex = ex;
        }

        public void Error() {
            if (netListener != null) {
                try {
                    netListener.error(new HttpError(ex));
                } catch (Exception e) {
                }
            }
        }

        public void Success() {
            if (netListener != null) {
                try {
                    netListener.success(data, encode, cookie);
                } catch (Exception e) {
                }
            }
        }

    }

    /**
     * handler
     */
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            try {
                PostRequest message = (PostRequest) msg.obj;
                switch (msg.what) {
                    case ERROR:
                        message.Error();
                        break;
                    case SUCCESS:
                        message.Success();
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
            }
        }
    };

    private HttpPost() {
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param files       files
     * @param headers     headers
     * @param netListener netListener
     */
    public static void post(final String url, Map<String, String> params, Map<String, HttpFile> files,
                            Map<String, String> headers, OnHttpListener netListener) {
        final PostRequest postMsg = new PostRequest(netListener);
        postMsg.setParams(params);
        postMsg.setFiles(files);
        postMsg.setHeaders(headers);
        HttpUtils.executorService().execute(new Runnable() {
            @Override
            public void run() {
                dopost(url, postMsg);
            }
        });
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param files       files
     * @param headers     headers
     * @param encode      encode
     * @param netListener netListener
     */
    public static void post(final String url, Map<String, String> params, Map<String, HttpFile> files,
                            Map<String, String> headers, String encode, OnHttpListener netListener) {
        final PostRequest postMsg = new PostRequest(netListener);
        postMsg.setEncode(encode);
        postMsg.setParams(params);
        postMsg.setFiles(files);
        postMsg.setHeaders(headers);
        HttpUtils.executorService().execute(new Runnable() {
            @Override
            public void run() {
                dopost(url, postMsg);
            }
        });
    }

    /**
     * post
     *
     * @param url         url
     * @param params      params
     * @param files       files
     * @param headers     headers
     * @param encode      encode
     * @param cookie      cookie
     * @param netListener netListener
     */
    public static void post(final String url, Map<String, String> params, Map<String, HttpFile> files,
                            Map<String, String> headers, String encode, String cookie, OnHttpListener netListener) {
        final PostRequest postMsg = new PostRequest(netListener);
        postMsg.setCookie(cookie);
        postMsg.setEncode(encode);
        postMsg.setParams(params);
        postMsg.setFiles(files);
        postMsg.setHeaders(headers);
        HttpUtils.executorService().execute(new Runnable() {
            @Override
            public void run() {
                dopost(url, postMsg);
            }
        });
    }

    /**
     * do post
     *
     * @param url     url
     * @param request request
     */
    private static void dopost(final String url, PostRequest request) {
        boolean success = false;
        int code = -1;
        HttpURLConnection connection = null;
        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // read result
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", request.getEncode());
            byte[] bytes = request.getBody();
            connection.setRequestProperty("Content-Length", "" + bytes.length);
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            if (request.getHeaders() != null && request.getHeaders().size() > 0) {
                for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (request.getCookie() != null) {
                connection.addRequestProperty("Cookie", request.getCookie());
            }
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            code = connection.getResponseCode();
            if (code == 200) {
                InputStream inputStream;
                inputStream = connection.getInputStream();
                Map<String, List<String>> map = connection.getHeaderFields();
                String cookieVal = request.getCookie();
                String encodeVal = request.getEncode();
                for (String name : map.keySet()) {
                    if ("Set-Cookie".equals(name)) {
                        cookieVal = map.get(name).toString().replace("[", "").replace("]", "");
                        break;
                    } else if ("Content-Type".equals(name)) {
                        int m = map.get(name).toString().indexOf("charset=");
                        encodeVal = map.get(name).toString().substring(m + 8).replace("]", "");
                    }
                }
                request.setCookie(cookieVal);
                request.setEncode(encodeVal);
                request.setData(inputStream2bytes(inputStream));
                success = true;
            } else {
                request.setException(new HttpServiceError(code));
            }
        } catch (Exception e) {
            request.setException(e);
        } finally {
            try {
                connection.disconnect();
            } catch (Exception e2) {
            }
        }
        if (success) {
            handler.obtainMessage(SUCCESS, request).sendToTarget();
        } else {
            handler.obtainMessage(ERROR, request).sendToTarget();
        }
    }

    /**
     * input stream to bytes
     *
     * @param stream stream
     * @return bytes
     */
    private static byte[] inputStream2bytes(InputStream stream) {
        ByteArrayOutputStream nBuilder = new ByteArrayOutputStream();
        try {
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = stream.read(data)) != -1) {
                nBuilder.write(data, 0, len);
            }
            nBuilder.close();
        } catch (IOException e1) {
        }
        return nBuilder.toByteArray();
    }

}
