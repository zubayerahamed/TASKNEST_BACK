package com.zayaanit.module.events;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Zubayer Ahamed
 * 
 * @since Jan 15, 2026
 */
public class FlexibleLocalTimeDeserializer extends JsonDeserializer<LocalTime> {
	private static final DateTimeFormatter[] FORMATS = { DateTimeFormatter.ofPattern("h:mm a"),
			DateTimeFormatter.ofPattern("HH:mm"), DateTimeFormatter.ofPattern("h:mm"),
			DateTimeFormatter.ISO_LOCAL_TIME };

	@Override
	public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String time = p.getText();
		for (DateTimeFormatter formatter : FORMATS) {
			try {
				return LocalTime.parse(time, formatter);
			} catch (DateTimeParseException e) {
				// Try next format
			}
		}
		throw new DateTimeParseException("Could not parse time: " + time, time, 0);
	}
}
