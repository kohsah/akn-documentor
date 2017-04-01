package com.github.io.kohsah.akomantoso.documentor;

import com.sun.org.apache.xerces.internal.impl.xs.XSElementDecl;
import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
import com.sun.org.apache.xerces.internal.xs.XSAnnotation;
import com.sun.org.apache.xerces.internal.xs.XSAttributeDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSAttributeUse;
import com.sun.org.apache.xerces.internal.xs.XSComplexTypeDefinition;
import static com.sun.org.apache.xerces.internal.xs.XSConstants.ELEMENT_DECLARATION;
import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSNamedMap;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ashok
 */
public class Main {
    
     public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File("schema/doc.xml");
        SchemaInfo schemaInfo = new SchemaInfo(xmlFile);
        schemaInfo.loadSchema();
        schemaInfo.loadDocumentInfo();
        DocumentInfo dInfo = schemaInfo.getDocumentInfo();
        System.out.println(" D INFO " + dInfo.toXmlString());

     }

    
    public static void main2(String[] args) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File("schema/doc.xml");
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        dbFactory.setValidating(true);
        dbFactory.setAttribute(
                "http://apache.org/xml/features/validation/schema", 
                Boolean.TRUE
        );
    // you also must specify Xerces PSVI DOM implementation
    // "org.apache.xerces.dom.PSVIDocumentImpl"
        dbFactory.setAttribute("http://apache.org/xml/properties/dom/document-class-name", 
            "com.sun.org.apache.xerces.internal.dom.PSVIDocumentImpl");        
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        ElementPSVI rootPSVI = (ElementPSVI)doc.getDocumentElement();
        XSModel schema = (XSModel) rootPSVI.getSchemaInformation();
        // map has QName and XSElementDecl
        XSNamedMap map = schema.getComponents(ELEMENT_DECLARATION);
        QName qqname = new QName("http://docs.oasis-open.org/legaldocml/ns/akn/3.0/WD17", "FRBRWork");
        XSElementDeclaration efrbrWork = (XSElementDeclaration) map.get(qqname);
        XSComplexTypeDefinition xworktd = (XSComplexTypeDefinition)efrbrWork.getTypeDefinition();

        
        Iterator<Entry<QName, XSElementDecl>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<QName, XSElementDecl> entry = (Map.Entry<QName,XSElementDecl>)iterator.next();
            QName qname = entry.getKey();
            XSElementDeclaration elem = entry.getValue();
            XSObjectList anntsList = elem.getAnnotations();
            for (int i=0; i < anntsList.getLength(); i++) {
                XSAnnotation annt = (XSAnnotation) anntsList.item(i);
                System.out.println(" Annotation = " + annt.getAnnotationString());
            }
            XSTypeDefinition xtd = elem.getTypeDefinition();
            if (xtd == null) {
                 System.out.println(" XTD IS NULL ");
            } else {
                XSComplexTypeDefinition xctd = (XSComplexTypeDefinition)xtd;
                XSObjectList annts = xctd.getAnnotations();
                for(int i=0; i < annts.getLength(); i++ ){
                    XSAnnotation annt = (XSAnnotation) annts.item(i);
                    System.out.println(" Annotation = " + annt.getAnnotationString());
                }
                XSObjectList attrs = xctd.getAttributeUses();
                for(int i = 0; i < attrs.getLength(); i++){
                    XSAttributeUse attrUse = (XSAttributeUse)attrs.item(i);
                    XSAttributeDeclaration attr = attrUse.getAttrDeclaration() ;
                    System.out.println("Attr name: " + attr.getName() + " Type: " + attr.getTypeDefinition().getName()) ;
                }
            }
            //xcp
            //System.out.println(" CT NAME = "  + xcp.getName());
            System.out.println("Key : " + entry.getKey() + " Value :" + elem);
            break;
        }
        //map.forEach((k,v)->System.out.println("Item: "+ k.getClass().getName() +  " Value :" + v.getClass().getName()));
    }
}
