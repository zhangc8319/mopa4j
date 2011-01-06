package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.persistence.PersistenceManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Autowired
    private MoPA4J moPA4J;

    @Test
    public void doTest() {

        User user = new User();
        user.setBirthdate(new java.util.Date());
        user.setName("Luigi Fugaro");
        log.info("user: " + user);

        Nation nation = new Nation();
        nation.setCode("IT");
        nation.setName("ITALY");
        log.info("nation: " + nation);

        City city = new City();
        city.setCode("RM");
        city.setName("ROME");
        log.info("city: " + city);

        nation.setCity(city);
        log.info("nation: " + nation);

        user.setNation(nation);
        log.info("user: " + user);

        moPA4J.create(user);
        log.info("user: " + user);

//        QueryObject queryObject = QueryObject.newInstance();
//        queryObject.like("name", "L");
//
//        User u = new User();
//        City c = new City();
//        c.setName("ROME");
//        u.setCity(c);
//        queryObject.as(u);
//        log.info("queryObject: " + queryObject);
//
//        List<User> users = userQueryManager.find(queryObject);
//        log.info("users.size(): " + users.size());
//        for (User _user : users) {
//            log.info("_user: " + _user);
//        }

    }
}
