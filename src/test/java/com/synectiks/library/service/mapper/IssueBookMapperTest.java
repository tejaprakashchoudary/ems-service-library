package com.synectiks.library.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueBookMapperTest {

    private IssueBookMapper issueBookMapper;

    @BeforeEach
    public void setUp() {
        issueBookMapper = new IssueBookMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(issueBookMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(issueBookMapper.fromId(null)).isNull();
    }
}
