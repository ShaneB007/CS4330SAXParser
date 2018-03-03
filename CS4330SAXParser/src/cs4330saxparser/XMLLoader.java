/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4330saxparser;


import java.io.File;
import java.util.Stack;
import javafx.scene.control.TextArea;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author bisho
 */
public class XMLLoader {
    public static void parse(File file, TextArea textArea) throws Exception {
        
        try {
			
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser parser = factory.newSAXParser();
		
	StringBuilder sb = new StringBuilder();	
	DefaultHandler handler = new DefaultHandler() {
        Stack<String> stack = new Stack<>();

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                stack.push(qName);
                sb.append(indentLevel(stack.size() - 1));
                sb.append(qName);
                
                if (stack.size() <= 2) {
                    sb.append("\n");
                }
                
                for (int i = 0; i < attributes.getLength(); i++) {
                    sb.append(indentLevel(stack.size() - 1));
                    sb.append(attributes.getQName(i) + " = " + attributes.getValue(i));
                }
                
                if (attributes.getLength() > 0) {
                    sb.append("\n");
                }
                
                textArea.setText(sb.toString());
            }
            

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if (!stack.isEmpty() && qName.equals(stack.peek())) {
                    stack.pop();
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                String str = new String(ch, start, length);
                
                if (!"".equals(str.trim())) {
                    sb.append(" = " + str).append("\n");
                }
                
                textArea.setText(sb.toString());
            }
			
        };
        
        parser.parse(file, handler);
	
    } catch (Exception e) {
    throw e;}
}
	
    private static String indentLevel(int level) {
        StringBuilder sb = new StringBuilder(level * 4);
        
        for (int i = 0; i < level; i++) {
            sb.append("    ");
        }
        
        return sb.toString();
    }
}
