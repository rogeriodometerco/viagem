package teste;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Teste {

	public static void main(String[] args) {
		try {

			Properties jndiProperties = new Properties();
			jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

				Context jndi = new InitialContext(jndiProperties);
				jndi.lookup("java:global/EventoService");

//				Properties jndiProps = new Properties();
//				jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
				//jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
				//Context ctx = new InitialContext(jndiProps);
				Context ctx = new InitialContext();
				// lookup
				ctx.lookup("java:global/EventoService");

				//final Hashtable jndiProperties = new Hashtable();
				jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
				final Context context = new InitialContext(jndiProperties);
				context.lookup("java:module/EventoService");
				//Ejb.lookup(servico.LocalizacaoService.class);
				//EventoService eventoService = Ejb.lookup(EventoService.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
