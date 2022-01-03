package ru.molefed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.persister.entity.email.NeedSendEmail;
import ru.molefed.persister.repository.email.NeedSendEmailRepository;
import ru.molefed.utils.DateUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NeedSendEmailService {

	private final NeedSendEmailRepository needSendEmailRepository;
	private final TemplateService templateService;
	private final JavaMailSender javaMailSender;

	@Transactional
	public void save(String email, EmailTemplate emailTemplate, Map<String, String> params) {

		NeedSendEmail needSendEmail = new NeedSendEmail();
		needSendEmail.setEmail(email);
		needSendEmail.setTitle(templateService.getFilledTemplate(emailTemplate.name() + "_title", params));
		needSendEmail.setBody(templateService.getFilledTemplate(emailTemplate.name() + "_body", params));
		needSendEmail.setCreated(DateUtils.now());

		needSendEmailRepository.save(needSendEmail);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void sending() {
		List<NeedSendEmail> needSendEmailList = needSendEmailRepository.findByOrderByCreated(
				Pageable.ofSize(37));

		Set<Long> forRemove = needSendEmailList.stream()
				.map(NeedSendEmail::getId)
				.collect(Collectors.toSet());

		for (NeedSendEmail needSendEmail : needSendEmailList) {
			try {
				sendEmail(needSendEmail.getEmail(),
						  needSendEmail.getTitle(),
						  needSendEmail.getBody());
			} catch (Throwable e) {
				forRemove.remove(needSendEmail.getId());
				log.error(e.getMessage(), e);
			}
		}

		if (!forRemove.isEmpty()) {
			needSendEmailRepository.deleteIn(forRemove);
		}
	}

	private void sendEmail(String email, String title, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@helloworld.com");
		message.setTo(email);
		message.setSubject(title);
		message.setText(body);

		javaMailSender.send(message);
	}
}
