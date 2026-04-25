package com.iam.identity;

import com.iam.identity.dao.IdentityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IdentityDao identityDao;
    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping("/{userId}")
    public String getUser(@PathVariable String userId){
        logger.info("Fetching user :{}", userId);
        logger.info(identityDao.getRootUser());
        return userId;
    }
}