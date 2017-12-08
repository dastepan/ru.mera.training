package beanCreateTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.pizza.service.impl.IngredientServiceImpl;
import org.pizza.service.impl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class MenuServiceTest {
    @Autowired
    private ApplicationContext cntx;

    @Test
    public void testCreateBean(){
        MenuServiceImpl bean=cntx.getBean("menuService", MenuServiceImpl.class);
        Assert.assertNotNull(bean);

        bean = (MenuServiceImpl) cntx.getBean("menuService");
        Assert.assertNotNull(bean);
    }
}
