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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class SchemaInfo {
    
    File fXmlDoc ;
    XSModel fSchema;
    DocumentInfo documentInfo ; 
    public SchemaInfo(File xmlFileWithSchema) {
        fXmlDoc = xmlFileWithSchema;
    }
    
    public DocumentInfo getDocumentInfo() {
        return this.documentInfo;
    }
    
    public boolean loadSchema() throws ParserConfigurationException, SAXException, IOException{
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
        Document doc = dBuilder.parse(this.fXmlDoc);
        ElementPSVI rootPSVI = (ElementPSVI)doc.getDocumentElement();
        XSModel schema = (XSModel) rootPSVI.getSchemaInformation();
        fSchema = schema;
        return (fSchema != null);
    }
    

    
    public String getAnnotationsOfType(XSElementDeclaration elem) {
        StringBuilder s = new StringBuilder();
        XSObjectList anntsList = elem.getAnnotations();
        for (int i=0; i < anntsList.getLength(); i++) {
            XSAnnotation annt = (XSAnnotation) anntsList.item(i);
            s.append(annt.getAnnotationString());
        }        
        return s.toString();
    }
    
    public String getAnnotationsOfType(XSComplexTypeDefinition elem) {
        StringBuilder s = new StringBuilder();
        XSObjectList anntsList = elem.getAnnotations();
        for (int i=0; i < anntsList.getLength(); i++) {
            XSAnnotation annt = (XSAnnotation) anntsList.item(i);
            s.append(annt.getAnnotationString());
        }        
        return s.toString();
    }
    
    public String getAttributes(XSComplexTypeDefinition xctd){
        XSObjectList attrs = xctd.getAttributeUses();
        for(int i = 0; i < attrs.getLength(); i++){
            XSAttributeUse attrUse = (XSAttributeUse)attrs.item(i);
            XSAttributeDeclaration attr = attrUse.getAttrDeclaration() ;
            boolean req  = attrUse.getRequired();
            
            System.out.println("Attr name: " + attr.getName() + " Type: " + attr.getTypeDefinition().getName()) ;
        } 
       return new String();
    }
    
    public void loadDocumentInfo() {
        // find declared elements
        XSNamedMap map = this.fSchema.getComponents(ELEMENT_DECLARATION);
        Iterator<Map.Entry<QName, XSElementDecl>> iterator = 
                map.entrySet().iterator();
        HashMap<String, ElementInfo> elements= new HashMap<>(0);
        while(iterator.hasNext()) {
            ElementInfo eInfo = new ElementInfo();
            // iterate through the elements
            Map.Entry<QName, XSElementDecl> entry = 
                    (Map.Entry<QName,XSElementDecl>)iterator.next();
            // the key is the QName
            QName qElemname = entry.getKey();
            String qElemNS = qElemname.getNamespaceURI();
            eInfo.setName(qElemname.getLocalPart());
            eInfo.setNamespace(qElemname.getNamespaceURI());
            // the value is the element declaration
            XSElementDeclaration elem = entry.getValue();
            // annotations 
            String sAnnotation = this.getAnnotationsOfType(elem);
            // get the complex type definition
            XSTypeDefinition xtd = elem.getTypeDefinition();
            if (xtd == null) {
                 System.out.println(" XTD IS NULL ");
            } else {
                XSComplexTypeDefinition xctd = (XSComplexTypeDefinition)xtd;
                String sNestedAnnotation = this.getAnnotationsOfType(xctd);
                XSObjectList attrs = xctd.getAttributeUses();
                for(int i = 0; i < attrs.getLength(); i++){
                    XSAttributeUse attrUse = (XSAttributeUse)attrs.item(i);
                    XSAttributeDeclaration attr = attrUse.getAttrDeclaration() ;
                    eInfo.getAttributes().put(
                            attr.getName(), 
                            new AttributeInfo(
                                attr.getName(),
                                attr.getTypeDefinition().getName(),
                                attrUse.getRequired()
                            )
                    );
                    //System.out.println("Attr name: " + attr.getName() + " Type: " + attr.getTypeDefinition().getName()) ;
                }
            }
            //xcp
            //System.out.println(" CT NAME = "  + xcp.getName());
            elements.put(eInfo.getName(), eInfo);
            System.out.println("Key : " + entry.getKey() + " Value :" + elem);
        }
        this.documentInfo = new DocumentInfo(elements);
    }
    /*
    public void elements() {
        XSNamedMap map = this.fSchema.getComponents(ELEMENT_DECLARATION);
        Iterator<Map.Entry<QName, XSElementDecl>> iterator = 
                map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<QName, XSElementDecl> entry = 
                    (Map.Entry<QName,XSElementDecl>)iterator.next();
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
        
    }
    */
    
    
}
