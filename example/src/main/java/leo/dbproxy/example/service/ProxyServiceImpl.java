package leo.dbproxy.example.service;

import leo.dbproxy.starter.annotation.Master;
import leo.dbproxy.starter.annotation.Slave;
import leo.dbproxy.example.domain.ProxyDbTest;
import leo.dbproxy.example.repository.ProxyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProxyServiceImpl implements ProxyService {

    private final ProxyRepository proxyRepository;

    @Autowired
    public ProxyServiceImpl(ProxyRepository proxyRepository) {
        log.info("construct ProxyServiceImpl");
        this.proxyRepository = proxyRepository;
    }


    @Override
    @Slave
    public List<ProxyDbTest> findAll() {
        return proxyRepository.findAll();
    }

    @Override
    @Master
    public void saveSomething() {

        //do some write and query
    }

}
