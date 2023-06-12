package com.relyme.linkOccupation.utils.bean;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class BeanCopyUtil extends BeanUtilsBean {

    private static final Logger log = LoggerFactory.getLogger(BeanCopyUtil.class);

    /**
     *  
     *
     * @param dest           目标对象
     * @param orig           源对象
     * @param ignoreNullFlag 是否忽略null值
     * @param identityKey 忽略主键值 ，特别是主键为数字类型时
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void copyProperties(Object dest, Object orig, boolean ignoreNullFlag,String identityKey) throws IllegalAccessException, InvocationTargetException {


// Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + "," + ignoreNullFlag + ")");
        }


        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors =
                    ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if(name.equals(identityKey)){
                    continue;
                }
                // Need to check isReadable() for WrapDynaBean
                // (see Jira issue# BEANUTILS-61)
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if (ignoreNullFlag) {
                        if (value != null) {
                            copyProperty(dest, name, value);
                        }
                    } else {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else if (orig instanceof Map) {
            @SuppressWarnings("unchecked")
            // Map properties are always of type <String, Object>
            Map<String, Object> propMap = (Map<String, Object>) orig;
            for (Map.Entry<String, Object> entry : propMap.entrySet()) {
                String name = entry.getKey();
                if(name.equals(identityKey)){
                    continue;
                }
                Object value = entry.getValue();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    if (ignoreNullFlag) {
                        if (value != null) {
                            copyProperty(dest, name, value);
                        }
                    } else {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor[] origDescriptors = getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if(name.equals(identityKey)){
                    continue;
                }
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value = getPropertyUtils().getSimpleProperty(orig, name);
                        if (ignoreNullFlag) {
                            if (value != null) {
                                copyProperty(dest, name, value);
                            }
                        } else {
                            copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        // Should not happen
                    }
                }
            }
        }
    }


    /**
     *  
     *
     * @param dest           目标对象
     * @param orig           源对象
     * @param ignoreNullFlag 是否忽略null值
     * @param notincloud 忽略主键值 ，特别是主键为数字类型时
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void copyProperties(Object dest, Object orig, boolean ignoreNullFlag,String[] notincloud) throws IllegalAccessException, InvocationTargetException {


// Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + "," + ignoreNullFlag + ")");
        }


        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors =
                    ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();

                boolean iscontinue = false;
                for (String uclound: notincloud) {
                    if(name.equals(uclound)){
                        iscontinue = true;
                    }
                }
                if(iscontinue){
                    continue;
                }

                // Need to check isReadable() for WrapDynaBean
                // (see Jira issue# BEANUTILS-61)
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if (ignoreNullFlag) {
                        if (value != null) {
                            copyProperty(dest, name, value);
                        }
                    } else {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else if (orig instanceof Map) {
            @SuppressWarnings("unchecked")
            // Map properties are always of type <String, Object>
                    Map<String, Object> propMap = (Map<String, Object>) orig;
            for (Map.Entry<String, Object> entry : propMap.entrySet()) {
                String name = entry.getKey();
                boolean iscontinue = false;
                for (String uclound: notincloud) {
                    if(name.equals(uclound)){
                        iscontinue = true;
                    }
                }
                if(iscontinue){
                    continue;
                }
                Object value = entry.getValue();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    if (ignoreNullFlag) {
                        if (value != null) {
                            copyProperty(dest, name, value);
                        }
                    } else {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor[] origDescriptors = getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                boolean iscontinue = false;
                for (String uclound: notincloud) {
                    if(name.equals(uclound)){
                        iscontinue = true;
                    }
                }
                if(iscontinue){
                    continue;
                }
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value = getPropertyUtils().getSimpleProperty(orig, name);
                        if (ignoreNullFlag) {
                            if (value != null) {
                                copyProperty(dest, name, value);
                            }
                        } else {
                            copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        // Should not happen
                    }
                }
            }
        }
    }


    /**
     *  
     *
     * @param dest           目标对象
     * @param orig           源对象
     * @param ignoreNullFlag 是否忽略null值
     * @param ignoreEmptyFlag 是否忽略""值
     * @param notincloud 忽略主键值 ，特别是主键为数字类型时
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void copyProperties(Object dest, Object orig, boolean ignoreNullFlag,boolean ignoreEmptyFlag,String[] notincloud) throws IllegalAccessException, InvocationTargetException {


// Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + "," + ignoreNullFlag + ")");
        }


        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors =
                    ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();

                boolean iscontinue = false;
                for (String uclound: notincloud) {
                    if(name.equals(uclound)){
                        iscontinue = true;
                    }
                }
                if(iscontinue){
                    continue;
                }

                // Need to check isReadable() for WrapDynaBean
                // (see Jira issue# BEANUTILS-61)
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    //如果是字符串并且为空则跳过赋值
                    if(ignoreEmptyFlag && origDescriptors[i].getType().equals(Character.TYPE)){
                        continue;
                    }
                    if (ignoreNullFlag) {
                        if (value != null) {
                            copyProperty(dest, name, value);
                        }
                    } else {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else if (orig instanceof Map) {
            @SuppressWarnings("unchecked")
            // Map properties are always of type <String, Object>
                    Map<String, Object> propMap = (Map<String, Object>) orig;
            for (Map.Entry<String, Object> entry : propMap.entrySet()) {
                String name = entry.getKey();
                boolean iscontinue = false;
                for (String uclound: notincloud) {
                    if(name.equals(uclound)){
                        iscontinue = true;
                    }
                }
                if(iscontinue){
                    continue;
                }
                Object value = entry.getValue();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    if (ignoreNullFlag) {
                        if (value != null) {
                            copyProperty(dest, name, value);
                        }
                    } else {
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor[] origDescriptors = getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                boolean iscontinue = false;
                for (String uclound: notincloud) {
                    if(name.equals(uclound)){
                        iscontinue = true;
                    }
                }
                if(iscontinue){
                    continue;
                }
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value = getPropertyUtils().getSimpleProperty(orig, name);
                        //如果是字符串并且为空则跳过赋值
                        if(ignoreEmptyFlag && origDescriptors[i].getPropertyType().equals(String.class)){
                            if(value == null || value.toString().trim().length() == 0){
                                continue;
                            }
                        }
                        if (ignoreNullFlag) {
                            if (value != null) {
                                copyProperty(dest, name, value);
                            }
                        } else {
                            copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        // Should not happen
                    }
                }
            }
        }
    }
}
