package pe.regioncusco.llankasun.server.restfull.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;

public class PersonaServiceFallbackFactory implements FallbackFactory<PersonaServiceFallback> {
    @Override
    public PersonaServiceFallback create(Throwable cause) {
        return new PersonaServiceFallback();
    }
}
