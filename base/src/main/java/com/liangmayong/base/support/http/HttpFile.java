package com.liangmayong.base.support.http;

/**
 * Created by LiangMaYong on 2016/12/20.
 */
public class HttpFile {

    // path
    private String path = "";
    // name
    private String name;

    public HttpFile(String name, String localPath) {
        this.name = name;
        this.path = localPath;
    }

    /**
     * doGet file local path
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
     * doGet file name
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
}
