package edu.wctc.jls.MyEcomApp.service;

import edu.wctc.jls.MyEcomApp.exeption.InvalidParameterException;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Custom Service class to allow for email sending.
 *
 * @author Jennifer
 */
@Service("deleteNoticeEmailSender")
public class NotificationEmailSender {

    @Autowired
    private transient MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    /**
     * Custom method which is used to send notification email when a wine record
     * is deleted.
     *
     * @param data
     * @param objectName- name of wine deleted
     * @param objectPrice - price of wine deleted
     * @throws MailException
     * @throws InvalidParameterException - if any parameters are passed in as
     * null or empty
     */
    public void sendDeleteEmail(Object data, String objectName, String objectPrice) throws MailException, InvalidParameterException {
        if (data == null || objectName.isEmpty() || objectName == null || objectPrice.isEmpty() || objectPrice == null) {
            throw new InvalidParameterException();
        }
        String recordType = (String) data;

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setSubject(recordType + " " + objectName + " priced " + objectPrice + " record has been deleted");

        // set the messsage
        msg.setText("A successful attempt was completed to delete a " + recordType
                + " record on : " + new Date());

        try {
            mailSender.send(msg);
        } catch (NullPointerException ex) {
            throw new MailSendException(ex.getMessage());
        }
    }

    /**
     * Custom method to send an email when a mail message is edited.
     *
     * @param data
     * @param objectName- previous name of object being edited.
     * @param objectPrice - previous price of wine object being edited.
     * @throws MailException
     * @throws InvalidParameterException if any parameters come in as null or
     * empty.
     */
    public void sendEditEmail(Object data, String objectName, String objectPrice) throws MailException, InvalidParameterException {
        if (data == null || objectName.isEmpty() || objectName == null || objectPrice.isEmpty() || objectPrice == null) {
            throw new InvalidParameterException();
        }
        String recordType = (String) data;
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setSubject(recordType + " " + objectName + " priced " + objectPrice + " record has been edited");

        // set the messsage
        msg.setText("A successful attempt to edit  " + recordType
                + " record on : " + new Date() + " was completed.");

        try {
            mailSender.send(msg);
        } catch (NullPointerException ex) {
            throw new MailSendException(ex.getMessage());
        }
    }

    /**
     * getter for the mail sender object
     *
     * @return the mailSender ojbect
     */
    public MailSender getMailSender() {
        return mailSender;
    }

    /**
     * setter for the mailSender.
     *
     * @param mailSender- the mail sender object
     * @throws InvalidParameterException if mail sender is null.
     */
    public void setMailSender(MailSender mailSender) throws InvalidParameterException {
        if (mailSender == null) {
            throw new InvalidParameterException();
        }
        this.mailSender = mailSender;
    }

    /**
     * getter for the template message
     *
     * @return the template message
     */
    public SimpleMailMessage getTemplateMessage() {
        return templateMessage;
    }

    /**
     * Setter Method for template Message
     *
     * @param templateMessage
     * @throws InvalidParameterException if template message is null
     */
    public void setTemplateMessage(SimpleMailMessage templateMessage) throws InvalidParameterException {
        if (templateMessage == null) {
            throw new InvalidParameterException();
        }
        this.templateMessage = templateMessage;
    }

    /**
     * Standard overriden hashcode
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.mailSender);
        hash = 11 * hash + Objects.hashCode(this.templateMessage);
        return hash;
    }

    /**
     * Standard overridden equals
     *
     * @param obj the instantiated object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotificationEmailSender other = (NotificationEmailSender) obj;
        if (!Objects.equals(this.mailSender, other.mailSender)) {
            return false;
        }
        if (!Objects.equals(this.templateMessage, other.templateMessage)) {
            return false;
        }
        return true;
    }

    /**
     * Standard overridden toString()
     *
     * @return String of class values
     */
    @Override
    public String toString() {
        return "NotificationEmailSender{" + "mailSender=" + mailSender + ", templateMessage=" + templateMessage + '}';
    }

}
