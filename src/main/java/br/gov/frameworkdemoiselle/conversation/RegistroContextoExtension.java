package br.gov.frameworkdemoiselle.conversation;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class RegistroContextoExtension implements Extension {
	
	private BatePapoContext batePapoContext = new BatePapoContext();
	
	public void registrarContexto( @Observes AfterBeanDiscovery event ){
		
		batePapoContext.terminarConversa();
		
		event.addContext(batePapoContext);
		
	}
	
	public BatePapoContext getContext(){
		return batePapoContext;
	}

}
