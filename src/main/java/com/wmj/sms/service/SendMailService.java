package com.wmj.sms.service;

import com.wmj.sms.dao.MailRequest;

public interface SendMailService {

    /**
     * 简单文本邮件
     *
     * @param mailRequest
     * @return
     */
    void sendSimpleMail(MailRequest mailRequest);


    /**
     * Html格式邮件,可带附件
     *
     * @param mailRequest
     * @return
     */
    void sendHtmlMail(MailRequest mailRequest);
}
