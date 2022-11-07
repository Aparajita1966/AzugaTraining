package week2.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import week2.constant.GeneralConstant;
import week2.service.ConverterService;
import week2.service.ConverterServiceImpl;


/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class Convertor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Convertor.class);
    public static List<String> strArr = new ArrayList<>();

    public static void convertFile(boolean sendIndividualZipFile, String path, JSONArray docs, boolean generateZipFile, boolean sendEmail) throws IOException {
        String filePath = "." + File.separator + "." + File.separator + "." + File.separator + "OutputFiles" + File.separator;
        File file = new File(path);
        if (file.mkdir()) {
            LOGGER.info("Path created " + file.getAbsolutePath());
        }
        ConverterService converterService = new ConverterServiceImpl();
        try {
			converterService.convertJsonToCSV(docs, filePath + path + GeneralConstant.CSV);
		} catch (IOException e) {
			LOGGER.info("Exception occured while creating CSV file " + filePath + path + GeneralConstant.CSV);
			throw new IOException("Json not found");
		}
        try {
			converterService.convertToPDF(filePath + path + GeneralConstant.CSV, filePath + path + ".pdf");
		} catch (FileNotFoundException e2) {
			LOGGER.info("Exception occured while creating PDF file " + filePath + path + GeneralConstant.CSV); 

		}
        try {
			converterService.convertToXls(filePath + path + GeneralConstant.CSV, filePath + path + ".xls");
		} catch (IOException e1) {
			LOGGER.info("Exception occured while creating XLS file " + filePath + path + GeneralConstant.CSV); 
		}
        try {
			converterService.convertToHtml(filePath + path + GeneralConstant.CSV, filePath + path + ".html");
		} catch (IOException e) {
			LOGGER.info("Exception occured while creating HTML file " + filePath + path + GeneralConstant.CSV); 
		}
        try {
			converterService.convertToXML(filePath + path + GeneralConstant.CSV, filePath + path + ".xml");
		} catch (IOException e) {
			LOGGER.info("Exception occured while creating XML file " + filePath + path + GeneralConstant.CSV); 

		}
        String zipFile = generateZipAndUnzipFolder(path, generateZipFile, filePath);
        if (sendIndividualZipFile) {
            strArr = new ArrayList<>();
            strArr.add(zipFile);
            sendEmail(path, sendEmail);
        } else {
            strArr.add(zipFile);
        }
    }

    private static void sendEmail(String path, boolean sendEmail) {
        if (sendEmail) {
            String msg = "<h1>Hello,</h1>\n\n" +
                    "Kindly find enclosed the generated reports for BasketBall-" + path.split("/")[1] + " API.\n\n" + "Note: This is a system generated mail. Do not reply. \n\n" + "Regards,\nAparajita";
            String sub = (path.split("/")[1] + " Reports").toUpperCase();
            MailSender.sendEmail(strArr, "mishra.aparajita.0000@gmail.com", msg, sub);
        }
    }

    private static String generateZipAndUnzipFolder(String path, boolean generateZipFile, String filePath) {
        String zipFile = filePath + path.split("/")[1] + "_report.zip";
        if (generateZipFile) {
            File files = new File(filePath + path.split("/")[1] + "/");
            HttpClient.ZipFolder.zipFolder(zipFile, files, filePath + path.split("/")[1] + "/");

            Utility.UnzipFolder.unZipFolder(zipFile, filePath + path.split("/")[1] + "_unzip" + "/");
        }
        return zipFile;
    }

    public static class MailSendProperties {

        private static final Logger LOGGER = LoggerFactory.getLogger(MailSendProperties.class);

        boolean sendEmail(String msg, String sub, String to, List<String> file) {
            String host = "smtp.gmail.com";
            Properties properties = System.getProperties();
            LOGGER.debug("system properties" + properties);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.starttls.required", "true");
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            LOGGER.debug("system properties after changing" + properties);
            Session session = Session.getInstance(properties, new Authenticator() {
                /**
                 * this method authenticates with Google server
                 * @return authentication state
                 */
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("dummyEmail", "********");
                }
            });

            LOGGER.debug("Mail body is {}",msg);
            LOGGER.debug("Mail Subject is {}",sub);
                try {
                    // Create a default MimeMessage object.
                    Message message = new MimeMessage(session);
                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress("aparajitam@azuga.com"));

                    // Set To: header field of the header.
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(to));

                    // Set Subject: header field
                    message.setSubject(sub);

                    // Create the message part
                    BodyPart messageBodyPart = new MimeBodyPart();

                    // Now set the actual message
                    messageBodyPart.setText(msg);

                    // Create a multipar message
                    Multipart multipart = new MimeMultipart();

                    // Set text message part
                    multipart.addBodyPart(messageBodyPart);

                    extracted(file, message, multipart);

                    // Send message
                    Transport.send(message);
                } catch (MessagingException e) {
                    LOGGER.error("Error occurred while trying to send the mail {}", e);
                    return false;
                }
            LOGGER.info("Mail sent successfully");
            return true;
        }

        private static void extracted(List<String> file, Message message, Multipart multipart) throws MessagingException {
            for (String f:file) {
                LOGGER.debug("File attached in mail is {}",f);
                BodyPart messageBodyPart;
                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(f);
                messageBodyPart.setDataHandler(new DataHandler(source));
                File fileName = new File(f);
                messageBodyPart.setFileName(fileName.getName());
                multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                message.setContent(multipart);
            }
        }
    }

    public static class MailSender {

        private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

        public static void sendEmail(List<String> zipFile, String toEmail, String msg, String sub) {
            MailSendProperties mailSend = new MailSendProperties();
            if (!mailSend.sendEmail(msg, sub, toEmail, zipFile)) {
                LOGGER.info("Error occurred");
            }
        }
    }
}

