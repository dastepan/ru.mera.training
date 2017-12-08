package repositoryTests;
import org.junit.runner.RunWith;
import org.pizza.repository.impl.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class PizzaRepoTest {
    @Autowired
    PizzaRepository repo;
}
