package com.synectiks.library.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.library.web.rest.TestUtil;

public class IssueBookDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueBookDTO.class);
        IssueBookDTO issueBookDTO1 = new IssueBookDTO();
        issueBookDTO1.setId(1L);
        IssueBookDTO issueBookDTO2 = new IssueBookDTO();
        assertThat(issueBookDTO1).isNotEqualTo(issueBookDTO2);
        issueBookDTO2.setId(issueBookDTO1.getId());
        assertThat(issueBookDTO1).isEqualTo(issueBookDTO2);
        issueBookDTO2.setId(2L);
        assertThat(issueBookDTO1).isNotEqualTo(issueBookDTO2);
        issueBookDTO1.setId(null);
        assertThat(issueBookDTO1).isNotEqualTo(issueBookDTO2);
    }
}
