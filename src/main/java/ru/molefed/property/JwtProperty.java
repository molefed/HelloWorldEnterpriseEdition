package ru.molefed.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperty {

	private String secret;
	private long expiresInMin;
	private long refreshExpiresInMin;
}
