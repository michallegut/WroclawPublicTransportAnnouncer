/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wroclawpublictransportannouncer.internal.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The {@link DataDownloader} is responsible for creating things and thing
 * handlers.
 *
 * @author Konrad Drozd, Marianna Dudzinska, Michal Legut, Przemyslaw Morkski - Initial contribution
 */
public class DataDownloader {

    private File downloadData(String url) throws MalformedURLException, IOException {
        File downloadedFile = new File("data/data.zip");
        FileUtils.copyURLToFile(new URL(url), downloadedFile);
        return downloadedFile;
    }

    private void extractDownloadedFile(File downloadedFile) throws IOException {
        ZipFile zipFile = new ZipFile(downloadedFile);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File entryDestination = new File("data", entry.getName());
            if (entry.isDirectory()) {
                entryDestination.mkdirs();
            } else {
                entryDestination.getParentFile().mkdirs();
                InputStream in = zipFile.getInputStream(entry);
                OutputStream out = new FileOutputStream(entryDestination);
                IOUtils.copy(in, out);
                IOUtils.closeQuietly(in);
                out.close();
            }
        }
        zipFile.close();
    }

    private StopTable parseData(String stop) throws ParserConfigurationException, SAXException, IOException {
        SortedSet<Record> workingDays = new TreeSet<>();
        SortedSet<Record> saturdays = new TreeSet<>();
        SortedSet<Record> sundays = new TreeSet<>();
        File extractedDirectiory = new File("data/XML-rozkladyjazdy");
        ArrayList<File> subfolders = new ArrayList<File>(Arrays.asList(extractedDirectiory.listFiles()));
        for (File subfolder : subfolders) {
            File xmlFile = new ArrayList<File>(Arrays.asList(subfolder.listFiles())).get(0);
            if (xmlFile.getName().endsWith("xml")) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("tabliczka");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getParentNode().getAttributes().getNamedItem("nazwa").getNodeValue().equalsIgnoreCase(stop)) {
                        String line = node.getParentNode().getParentNode().getParentNode().getAttributes()
                                .getNamedItem("nazwa").getNodeValue();
                        String[] directions = node.getParentNode().getParentNode().getAttributes().getNamedItem("nazwa")
                                .getNodeValue().split(" - ");
                        String direction = directions[directions.length - 1];
                        NodeList days = node.getChildNodes();
                        for (int j = 0; j < days.getLength(); j++) {
                            if (days.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                NodeList times = days.item(j).getChildNodes();
                                for (int k = 0; k < times.getLength(); k++) {
                                    if (times.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                        int hour = Integer
                                                .parseInt(times.item(k).getAttributes().getNamedItem("h").getNodeValue());
                                        NodeList minuteNodes = times.item(k).getChildNodes();
                                        for (int l = 0; l < minuteNodes.getLength(); l++) {
                                            if (minuteNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {
                                                int minute = Integer.parseInt(minuteNodes.item(l).getAttributes()
                                                        .getNamedItem("m").getNodeValue());
                                                if (days.item(j).getAttributes().getNamedItem("nazwa").getNodeValue()
                                                        .equalsIgnoreCase("w dni robocze")) {
                                                    workingDays.add(new Record(hour, minute, line, direction));
                                                } else if (days.item(j).getAttributes().getNamedItem("nazwa").getNodeValue()
                                                        .equalsIgnoreCase("sobota")) {
                                                    saturdays.add(new Record(hour, minute, line, direction));
                                                } else if (days.item(j).getAttributes().getNamedItem("nazwa").getNodeValue()
                                                        .equalsIgnoreCase("niedziela")) {
                                                    sundays.add(new Record(hour, minute, line, direction));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new StopTable(workingDays, saturdays, sundays);
    }

    public StopTable getData(String url, String stop) throws ParserConfigurationException, SAXException, IOException {
        File downloadedData = downloadData(url);
        extractDownloadedFile(downloadedData);
        return parseData(stop);
    }
}