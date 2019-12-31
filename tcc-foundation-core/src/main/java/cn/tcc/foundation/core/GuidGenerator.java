package cn.tcc.foundation.core;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class GuidGenerator {

    /**
     *
     * @return
     */
    public static String newGuid(){
        UUID uuid = Generators.timeBasedGenerator().generate();
        String guid = String.format("%s", uuid.toString().replace("-",""));
        return guid;
    }
}
