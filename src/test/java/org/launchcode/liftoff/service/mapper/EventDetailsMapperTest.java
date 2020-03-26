package org.launchcode.liftoff.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventDetailsMapperTest {

    private EventDetailsMapper eventDetailsMapper;

    @BeforeEach
    public void setUp() {
        eventDetailsMapper = new EventDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventDetailsMapper.fromId(null)).isNull();
    }
}
