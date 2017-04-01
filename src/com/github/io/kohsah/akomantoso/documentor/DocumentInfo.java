
package com.github.io.kohsah.akomantoso.documentor;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.function.BiConsumer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author ashok
 */
public class DocumentInfo {
     HashMap<String, ElementInfo> elementsInfo = new HashMap<>(0);
     
     public DocumentInfo(HashMap<String,ElementInfo> eInfo) {
         this.elementsInfo = eInfo;
     }
     
     public void add(ElementInfo info) {
         elementsInfo.put(info.getName(), info);
     }
     
     public ElementInfo get(String name) {
         return elementsInfo.get(name);
     }
     
     @Override
     public String toString() {
         StringBuilder s = new StringBuilder();
         return s.toString();
     }
     
     private  String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute("indent-number", 4);
       
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");             
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
     
     public String toXmlString() throws ParserConfigurationException {
         return convertDocumentToString(toXml());
     }
     
     public Document toXml() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("documentInfo");
        doc.appendChild(rootElement);
        Iterator<String> ir = this.elementsInfo.keySet().iterator();
        while (ir.hasNext()) {
            String sKey = ir.next();
            ElementInfo info = elementsInfo.get(sKey);
            Element elem = doc.createElement("element");
            elem.setAttribute("name", info.getName());
            elem.setAttribute("ns", info.getNamespace());
            BiConsumer<String, AttributeInfo> consAttr = 
                (attrName, attrInfo)-> {
                    Element attrElem = doc.createElement("attribute");
                    attrElem.setAttribute("name", attrInfo.getName());
                    attrElem.setAttribute("type", attrInfo.getType());
                    attrElem.setAttribute("required", attrInfo.isRequired().toString());
                    elem.appendChild(attrElem);
                };            
            info.getAttributes().forEach(consAttr);
            rootElement.appendChild(elem);
        }
        return doc;
     }
}
