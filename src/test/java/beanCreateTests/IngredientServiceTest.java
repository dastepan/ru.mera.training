package beanCreateTests;

        import org.junit.Assert;
        import org.junit.Test;
        import org.junit.runner.RunWith;

        import org.pizza.service.impl.IngredientServiceImpl;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.ApplicationContext;
        import org.springframework.test.context.ContextConfiguration;
        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class IngredientServiceTest {
    @Autowired
    private ApplicationContext cntx;

    @Test
    public void testCreateBean(){
        IngredientServiceImpl bean=cntx.getBean("ingredientService", IngredientServiceImpl.class);
        Assert.assertNotNull(bean);

        bean = (IngredientServiceImpl) cntx.getBean("ingredientService");
        Assert.assertNotNull(bean);
    }
}
