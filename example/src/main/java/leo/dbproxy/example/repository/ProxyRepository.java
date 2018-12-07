package leo.dbproxy.example.repository;

import leo.dbproxy.example.domain.ProxyDbTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProxyRepository extends JpaRepository<ProxyDbTest, Long> {

}
