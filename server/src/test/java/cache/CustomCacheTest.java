package cache;


import com.hyc.Application;
import com.hyc.config.shiro.cache.AuthorCustomCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class CustomCacheTest {

    @Test
    public void test(){
        AuthorCustomCache<Object, Object> objectObjectCustomCache = new AuthorCustomCache<>();
        System.out.println();
    }

}