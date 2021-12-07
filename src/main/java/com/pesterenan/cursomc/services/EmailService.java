package com.pesterenan.cursomc.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Pedido;

@Service
public interface EmailService {

	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
