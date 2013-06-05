package br.gov.frameworkdemoiselle.conversation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

public class BatePapoContext implements Context {
	
	private boolean conversaCorrendo = false;
	
	private ArrayList<Object> store = new ArrayList<Object>();

	public void comecarConversa(){
		//terminarConversa();
		
		conversaCorrendo = true;
	}
	
	public void terminarConversa(){
		conversaCorrendo = false;
		store.clear();
	}
	
	private <T> Class<?> getType(final Contextual<T> contextual) {
		Bean<T> bean = (Bean<T>) contextual;
		return bean.getBeanClass();
	}
	
	public Object getFromStore(Class<?> type){
		for (Object o : store){
			if (o.getClass().equals( type )){
				return o;
			}
		}
		
		return null;
	}
	
	public void addToStore(Object obj){
		store.add(obj);
	}

	//###############################

	@Override
	public Class<? extends Annotation> getScope() {
		return BatePapoScoped.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Contextual<T> contextual,CreationalContext<T> creationalContext) {
		T instance = null;

		if (!isActive()) {
			throw new ContextNotActiveException();
		}

		Class<?> type = getType(contextual);
		if ( getFromStore(type) != null ) {
			instance = (T) getFromStore(type);

		} else if (creationalContext != null) {
			instance = contextual.create(creationalContext);
			addToStore(instance);
		}

		return instance;
	}

	@Override
	public <T> T get(Contextual<T> contextual) {
		return null;
	}

	@Override
	public boolean isActive() {
		return conversaCorrendo;
	}

}
