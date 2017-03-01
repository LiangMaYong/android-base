package com.liangmayong.base.support.nethttp;

/**
 * Created by LiangMaYong on 2016/12/20.
 */
public class NetFile {

    // path
    private String path = "";
    // name
    private String name;
    // content type
    private String contentType;

    public NetFile(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * get file local path
     *
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * set file local path
     *
     * @param path path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * get file name
     *
     * @return file name
     */
    public String getName() {
        return name;
    }

    /**
     * set file name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HttpFile{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * setContentType
     *
     * @param contentType contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * get content type by extension name
     *
     * @return content type
     */
    public String getContentType() {
        if (this.contentType != null && !"".equals(contentType)) {
            return contentType;
        }
        String contentType = "";
        String extensionName = getExtensionName(name);
        if (extensionName == null || extensionName.equals("") || extensionName.equals(".*") || extensionName.equals(".") || extensionName.equals("*")) {
            contentType = "application/octet-stream";
        } else if (extensionName.equals(".txt") || extensionName.equals("txt")) {
            contentType = "text/plain";
        } else if (extensionName.equals(".tif") || extensionName.equals("tif")) {
            contentType = "image/tiff";
        } else if (extensionName.equals(".001") || extensionName.equals("001")) {
            contentType = "text/plain";
        } else if (extensionName.equals(".asp") || extensionName.equals("asp")) {
            contentType = "text/asp";
        } else if (extensionName.equals(".avi") || extensionName.equals("avi")) {
            contentType = "video/avi";
        } else if (extensionName.equals(".bmp") || extensionName.equals("bmp")) {
            contentType = "application/x-bmp";
        } else if (extensionName.equals(".exe") || extensionName.equals("exe")) {
            contentType = "application/x-msdownload";
        } else if (extensionName.equals(".eps") || extensionName.equals("eps")) {
            contentType = "application/x-ps";
        } else if (extensionName.equals(".htm") || extensionName.equals("htm")) {
            contentType = "text/html";
        } else if (extensionName.equals(".html") || extensionName.equals("html")) {
            contentType = "text/html";
        } else if (extensionName.equals(".jfif") || extensionName.equals("jfif")) {
            contentType = "image/jpeg";
        } else if (extensionName.equals(".jpe") || extensionName.equals("jpe")) {
            contentType = "image/jpeg";
        } else if (extensionName.equals(".jpeg") || extensionName.equals("jpeg")) {
            contentType = "image/jpeg";
        } else if (extensionName.equals(".jpg") || extensionName.equals("jpg")) {
            contentType = "image/jpeg";
        } else if (extensionName.equals(".png") || extensionName.equals("png")) {
            contentType = "image/png";
        } else if (extensionName.equals(".gif") || extensionName.equals("gif")) {
            contentType = "image/gif";
        } else if (extensionName.equals(".js") || extensionName.equals("js")) {
            contentType = "application/x-javascript";
        } else if (extensionName.equals(".jsp") || extensionName.equals("jsp")) {
            contentType = "text/html";
        } else if (extensionName.equals(".mp3") || extensionName.equals("mp3")) {
            contentType = "audio/mp3";
        } else if (extensionName.equals(".mp4") || extensionName.equals("mp4")) {
            contentType = "video/mpeg4";
        } else if (extensionName.equals(".mpa") || extensionName.equals("mpa")) {
            contentType = "video/x-mpg";
        } else if (extensionName.equals(".pot") || extensionName.equals("pot")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (extensionName.equals(".ppa") || extensionName.equals("ppa")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (extensionName.equals(".pps") || extensionName.equals("pps")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (extensionName.equals(".ppt") || extensionName.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (extensionName.equals(".ppm") || extensionName.equals("ppm")) {
            contentType = "application/x-ppm";
        } else if (extensionName.equals(".rmvb") || extensionName.equals("rmvb")) {
            contentType = "application/vnd.rn-realmedia-vbr";
        } else if (extensionName.equals(".torrent") || extensionName.equals("torrent")) {
            contentType = "application/x-bittorrent";
        } else if (extensionName.equals(".vxml") || extensionName.equals("vxml")) {
            contentType = "text/xml";
        } else if (extensionName.equals(".xml") || extensionName.equals("xml")) {
            contentType = "text/xml";
        } else if (extensionName.equals(".wm") || extensionName.equals("wm")) {
            contentType = "video/x-ms-wm";
        } else if (extensionName.equals(".wma") || extensionName.equals("wma")) {
            contentType = "audio/x-ms-wma";
        } else if (extensionName.equals(".xsl") || extensionName.equals("xsl")) {
            contentType = "text/xml";
        } else if (extensionName.equals(".xwd") || extensionName.equals("xwd")) {
            contentType = "application/x-xwd";
        } else if (extensionName.equals(".java") || extensionName.equals("java")) {
            contentType = "java/*";
        } else if (extensionName.equals(".pdf") || extensionName.equals("pdf")) {
            contentType = "application/pdf";
        } else {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    /**
     * get file extension name
     *
     * @param filename filename
     * @return extension name
     */
    private String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
