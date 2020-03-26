package org.launchcode.liftoff.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.liftoff.web.rest.TestUtil;

public class EventCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventCategoryDTO.class);
        EventCategoryDTO eventCategoryDTO1 = new EventCategoryDTO();
        eventCategoryDTO1.setId(1L);
        EventCategoryDTO eventCategoryDTO2 = new EventCategoryDTO();
        assertThat(eventCategoryDTO1).isNotEqualTo(eventCategoryDTO2);
        eventCategoryDTO2.setId(eventCategoryDTO1.getId());
        assertThat(eventCategoryDTO1).isEqualTo(eventCategoryDTO2);
        eventCategoryDTO2.setId(2L);
        assertThat(eventCategoryDTO1).isNotEqualTo(eventCategoryDTO2);
        eventCategoryDTO1.setId(null);
        assertThat(eventCategoryDTO1).isNotEqualTo(eventCategoryDTO2);
    }
}
