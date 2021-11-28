package com.jean.community;

import java.io.IOException;

/**
 * Created by The High Priestess
 *
 * @description
 */
public class WkTest {
    public static void main(String[] args) {
        String cmd = "D:/wkhtmltopdf/bin/wkhtmlpdf/bin/wkhtmltoimage --quality 75 https://www.baidu.com D:/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
