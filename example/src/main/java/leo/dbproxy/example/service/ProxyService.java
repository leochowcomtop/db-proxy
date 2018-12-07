package leo.dbproxy.example.service;

import leo.dbproxy.example.domain.ProxyDbTest;

import java.util.List;

public interface ProxyService {

    List<ProxyDbTest> findAll();

    void saveSomething();
}
