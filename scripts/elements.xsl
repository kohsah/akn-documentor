<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl"
    exclude-result-prefixes="xs xd" version="2.0">
    <xd:doc scope="stylesheet">
        <xd:desc>
            <xd:p><xd:b>Created on:</xd:b> Dec 15, 2016</xd:p>
            <xd:p><xd:b>Author:</xd:b> ashok</xd:p>
            <xd:p/>
        </xd:desc>
    </xd:doc>
    <xsl:variable name="tablecellbg" select="'#D3D3D3'"/>
    
    <xsl:attribute-set name="border">
        <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="paddings">
        <xsl:attribute name="padding-top">4px</xsl:attribute>
        <xsl:attribute name="padding-bottom">4px</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="cell-left" use-attribute-sets="paddings">
        <xsl:attribute name="text-align">right</xsl:attribute>
        <xsl:attribute name="padding-right">5px</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="cell-right" use-attribute-sets="paddings">
        <xsl:attribute name="text-align">left</xsl:attribute>
        <xsl:attribute name="padding-left">5px</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="aleft" use-attribute-sets="cell-left border" />
        
    <xsl:attribute-set name="aright" use-attribute-sets="cell-right border" />
    

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="my-page">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="my-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="10pt">
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="element">
        <fo:table break-after="page">
            <fo:table-column column-width="25%" />
            <fo:table-column column-width="75%"/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell xsl:use-attribute-sets="aleft">
                        <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block>Name</fo:block></fo:table-cell>
                    <fo:table-cell xsl:use-attribute-sets="aright">
                        <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block font-style="italic" font-weight="bold"><xsl:value-of select="@name"/></fo:block></fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >OASIS Description</fo:block></fo:table-cell>
                      <fo:table-cell xsl:use-attribute-sets="aright">
                          <fo:block font-style="italic"></fo:block>
                      </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >Akn4UN usage</fo:block></fo:table-cell>
                      <fo:table-cell xsl:use-attribute-sets="aright">
                          <fo:block font-style="italic"></fo:block></fo:table-cell>
                </fo:table-row>
                
                <xsl:call-template name="show-attributes" />
                
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >Not used Attributes</fo:block></fo:table-cell>
                      <fo:table-cell xsl:use-attribute-sets="aright">
                          <fo:block font-style="italic"></fo:block></fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >Connected Elements</fo:block></fo:table-cell>
                      <fo:table-cell xsl:use-attribute-sets="aright">
                          <fo:block font-style="italic"></fo:block></fo:table-cell>
                </fo:table-row>    
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >Rules</fo:block></fo:table-cell>
                      <fo:table-cell xsl:use-attribute-sets="aright">
                          <fo:block font-style="italic"></fo:block></fo:table-cell>
                </fo:table-row>                
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >Improper Use</fo:block></fo:table-cell>
                      <fo:table-cell xsl:use-attribute-sets="aright">
                          <fo:block font-style="italic"></fo:block></fo:table-cell>
                </fo:table-row>    
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >Example(s)</fo:block></fo:table-cell>
                      <fo:table-cell xsl:use-attribute-sets="aright">
                          <fo:block font-style="italic"></fo:block></fo:table-cell>
                </fo:table-row>                
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    <xsl:template name="show-attributes">
        <xsl:for-each select="./attribute">
                <fo:table-row>
                      <fo:table-cell xsl:use-attribute-sets="aleft">
                         <xsl:attribute name="background-color" select="$tablecellbg" />
                        <fo:block >Used Attributes</fo:block>
                      </fo:table-cell>
                       <fo:table-cell xsl:use-attribute-sets="border">
                            <fo:block margin-left="10px" font-style="italic"><xsl:value-of select="@name"/></fo:block>
                            <fo:table padding-left="0px" >
                                <fo:table-column  width="10%"/>
                                <fo:table-column  width="40%"/>
                                <fo:table-column width="10%" />
                                <fo:table-column width="40%" />
                                <fo:table-body>
                                    <fo:table-row>
                                        <fo:table-cell padding-left="10px" xsl:use-attribute-sets="border">
                                            <fo:block>Format: </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell xsl:use-attribute-sets="border">
                                            <fo:block><xsl:value-of select="@type"/></fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell xsl:use-attribute-sets="border">
                                            <fo:block>Required: </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell xsl:use-attribute-sets="border">
                                            <fo:block><xsl:value-of select="@required"/></fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                            <fo:block margin-left="10px" font-style="italic">Description...</fo:block>
                        </fo:table-cell>
                </fo:table-row>                
        </xsl:for-each>
    
        
    </xsl:template>
    
</xsl:stylesheet>
