package ru.molefed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShedulerService {

	private final NeedSendEmailService needSendEmailService;

	@Scheduled(initialDelay = 1000 * 20, fixedDelay = 1000 * 3)
	public void sendingEmail() {
		log.debug("Start email sending");
		try {
			needSendEmailService.sending();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			log.debug("Stop email sending");
		}
	}
}
