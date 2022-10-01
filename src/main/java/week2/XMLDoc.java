package week2;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class XMLDoc {

    /**
     * @param xmlElements ArrayList of CSV Values
     * @param elementName Name fixed for node tree
     * @return Final doc
     */
    public Document docBuilder(ArrayList<String[]> xmlElements, String elementName) throws ParserConfigurationException {
        if (elementName == null) {
            elementName = "element";
        }
        DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
        Document xmlDoc = xmlBuilder.newDocument();

        Element rootElement = xmlDoc.createElement("root");
        xmlDoc.appendChild(rootElement);
        Element mainElement = xmlDoc.createElement(elementName + "s");
        rootElement.appendChild(mainElement);

        boolean headerDefined = false;
        String[] header = new String[xmlElements.size()];

        //DOC Generation -> XML Generation of every ELEMENT
        for (String[] node : xmlElements) {
            if (headerDefined) {
                Element nodesElements = xmlDoc.createElement(elementName);
                mainElement.appendChild(nodesElements);

                for (int j = 0; j < node.length; j++) {
                    node[j] = node[j].replaceAll("\"", StringUtils.EMPTY).trim();
                    try {
                        Element nodesValues = xmlDoc.createElement(header[j]);
                        nodesElements.appendChild(nodesValues);
                        Text nodeTxt = xmlDoc.createTextNode(node[j]);
                        nodesValues.appendChild(nodeTxt);
                    } catch (Exception e) {
                        System.out.println(node[j]);
                        System.out.println(header[j]);
                    }

                }
            }
            //DOC Generation -> Array Generation of every COL Name for NODES
            else {
                header = node;
                for (int j = 0; j < node.length; j++) {
                    header[j] = header[j].replaceAll("[^a-zA-Z0-9]", StringUtils.EMPTY);

                    header[j] = "node" + header[j];

                }
                headerDefined = true;
            }
        }
        return (xmlDoc);
    }

}

