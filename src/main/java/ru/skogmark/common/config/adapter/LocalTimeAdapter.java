package ru.skogmark.common.config.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    private final DateTimeFormatter deserializationFormatter = DateTimeFormatter.ofPattern("[H:mm:ss][H:mm]");
    private final DateTimeFormatter serializationFormatter = DateTimeFormatter.ofPattern("H:mm:ss");

    @Override
    public LocalTime unmarshal(String v) {
        return LocalTime.parse(v, deserializationFormatter);
    }

    @Override
    public String marshal(LocalTime v) {
        return serializationFormatter.format(v);
    }
}
