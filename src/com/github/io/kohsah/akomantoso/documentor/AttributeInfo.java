/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.io.kohsah.akomantoso.documentor;

/**
 *
 * @author ashok
 */
public class AttributeInfo {
    private String name ; 
    private String type;
    private Boolean required;

    public AttributeInfo(String name, String type, boolean use) {
        this.name = name;
        this.type = type;
        this.required = use;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the use
     */
    public Boolean isRequired() {
        return required;
    }

    /**
     * @param use the use to set
     */
    public void setRequired(boolean use) {
        this.required = use;
    }
}
