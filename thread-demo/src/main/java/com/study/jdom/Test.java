package com.study.jdom;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author yutong on 2016/12/28.
 */
public class Test {

    public static void main(String[] args) {
        Element root = new Element("root");
        Document document = new Document(root);

        Element child = new Element("prov");
        child.setAttribute("name", "北京市");
        child.setAttribute("zipcode", "101111");
        root.setContent(child);

        Format format = Format.getPrettyFormat();
        XMLOutputter XMLOut = new XMLOutputter(format);
        try {
            XMLOut.output(document, new FileWriter("/file.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
