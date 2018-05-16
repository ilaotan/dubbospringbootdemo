package test.com.ilaotan;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.ilaotan.DubboConsumerDemo;
import org.junit.runner.RunWith;

/*
*
clean install -Dmaven.test.skip=true

*
* */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboConsumerDemo.class)
public class Base {

}
