package beanCreateTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.pizza.service.impl.IngredientServiceImpl;
import org.pizza.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class OrderServiceTest {
    @Autowired
    private ApplicationContext cntx;

    @Test
    public void testCreateBean(){
        OrderServiceImpl bean=cntx.getBean("orderService", OrderServiceImpl.class);
        Assert.assertNotNull(bean);

        bean = (OrderServiceImpl) cntx.getBean("orderService");
        Assert.assertNotNull(bean);
    }
}
