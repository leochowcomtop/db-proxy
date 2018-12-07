package leo.dbproxy.starter.aop;

import leo.dbproxy.starter.ProxyLookupKey;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;


@Slf4j
public class ProxyAccessContext {

    private static final ThreadLocal<Deque<ProxyLookupKey>> proxyLookupKeyThreadLocal = new ThreadLocal<Deque<ProxyLookupKey>>() {
        @Override
        protected Deque<ProxyLookupKey> initialValue() {
            return new ArrayDeque<ProxyLookupKey>();
        }
    };

    public static ProxyLookupKey peek() {
        return proxyLookupKeyThreadLocal.get().peek();
    }

    static void push(ProxyLookupKey key) {
        log.debug("push new lookupkey:{}", key);
        proxyLookupKeyThreadLocal.get().push(key);
    }

    static ProxyLookupKey pop() {
        ProxyLookupKey proxyLookupKey = proxyLookupKeyThreadLocal.get().pop();
        log.debug("pop lookupkey:{}", proxyLookupKey);
        return proxyLookupKey;
    }

}
