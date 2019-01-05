package ru.skogmark.common.config.adapter;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class LocalTimeAdapterTest {
    @Test
    public void shouldConvertStringToLocalTime() {
        LocalTimeAdapter adapter = new LocalTimeAdapter();
        LocalTime time0 = adapter.unmarshal("2:00:00");
        LocalTime time1 = adapter.unmarshal("13:51:55");
        LocalTime time2 = adapter.unmarshal("23:30:00");
        LocalTime time3 = adapter.unmarshal("02:00:00");
        LocalTime time4 = adapter.unmarshal("2:00");
        LocalTime time5 = adapter.unmarshal("14:59");

        assertEquals(LocalTime.of(2, 0, 0), time0);
        assertEquals(LocalTime.of(13, 51, 55), time1);
        assertEquals(LocalTime.of(23, 30, 0), time2);
        assertEquals(LocalTime.of(2, 0, 0), time3);
        assertEquals(LocalTime.of(2, 0), time4);
        assertEquals(LocalTime.of(14, 59), time5);
    }

    @Test
    public void shouldConvertLocalTimeToString() {
        LocalTimeAdapter adapter = new LocalTimeAdapter();
        String time0 = adapter.marshal(LocalTime.of(2, 0, 0));
        String time1 = adapter.marshal(LocalTime.of(13, 51, 55));
        String time2 = adapter.marshal(LocalTime.of(23, 30, 0));
        String time3 = adapter.marshal(LocalTime.of(2, 0, 0));

        assertEquals("2:00:00", time0);
        assertEquals("13:51:55", time1);
        assertEquals("23:30:00", time2);
        assertEquals("2:00:00", time3);
    }
}