package repositoryTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pizza.model.Ingredient;
import org.pizza.repository.impl.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class IngredientRepoTest {
    @Autowired
    IngredientRepository repo;


    @Test
    public void testAddEntity(){
        Ingredient entity=new Ingredient();
        Assert.assertTrue(repo.save(entity)==entity);
    }
    @Test
    public void testExistsEntity(){
        Ingredient entity=repo.save(new Ingredient());
        Assert.assertTrue(repo.existsById(entity.getId()));
    }
    @Test
    public void testRemoveEntity(){
        Ingredient entity=repo.save(new Ingredient());
        repo.delete(entity);
        Assert.assertFalse(repo.existsById(entity.getId()));
    }
}
