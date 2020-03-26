package org.launchcode.liftoff.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventCategoryMapperTest {

    private EventCategoryMapper eventCategoryMapper;

    @BeforeEach
    public void setUp() {
        eventCategoryMapper = new EventCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventCategoryMapper.fromId(null)).isNull();
    }
}
