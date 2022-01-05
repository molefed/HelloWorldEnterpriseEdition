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
	private final RefreshTokenService refreshTokenService;
	private final UserService userService;

	@Scheduled(initialDelay = 1000 * 20, fixedDelay = 1000 * 60)
	public void sendingEmail() {
		start("sendingEmail", needSendEmailService::sending);
	}

	@Scheduled(cron = "0 0/30 * * * *") // every 30 min
	public void deleteOldTokens() {
		start("deleteOldTokens", refreshTokenService::deleteOldTokens);
	}

	@Scheduled(cron = "0 0/45 * * * *") // every 45 min
	public void removeOldUserEmailValidStore() {
		start("removeOldUserEmailValidStore", userService::removeOldUserEmailValidStore);
	}

	private void start(String taskName, Runnable runnable) {
		log.debug("Start " + taskName);
		try {
			runnable.run();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			log.debug("Stop " + taskName);
		}
	}
}
