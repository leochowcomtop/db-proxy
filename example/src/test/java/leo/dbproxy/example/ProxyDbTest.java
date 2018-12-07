package leo.dbproxy.example;

import leo.dbproxy.example.repository.ProxyRepository;
import leo.dbproxy.example.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class
)
@Slf4j
public class ProxyDbTest {

    @Autowired
    ProxyRepository proxyRepository;

    @Autowired
    ProxyService proxyService;

    @Test
    public void testQuery() throws Exception {
        proxyRepository.save(new leo.dbproxy.example.domain.ProxyDbTest());
        proxyService.findAll();
    }

    @Test
    public void testSave()throws Exception {
        Thread.sleep(3000);
        proxyRepository.save(new leo.dbproxy.example.domain.ProxyDbTest());
    }

}
