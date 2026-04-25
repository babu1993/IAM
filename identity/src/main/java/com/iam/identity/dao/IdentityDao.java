package com.iam.identity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdentityDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getRootUser() {
        jdbcTemplate.execute("SELECT * FROM IAM.identities WHERE id = '00000000-0000-0000-0000-000000000001'");
        return "Passed";
    }


}
