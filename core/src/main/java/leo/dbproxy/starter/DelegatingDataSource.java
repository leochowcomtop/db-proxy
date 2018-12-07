package leo.dbproxy.starter;

import com.zaxxer.hikari.HikariDataSource;
import leo.dbproxy.starter.aop.ProxyAccessContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class DelegatingDataSource extends AbstractRoutingDataSource {


    private static final String MASTER_KEY = "MASTER";
    private static final String SLAVE_KEY_PREFIX = "SLAVE_";
    private final int SLAVE_CNT;


    @Override
    protected Object determineCurrentLookupKey() {
        ProxyLookupKey lookupKey = ProxyAccessContext.peek();
        if (null == lookupKey) {
            return getLookupKeyByTransaction();
        } else if (lookupKey.isForceMaster()) {
            return MASTER_KEY;
        } else if (lookupKey.isForceSlave()) {
            return SLAVE_KEY_PREFIX + Thread.currentThread().getId() % SLAVE_CNT;
        } else {
            throw new IllegalStateException("ProxyLookupKey dbtarget can't be here!");
        }

    }

    private Object getLookupKeyByTransaction() {
        long currThreadId = Thread.currentThread().getId();
        boolean isActualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        boolean isCurrentTransactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isActualTransactionActive || !isCurrentTransactionReadOnly) {
            /**
             * 没有明确标明为master的，不具备传播性？
             * 考虑点：
             * 1.系统会自行选择master，但是尽可能多的给slave的机会
             */
//            ProxyAccessContext.push(ProxyLookupKey.builder().isForceMaster(true).build());
            return MASTER_KEY;
        } else {
            return SLAVE_KEY_PREFIX + currThreadId % SLAVE_CNT;
        }
    }


    DelegatingDataSource(DBProperties properties) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(MASTER_KEY, properties.getMaster());
        List<HikariDataSource> slaves = properties.getSlaves();
        SLAVE_CNT = slaves.size();

        for (int i = 0; i < SLAVE_CNT; i++) {
            targetDataSources.putIfAbsent(SLAVE_KEY_PREFIX + i, slaves.get(i));
        }
        if (!targetDataSources.containsKey(MASTER_KEY)) {
            throw new RuntimeException("must contains one Master");
        }
        if (!targetDataSources.containsKey(SLAVE_KEY_PREFIX + 0)) {
            throw new RuntimeException("must contains one Slave");
        }
        this.setTargetDataSources(targetDataSources);
    }
}
