package com.main.email.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.main.vo.MailEvent;


@Service
public class EmailListener {

	@Autowired
	private JavaMailSender javaMailSender;

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void onMessage(MailEvent mail) {
		String subject="Leave Request Initiated || Leave Request Id - "+mail.getLeaveReqId();
		String to = mail.getEmailId();
		String status = mail.getStatus();
		if("Approved".equalsIgnoreCase(status))
			subject=subject.replace("Initiated","Approved");
		else if("Rejected".equalsIgnoreCase(status))
			subject=subject.replace("Initiated","Rejected");
		String body=mail.getMsg();
		sendEmail(to,subject,body);
	}
	
	public void sendEmail(String to,String subject,String body)
	{	
		try
		{
			SimpleMailMessage mail =new SimpleMailMessage();
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(body);
			javaMailSender.send(mail);
			System.out.println("Email Message sent successfully");
		}
		catch(Exception e)
		{
			System.out.println("Error while sending Email :- "+e);
		}
	}
}
