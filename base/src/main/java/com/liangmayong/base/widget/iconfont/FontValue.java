package com.liangmayong.base.widget.iconfont;

/**
 * FontValue
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class FontValue implements CharSequence {

    private String font;
    private String value;

    public String getFont() {
        return font;
    }

    public FontValue(String font, int unicode) {
        this.font = font;
        this.value = Character.toString((char) unicode);
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int index) {
        return value.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return value.subSequence(start, end);
    }

    @Override
    public String toString() {
        return value;
    }
}
