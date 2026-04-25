package com.iam.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;


@Component
public class FormParser {

    private final Logger logger = LoggerFactory.getLogger(FormParser.class);

    public <T> T parserForm(MultiValueMap<String, String> form, Class<T> cls){

        try {
            Field[] fields = cls.getDeclaredFields();
            Constructor<T> constructor = cls.getConstructor();
            T obj = constructor.newInstance();
            for (Field field : fields) {
                String memberName = field.getName();
                if (form.containsKey(memberName)) {
                    Method m = obj.getClass().getMethod("set" + StringUtil.makeFirstCharUpperCase(memberName), String.class);
                    m.invoke(obj, form.getFirst(memberName));
                }
            }
            return obj;
        }
        catch(NoSuchMethodException e){
            logger.info(e.toString());
            throw new RuntimeException(e);
        } catch(InvocationTargetException | IllegalAccessException | InstantiationException e){
            throw new RuntimeException(e);
        }
    }
}
