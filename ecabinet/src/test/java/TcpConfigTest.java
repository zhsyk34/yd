import com.yd.ecabinet.config.TcpConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TcpConfig.class)
public class TcpConfigTest {

    @Autowired
    private TcpConfig tcpConfig;

    @Test
    public void test() {
        System.out.println(tcpConfig.getPort());
    }
}