package com.econtact.authWeb.app.utils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

public final class WebUtils {
	public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
    
	public static <T> T getBean(final Class<T> beanClass) {
		final BeanManager beanManager = CDI.current().getBeanManager();
		final Bean bean = beanManager.resolve(beanManager.getBeans(beanClass));
		CreationalContext cCtx = beanManager.createCreationalContext(bean);
		T result = (T) beanManager.getReference(bean, beanClass, cCtx);
		return result;
	}
	
	private WebUtils(){
	}
}
