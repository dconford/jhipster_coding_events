package org.launchcode.liftoff.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.liftoff.web.rest.TestUtil;

public class EventCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventCategory.class);
        EventCategory eventCategory1 = new EventCategory();
        eventCategory1.setId(1L);
        EventCategory eventCategory2 = new EventCategory();
        eventCategory2.setId(eventCategory1.getId());
        assertThat(eventCategory1).isEqualTo(eventCategory2);
        eventCategory2.setId(2L);
        assertThat(eventCategory1).isNotEqualTo(eventCategory2);
        eventCategory1.setId(null);
        assertThat(eventCategory1).isNotEqualTo(eventCategory2);
    }
}
