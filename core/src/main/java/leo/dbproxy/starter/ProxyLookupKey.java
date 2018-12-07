package leo.dbproxy.starter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProxyLookupKey {
    public boolean isForceMaster() {
        return isForceMaster;
    }

    private boolean isForceMaster = false;

    public boolean isForceSlave() {
        return !isForceMaster;
    }

}
