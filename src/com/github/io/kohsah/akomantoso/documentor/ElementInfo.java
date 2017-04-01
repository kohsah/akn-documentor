package com.github.io.kohsah.akomantoso.documentor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author ashok
 */
public class ElementInfo {
    private String name;
    private String namespace;
    HashMap<String, AttributeInfo> attributes = new HashMap<>(0);
    
    public ElementInfo() {
        name = "";
        namespace = "";
    }
    
    public ElementInfo(String namespace, String name, List<AttributeInfo> attrs) {
        this.namespace = namespace;
        this.name = name;
        attrs.stream().forEach((AttributeInfo attr) -> {
            attributes.put(attr.getName(), attr);
        });
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return the attributes
     */
    public HashMap<String,AttributeInfo> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(HashMap<String,AttributeInfo> attributes) {
        this.attributes = attributes;
    }
}
