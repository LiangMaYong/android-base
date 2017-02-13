package com.liangmayong.base.support.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * XmlParserUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class XmlParserUtils extends DefaultHandler {

    Map<String, String> map = null;
    List<Map<String, String>> list = null;
    String currentTag = null;
    String currentValue = null;
    String nodeName = null;
    boolean isLoading = false;
    List<String> strings = null;

    /**
     * parserXmlPosition
     *
     * @param xml      xml
     * @param nodeName nodeName
     * @param index    index
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static Map<String, String> parserXmlPosition(String xml, String nodeName, int index) throws Exception {
        return parserXml(new ByteArrayInputStream(xml.getBytes()), nodeName).get(index);
    }

    /**
     * parserXmlPosition
     *
     * @param xml      xml
     * @param nodeName nodeName
     * @param form     form
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static Map<String, String> parserXmlPosition(String xml,
                                                        String nodeName, String[] form, int index) throws Exception {
        return parserXml(new ByteArrayInputStream(xml.getBytes()), nodeName, form).get(index);
    }


    /**
     * parserXmlPosition
     *
     * @param inStream inStream
     * @param nodeName nodeName
     * @param index    index
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static Map<String, String> parserXmlPosition(InputStream inStream, String nodeName, int index) throws Exception {
        return parserXml(inStream, nodeName).get(index);
    }

    /**
     * parserXmlPosition
     *
     * @param inStream inStream
     * @param nodeName nodeName
     * @param form     form
     * @param index    index
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static Map<String, String> parserXmlPosition(InputStream inStream, String nodeName, String[] form, int index) throws Exception {
        return parserXml(inStream, nodeName, form).get(index);
    }

    /**
     * parserXml
     *
     * @param xml      xml
     * @param nodeName nodeName
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static List<Map<String, String>> parserXml(String xml, String nodeName) throws Exception {
        return parserXml(new ByteArrayInputStream(xml.getBytes()), nodeName);
    }

    /**
     * parserXml
     *
     * @param xml      xml
     * @param nodeName nodeName
     * @param form     form
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static List<Map<String, String>> parserXml(String xml,
                                                      String nodeName, String[] form) throws Exception {
        return parserXml(new ByteArrayInputStream(xml.getBytes()), nodeName, form);
    }

    /**
     * parserXml
     *
     * @param inStream inStream
     * @param nodeName nodeName
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static List<Map<String, String>> parserXml(InputStream inStream, String nodeName) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        XmlParserUtils handler = new XmlParserUtils(nodeName);
        saxParser.parse(inStream, handler);
        inStream.close();
        return handler.getList();
    }

    /**
     * parserXml
     *
     * @param inStream inStream
     * @param nodeName nodeName
     * @param form     form
     * @return List<Map<String, String>>
     * @throws Exception e
     */
    public static List<Map<String, String>> parserXml(InputStream inStream,
                                                      String nodeName, String[] form) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        XmlParserUtils handler = new XmlParserUtils(nodeName, form);
        saxParser.parse(inStream, handler);
        inStream.close();
        return handler.getList();
    }

    private XmlParserUtils(String nodeName, String[] string) {
        this.nodeName = nodeName;
        if (string != null) {
            strings = new ArrayList<String>();
            for (int i = 0; i < string.length; i++) {
                strings.add(string[i]);
            }
        }
    }

    private XmlParserUtils(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void startDocument() throws SAXException {
        list = new ArrayList<Map<String, String>>();
        isLoading = true;
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals(nodeName)) {
            map = new HashMap<String, String>();
        }
        if (attributes != null && map != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                map.put(attributes.getQName(i), attributes.getValue(i));
            }
        }
        currentTag = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (currentTag != null && map != null) {
            currentValue = new String(ch, start, length);
            if (currentValue != null && !currentValue.trim().equals("")
                    && !currentValue.trim().equals("\n")) {
                if (strings != null) {
                    if (strings.contains(currentTag)) {
                        map.put(currentTag, currentValue);
                    }
                } else {
                    map.put(currentTag, currentValue);
                }
            }
        }
        currentTag = null;
        currentValue = null;
    }

    @Override
    public void endDocument() throws SAXException {
        isLoading = false;
        super.endDocument();
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals(nodeName)) {
            list.add(map);
            map = null;
        }
    }

}
