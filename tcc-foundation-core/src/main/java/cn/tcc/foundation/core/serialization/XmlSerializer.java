package cn.tcc.foundation.core.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

/**
 * @author shawn.xiao
 * @date 2017/12/21
 */
public class XmlSerializer {

    public static String toXml(Object object) {
        ObjectMapper xmlMapper = new XmlMapper();
        String xml = "";
        try {
            xml = xmlMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static Object fromXml(String xml, Class<?> clazz) {
        XmlMapper mapper = new XmlMapper();
        Object retunValue = null;
        try {
            retunValue = mapper.readValue(xml, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retunValue;
    }
}
