package com.lwreid.platform.selenium.pages.main;

import com.lwreid.platform.selenium.pages.BasePage;
import com.lwreid.platform.selenium.pages.BaseTest;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Nikita Ovsyannikov on 21.09.2016.
 */
public class ObjectPool {

    private ObjectsCollection<BasePage> pages = new ObjectsCollection<>();
    private static ObjectPool pool;

    private ObjectPool() {}

    public static ObjectPool getInstance() {
        pool = Optional.ofNullable(pool).orElse(new ObjectPool());
        return pool;
    }

    public BasePage getObject(Class<? extends BasePage> typeReference) {
       return pages.getInstance(typeReference);
    }

    private class ObjectsCollection<B> {
        private Map<Class<? extends B>, B> collection = new HashMap<>();

        @SuppressWarnings("unchecked")
        <T extends B> T getInstance(Class<T> typeReference) {
            T result = (T) collection.get(typeReference);
            if (result == null) {
                try {
                    Constructor<T> constructor = typeReference.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    result = constructor.newInstance();
                    PageFactory.initElements(BaseTest.getDriver(), result);
                    collection.put(typeReference, result);
                    return result;
                } catch (Exception e) {
                    throw new AssertionError(String.format("Wasn't able to instantiate [%s] class from ObjectCollection. " +
                            "See inner exception for details.", typeReference.getTypeName()), e);
                }
            } else {
                return result;
            }
        }
    }
}
