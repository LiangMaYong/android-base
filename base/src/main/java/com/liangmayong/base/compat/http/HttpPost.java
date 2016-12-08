package com.liangmayong.base.compat.http;

import android.annotation.SuppressLint;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
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
    ;
    private static String prefix = "--";
    private static String lineEnd = "\r\n";
    private static final int SUCCESS = 1;
    private static final int ERROR = 2;

    private static class PostMsg {
        private OnHttpListener netListener;
        private byte[] data;
        private Exception ex;
        private String encode = "utf-8";
        private String cookie;
        private Map<String, File> files = new HashMap<String, File>();
        public Map<String, String> params = new HashMap<String, String>();
        public Map<String, String> headers = new HashMap<String, String>();

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Map<String, File> getFiles() {
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

        public void setFiles(Map<String, File> files) {
            for (Map.Entry<String, File> entry : this.files.entrySet()) {
                this.files.remove(entry.getKey());
            }
            if (files == null) {
                return;
            }
            for (Map.Entry<String, File> entry : files.entrySet()) {
                this.files.put(entry.getKey(), entry.getValue());
            }
        }

        public PostMsg(OnHttpListener netListener) {
            this.netListener = netListener;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {

            for (Map.Entry<String, String> entry : this.params.entrySet()) {
                this.params.remove(entry.getKey());
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

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            try {
                PostMsg message = (PostMsg) msg.obj;
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

    public static void post(final String url, Map<String, String> params, Map<String, File> files,
                            Map<String, String> headers, OnHttpListener netListener) {
        final PostMsg postMsg = new PostMsg(netListener);
        postMsg.setParams(params);
        postMsg.setFiles(files);
        postMsg.setHeaders(headers);
        HttpUtil.executorService().execute(new Runnable() {
            @Override
            public void run() {
                dopost(url, postMsg);
            }
        });
    }

    public static void post(final String url, Map<String, String> params, Map<String, File> files,
                            Map<String, String> headers, String encode, OnHttpListener netListener) {
        final PostMsg postMsg = new PostMsg(netListener);
        postMsg.setEncode(encode);
        postMsg.setParams(params);
        postMsg.setFiles(files);
        postMsg.setHeaders(headers);
        HttpUtil.executorService().execute(new Runnable() {
            @Override
            public void run() {
                dopost(url, postMsg);
            }
        });
    }

    public static void post(final String url, Map<String, String> params, Map<String, File> files,
                            Map<String, String> headers, String encode, String cookie, OnHttpListener netListener) {
        final PostMsg postMsg = new PostMsg(netListener);
        postMsg.setCookie(cookie);
        postMsg.setEncode(encode);
        postMsg.setParams(params);
        postMsg.setFiles(files);
        postMsg.setHeaders(headers);
        HttpUtil.executorService().execute(new Runnable() {
            @Override
            public void run() {
                dopost(url, postMsg);
            }
        });
    }

    private static void dopost(final String url, PostMsg message) {
        boolean success = false;
        int code = -1;
        HttpURLConnection connection = null;
        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setChunkedStreamingMode(512 * 1024);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // read result
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", message.getEncode());
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            if (message.getHeaders() != null && message.getHeaders().size() > 0) {
                for (Map.Entry<String, String> entry : message.getHeaders().entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            byte[] end_data = (lineEnd + prefix + boundary + prefix + lineEnd).getBytes();
            if (message.getCookie() != null) {
                connection.addRequestProperty("Cookie", message.getCookie());
            }
            try {
                if ((message.getParams() != null && message.getParams().size() > 0)
                        || (message.getFiles() != null && message.getFiles().size() > 0)) {

                    OutputStream outputStream = connection.getOutputStream();
                    // post params
                    if (message.getParams() != null && message.getParams().size() > 0) {
                        for (Map.Entry<String, String> entry : message.getParams().entrySet()) {
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
                    if (message.getFiles() != null && message.getFiles().size() > 0) {
                        for (Map.Entry<String, File> entry : message.getFiles().entrySet()) {
                            File item = entry.getValue();
                            long totallenght = item.length();
                            int bufferSize = (int) (totallenght / 10);
                            StringBuilder sb = new StringBuilder();
                            sb.append(prefix);
                            sb.append(boundary);
                            sb.append(lineEnd);
                            sb.append("Content-Disposition: form-data;name=\"" + entry.getKey() + "\";filename=\""
                                    + item.getName() + "\"" + lineEnd);
                            sb.append("Content-Type:" + getContentType(item.getName()) + lineEnd + lineEnd);
                            outputStream.write(sb.toString().getBytes());
                            FileInputStream in = new FileInputStream(item);
                            int bytes = 0;
                            byte[] bufferOut = new byte[Math.max(20 * 1024, Math.min(512 * 1024, bufferSize))];
                            while ((bytes = in.read(bufferOut)) != -1) {
                                outputStream.write(bufferOut, 0, bytes);
                            }
                            outputStream.write(lineEnd.getBytes());
                            in.close();
                        }
                    }
                    outputStream.write(end_data);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
            }
            code = connection.getResponseCode();
            if (code == 200) {
                InputStream inputStream;
                inputStream = connection.getInputStream();
                Map<String, List<String>> map = connection.getHeaderFields();
                String cookieVal = message.getCookie();
                String encodeVal = message.getEncode();
                for (String name : map.keySet()) {
                    if ("Set-Cookie".equals(name)) {
                        cookieVal = map.get(name).toString().replace("[", "").replace("]", "");
                        break;
                    } else if ("Content-Type".equals(name)) {
                        int m = map.get(name).toString().indexOf("charset=");
                        encodeVal = map.get(name).toString().substring(m + 8).replace("]", "");
                    }
                }
                message.setCookie(cookieVal);
                message.setEncode(encodeVal);
                message.setData(inputStream2bytes(inputStream));
                success = true;
            } else {
                success = false;
                message.setException(new HttpServiceError(code));
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            message.setException(e);
            try {
                connection.disconnect();
            } catch (Exception e2) {
            }
        } catch (UnsupportedEncodingException e) {
            message.setException(e);
            try {
                connection.disconnect();
            } catch (Exception e2) {
            }
        } catch (SocketTimeoutException e) {
            message.setException(e);
            try {
                connection.disconnect();
            } catch (Exception e2) {
            }
        } catch (EOFException e) {
            message.setException(e);
            try {
                connection.disconnect();
            } catch (Exception e2) {
            }
        } catch (IOException e) {
            message.setException(e);
            try {
                connection.disconnect();
            } catch (Exception e2) {
            }
        }
        if (success) {
            handler.obtainMessage(SUCCESS, message).sendToTarget();
        } else {
            handler.obtainMessage(ERROR, message).sendToTarget();
        }
    }

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

    private static String getContentType(String type) {
        String contenttype = "";
        type = getExtensionName(type);
        if (type == null || type.equals("") || type.equals(".*") || type.equals(".") || type.equals("*")) {
            contenttype = "application/octet-stream";
        } else if (type.equals(".txt") || type.equals("txt")) {
            contenttype = "text/plain";
        } else if (type.equals(".tif") || type.equals("tif")) {
            contenttype = "image/tiff";
        } else if (type.equals(".001") || type.equals("001")) {
            contenttype = "text/plain";
        } else if (type.equals(".asp") || type.equals("asp")) {
            contenttype = "text/asp";
        } else if (type.equals(".avi") || type.equals("avi")) {
            contenttype = "video/avi";
        } else if (type.equals(".bmp") || type.equals("bmp")) {
            contenttype = "application/x-bmp";
        } else if (type.equals(".exe") || type.equals("exe")) {
            contenttype = "application/x-msdownload";
        } else if (type.equals(".eps") || type.equals("eps")) {
            contenttype = "application/x-ps";
        } else if (type.equals(".htm") || type.equals("htm")) {
            contenttype = "text/html";
        } else if (type.equals(".html") || type.equals("html")) {
            contenttype = "text/html";
        } else if (type.equals(".jfif") || type.equals("jfif")) {
            contenttype = "image/jpeg";
        } else if (type.equals(".jpe") || type.equals("jpe")) {
            contenttype = "image/jpeg";
        } else if (type.equals(".jpeg") || type.equals("jpeg")) {
            contenttype = "image/jpeg";
        } else if (type.equals(".jpg") || type.equals("jpg")) {
            contenttype = "image/jpeg";
        } else if (type.equals(".png") || type.equals("png")) {
            contenttype = "image/png";
        } else if (type.equals(".gif") || type.equals("gif")) {
            contenttype = "image/gif";
        } else if (type.equals(".js") || type.equals("js")) {
            contenttype = "application/x-javascript";
        } else if (type.equals(".jsp") || type.equals("jsp")) {
            contenttype = "text/html";
        } else if (type.equals(".mp3") || type.equals("mp3")) {
            contenttype = "audio/mp3";
        } else if (type.equals(".mp4") || type.equals("mp4")) {
            contenttype = "video/mpeg4";
        } else if (type.equals(".mpa") || type.equals("mpa")) {
            contenttype = "video/x-mpg";
        } else if (type.equals(".pot") || type.equals("pot")) {
            contenttype = "application/vnd.ms-powerpoint";
        } else if (type.equals(".ppa") || type.equals("ppa")) {
            contenttype = "application/vnd.ms-powerpoint";
        } else if (type.equals(".pps") || type.equals("pps")) {
            contenttype = "application/vnd.ms-powerpoint";
        } else if (type.equals(".ppt") || type.equals("ppt")) {
            contenttype = "application/vnd.ms-powerpoint";
        } else if (type.equals(".ppm") || type.equals("ppm")) {
            contenttype = "application/x-ppm";
        } else if (type.equals(".rmvb") || type.equals("rmvb")) {
            contenttype = "application/vnd.rn-realmedia-vbr";
        } else if (type.equals(".torrent") || type.equals("torrent")) {
            contenttype = "application/x-bittorrent";
        } else if (type.equals(".vxml") || type.equals("vxml")) {
            contenttype = "text/xml";
        } else if (type.equals(".xml") || type.equals("xml")) {
            contenttype = "text/xml";
        } else if (type.equals(".wm") || type.equals("wm")) {
            contenttype = "video/x-ms-wm";
        } else if (type.equals(".wma") || type.equals("wma")) {
            contenttype = "audio/x-ms-wma";
        } else if (type.equals(".xsl") || type.equals("xsl")) {
            contenttype = "text/xml";
        } else if (type.equals(".xwd") || type.equals("xwd")) {
            contenttype = "application/x-xwd";
        } else if (type.equals(".java") || type.equals("java")) {
            contenttype = "java/*";
        } else if (type.equals(".pdf") || type.equals("pdf")) {
            contenttype = "application/pdf";
        } else {
            contenttype = "application/octet-stream";
        }
        return contenttype;
    }

    private static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
