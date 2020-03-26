package org.launchcode.liftoff.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.liftoff.web.rest.TestUtil;

public class EventDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventDetailsDTO.class);
        EventDetailsDTO eventDetailsDTO1 = new EventDetailsDTO();
        eventDetailsDTO1.setId(1L);
        EventDetailsDTO eventDetailsDTO2 = new EventDetailsDTO();
        assertThat(eventDetailsDTO1).isNotEqualTo(eventDetailsDTO2);
        eventDetailsDTO2.setId(eventDetailsDTO1.getId());
        assertThat(eventDetailsDTO1).isEqualTo(eventDetailsDTO2);
        eventDetailsDTO2.setId(2L);
        assertThat(eventDetailsDTO1).isNotEqualTo(eventDetailsDTO2);
        eventDetailsDTO1.setId(null);
        assertThat(eventDetailsDTO1).isNotEqualTo(eventDetailsDTO2);
    }
}
