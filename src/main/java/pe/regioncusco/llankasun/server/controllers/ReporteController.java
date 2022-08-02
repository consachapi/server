package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.services.ReporteService;

import javax.annotation.security.RolesAllowed;

@Rest
@RequestMapping(ReporteController.REPORTES)
public class ReporteController {
    public static final String REPORTES = "/v1/reporte";
    private static final String META_RESPONSABLE = "/meta/responsable";
    private static final String META_RESPONSABLE_PDF = "/meta/documento";
    private static final String RESPONSABLE_TODO = "/meta/responsable/todo";

    @Autowired private ReporteService reporteService;

    @GetMapping(META_RESPONSABLE)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public ResponseEntity<?> findAllResidenteByMeta(@RequestParam("meta") String meta, @RequestParam("anio") Integer anio, @RequestParam("mes") Integer mes){
        return new ResponseEntity<>(reporteService.findAllResidenteByMeta(meta, anio, mes), HttpStatus.OK);
    }

    @GetMapping(META_RESPONSABLE_PDF)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public String reportDocument(@RequestParam("meta") String meta, @RequestParam("anio") Integer anio, @RequestParam("mes") Integer mes){
        return reporteService.generateResidenteByMeta(meta, anio, mes);
    }

    @GetMapping(RESPONSABLE_TODO)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<?> findAllReportByResidente(@RequestParam("oficina") Long oficina, @RequestParam("anio") Integer anio, @RequestParam("mes") Integer mes){
        return new ResponseEntity<>(reporteService.findAllReportByResidente(oficina, anio, mes), HttpStatus.OK);
    }

}
