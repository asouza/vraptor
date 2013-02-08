package br.com.caelum.vraptor.serialization.xstream;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * for DI purposes
 */
@Component @ApplicationScoped
public class NullConverter implements SingleValueConverter {
    public String toString(Object o) {return null;}

    public Object fromString(String s) {return null;}

    public boolean canConvert(Class aClass) {return false;}
}