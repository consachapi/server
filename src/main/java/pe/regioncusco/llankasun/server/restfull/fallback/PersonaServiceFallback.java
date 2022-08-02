package pe.regioncusco.llankasun.server.restfull.fallback;

import pe.regioncusco.llankasun.server.restfull.PersonaFeignClient;
import pe.regioncusco.llankasun.server.restfull.models.PersonaData;
import pe.regioncusco.llankasun.server.restfull.models.PersonaTpl;

import java.util.ArrayList;
import java.util.List;

public class PersonaServiceFallback implements PersonaFeignClient {
    @Override
    public PersonaTpl getPersona(String ndocumento) {
        PersonaData personaData = new PersonaData();
        personaData.setNumeroDocumento("00000000");
        personaData.setApellidos("PEREZ");
        personaData.setNombres("JUAN");
        personaData.setOficinaAbreviatura("SGMTI");
        personaData.setOficina("OFICINA FUNCIONAL DE INFORMATICA");
        personaData.setArea("SUBGERENCIA DE MODERNIZACION Y TECNOLOGIAS DE LA INFORMACION");
        personaData.setEmail("juan@regioncusco.gob.pe");
        personaData.setTelefono("084224455");
        personaData.setDireccion("WANCHAQ");

        List<PersonaData> data = new ArrayList<>();
        data.add(personaData);

        PersonaTpl personaTpl = new PersonaTpl();
        personaTpl.setSuccess(false);
        personaTpl.setMsg("Ocurrio un error en el lado del proveedor");
        personaTpl.setData(data);
        personaTpl.setTotal(1);
        return personaTpl;
    }
}
