package repositroy;

import com.hyc.Application;
import com.hyc.entity.User;
import com.hyc.repositroy.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void findByUsername() {
        User admin = userRepository.findByUsername("admin");
        System.out.println();
    }
}