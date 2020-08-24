package com.synectiks.library.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.library.web.rest.TestUtil;

public class IssueBookTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueBook.class);
        IssueBook issueBook1 = new IssueBook();
        issueBook1.setId(1L);
        IssueBook issueBook2 = new IssueBook();
        issueBook2.setId(issueBook1.getId());
        assertThat(issueBook1).isEqualTo(issueBook2);
        issueBook2.setId(2L);
        assertThat(issueBook1).isNotEqualTo(issueBook2);
        issueBook1.setId(null);
        assertThat(issueBook1).isNotEqualTo(issueBook2);
    }
}
