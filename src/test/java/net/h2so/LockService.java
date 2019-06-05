package net.h2so;

import net.h2so.alphalock.annotation.AlphaLock;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Auther mikicomo
 * @Date 2019-06-04 15:23
 */
@Service
public class LockService {

    @AlphaLock(maxWaitTime = 10, leaseTime = 60, keys = {"#param", "#param"})
    public String getValue(String param) throws Exception {

        System.out.printf("get value param start...");
        Thread.sleep(1000 * 10);
        System.out.printf("get value param end...");
        return "success";
    }

    @AlphaLock(maxWaitTime = 10, leaseTime = 60, keys = {"#object.id", "#object.name", "#object.ok"})
    public String getValue(Object object) throws Exception {

        System.out.printf("get value object start...");
        Thread.sleep(1000 * 3);
        System.out.printf("get value object end...");
        return "success";
    }
}
