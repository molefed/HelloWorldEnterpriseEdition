package ru.molefed.service;

import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateService {

	private final freemarker.template.Configuration configuration;

	@SneakyThrows
	public String getFilledTemplate(String templateName, Map<String, String> params) {
		Template template = configuration.getTemplate(templateName.toLowerCase());
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
	}
}
