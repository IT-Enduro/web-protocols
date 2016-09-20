package ru.romanow.protocols.rpc.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

/**Î
 * Created by ronin on 20.09.16
 */
@Service
public class TestService
        implements RemoteService {

    @Override
    public int processRequest() {
        return RandomUtils.nextInt(0, 100);
    }
}
