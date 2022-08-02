package pe.regioncusco.llankasun.server.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.regioncusco.llankasun.server.commons.HeaderFooterPageEventHorizontal;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.commons.Utils;
import pe.regioncusco.llankasun.server.config.AccessTokenImpl;
import pe.regioncusco.llankasun.server.dtos.ResponsableDiarioDto;
import pe.regioncusco.llankasun.server.models.entities.*;
import pe.regioncusco.llankasun.server.models.entities.Meta;
import pe.regioncusco.llankasun.server.models.repositorys.ActividadRepository;
import pe.regioncusco.llankasun.server.models.repositorys.MetaDetalleRepository;
import pe.regioncusco.llankasun.server.models.repositorys.RegistroActividadRepository;
import pe.regioncusco.llankasun.server.models.repositorys.ResidenteMetaRepository;
import pe.regioncusco.llankasun.server.services.MetaService;
import pe.regioncusco.llankasun.server.services.OficinaService;
import pe.regioncusco.llankasun.server.services.ReporteService;

import java.io.ByteArrayOutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {
    private static final Logger LOG = LoggerFactory.getLogger(ReporteServiceImpl.class);

    @Autowired private AccessTokenImpl accessToken;
    @Autowired private MetaService metaService;
    @Autowired private ActividadRepository actividadRepository;
    @Autowired private RegistroActividadRepository registroActividadRepository;
    @Autowired private MetaDetalleRepository metaDetalleRepository;
    @Autowired private ResidenteMetaRepository residenteMetaRepository;
    @Autowired private OficinaService oficinaService;

    @Override
    public Meta findAllById(String id) {
        return null;
    }

    @Override
    public List<ResponsableDiarioDto> findAllResidenteByMeta(String meta, Integer anio, Integer mes) {
        Meta met = metaService.findMetaById(meta);
        LocalDate local = LocalDate.of(anio, mes, 15);
        Date dateConsulta = new Date(local.atStartOfDay(ZoneId.of("America/Lima")).toEpochSecond() * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateConsulta);

        int month = calendar.get(Calendar.MONDAY) + 1;
        int year = calendar.get(Calendar.YEAR);

        List<ResponsableDiarioDto> responsableDiarioDtos = new ArrayList<>();
        for(Residente residente: met.getResidentes()){
            ResponsableDiarioDto responsableDiarioDto = new ResponsableDiarioDto();
            responsableDiarioDto.setNdocumento(residente.getNumDocumento());
            responsableDiarioDto.setNombres(residente.getNombres() + " " + residente.getApellidos());

            List<Map<String, Boolean>> actividades = new ArrayList<>();
            for (int k = 1; k <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); k++){
                LocalDate localDate = LocalDate.of(year, month, k);
                Date date = new Date(localDate.atStartOfDay(ZoneId.of("America/Lima")).toEpochSecond() * 1000);
                List<Actividad> actividads = actividadRepository.findActividadByUsuario(date, residente, met.getMeta());

                Map<String, Boolean> item = new HashMap<>();
                boolean isPresent = false;

                for(Actividad actividad: actividads){
                    List<RegistroActividad> registroActividads = registroActividadRepository.findAllByActividad(actividad);
                    if(!registroActividads.isEmpty()){
                        isPresent = true;
                    }
                }
                item.put("diario", isPresent);
                actividades.add(item);
            }
            responsableDiarioDto.setDiarios(actividades);

            responsableDiarioDtos.add(responsableDiarioDto);
        }
        return responsableDiarioDtos;
    }

    @Override
    public String generateResidenteByMeta(String meta, Integer anio, Integer mes) {
        Meta met = metaService.findMetaById(meta);
        MetaDetalle metaDetalle = metaDetalleRepository.findById(met.getMeta()).get();
        Document document = new Document(PageSize.A4.rotate(), 0, 0, 98, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            //PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HeaderFooter.pdf"));
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.addAuthor (ParamsManager.ENTIDAD_NOMBRE + " - Sede Central");
            document.addTitle ("Reporte de Registro Mensual de Actividades Diarias");
            document.addSubject ("Reporte mensual");
            document.addKeywords ("reporte");
            document.addCreator ("Sistema de Control y Monitoreo de Actividades Diarias");
            HeaderFooterPageEventHorizontal event = new HeaderFooterPageEventHorizontal();
            event.setUser(accessToken.getUserId());
            writer.setPageEvent(event);

            PdfPTable tableHeaderMeta = new PdfPTable(new float[] { 2, 10 });
            tableHeaderMeta.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            tableHeaderMeta.addCell(detailBold("META: "));
            tableHeaderMeta.addCell(detailNormal(metaDetalle.getMeta()));
            tableHeaderMeta.addCell(detailBold("CODIGO PROY/PROD.: "));
            tableHeaderMeta.addCell(detailNormal(metaDetalle.getCodigoProductoProyecto()));
            tableHeaderMeta.addCell(detailBold("PROYECTO/PROD.: "));
            tableHeaderMeta.addCell(detailNormal(metaDetalle.getProductoProyecto()));
            tableHeaderMeta.setSpacingAfter(8);



            LocalDate local = LocalDate.of(anio, mes, 15);
            Date dateConsulta = new Date(local.atStartOfDay(ZoneId.of("America/Lima")).toEpochSecond() * 1000);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateConsulta);
            int month = calendar.get(Calendar.MONDAY) + 1;
            int year = calendar.get(Calendar.YEAR);

            Format formatter = new SimpleDateFormat("MMM yyyy", new Locale("spa", "PER"));
            PdfPTable tableHeaderReporte = new PdfPTable(new float[] { 1 });
            tableHeaderReporte.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableHeaderReporte.addCell(detailBold("Reporte de Registro Mensual de Actividades Diarias - " + formatter.format(dateConsulta)));

            float[] widths = new float[calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1];
            for (int k = 0; k < calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1; k++){
                if(k == 0){
                    widths[k] = 10;
                } else {
                    widths[k] = 1;
                }
            }
            PdfPTable table = new PdfPTable(widths);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(headerDetail("Nombres y Apellidos"));

            for (int k = 1; k <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); k++){
                table.addCell(headerDetail(String.valueOf(k)));
            }

            for(Residente residente: met.getResidentes()){
                String nombre = residente.getNombres() + " " + residente.getApellidos();
                table.addCell(cellTableName(nombre.length() < 30 ? nombre : nombre.substring(0, 30)));
                for (int k = 1; k <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); k++){
                    LocalDate localDate = LocalDate.of(year, month, k);
                    Date date = new Date(localDate.atStartOfDay(ZoneId.of("America/Lima")).toEpochSecond() * 1000);

                    List<Actividad> actividads = actividadRepository.findActividadByUsuario(date, residente, met.getMeta());
                    boolean isPresent = false;
                    for(Actividad actividad: actividads){
                        List<RegistroActividad> registroActividads = registroActividadRepository.findAllByActividad(actividad);
                        if(!registroActividads.isEmpty()){
                            isPresent = true;
                        }
                    }
                    if(isPresent){
                        table.addCell(cellTableValue("X"));
                    } else {
                        table.addCell(cellTableValue(""));
                    }
                }
            }

            document.open();
            document.add(tableHeaderMeta);
            document.add(tableHeaderReporte);
            document.add(table);
            document.close();
            return Utils.encodeBytesFile(out.toByteArray());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<ResponsableDiarioDto> findAllReportByResidente(Long oficina, Integer anio, Integer mes) {
        Perfil perfil = new Perfil();
        perfil.setId(2L);

        Oficina ofic = oficinaService.findOficinaById(oficina);

        LocalDate local = LocalDate.of(anio, mes, 15);
        Date dateConsulta = new Date(local.atStartOfDay(ZoneId.of("America/Lima")).toEpochSecond() * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateConsulta);

        int month = calendar.get(Calendar.MONDAY) + 1;
        int year = calendar.get(Calendar.YEAR);

        List<ResponsableDiarioDto> responsableDiarioDtos = new ArrayList<>();
        List<ResidenteMeta> residenteMetas = residenteMetaRepository.findAllActiveByOficina(true, perfil, ofic);
        for (ResidenteMeta residenteMeta: residenteMetas){
            LOG.info("Procesando para {}", residenteMeta.getNumDocumento() + " - " + residenteMeta.getOficina().getAbreviatura());
            ResponsableDiarioDto responsableDiarioDto = new ResponsableDiarioDto();

            String cargo = residenteMeta.getCargo().getDescripcion().length() < 20 ? residenteMeta.getCargo().getDescripcion() : residenteMeta.getCargo().getDescripcion().substring(0, 20);

            responsableDiarioDto.setNdocumento(residenteMeta.getNumDocumento() + "|" + cargo);
            responsableDiarioDto.setNombres(residenteMeta.getNombres() + " " + residenteMeta.getApellidos());

            Residente residente = new Residente();
            residente.setNumDocumento(residenteMeta.getNumDocumento());
            residente.setNombres(residenteMeta.getNombres());
            residente.setApellidos(residenteMeta.getApellidos());

            List<Map<String, Boolean>> actividades = new ArrayList<>();
            for (int k = 1; k <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); k++){
                LocalDate localDate = LocalDate.of(year, month, k);
                Date date = new Date(localDate.atStartOfDay(ZoneId.of("America/Lima")).toEpochSecond() * 1000);

                Map<String, Boolean> item = new HashMap<>();
                boolean isPresent = false;
                for(Meta meta: residenteMeta.getMetas()){
                    List<Actividad> actividads = actividadRepository.findActividadByUsuario(date, residente, meta.getMeta());
                    for(Actividad actividad: actividads){
                        List<RegistroActividad> registroActividads = registroActividadRepository.findAllByActividad(actividad);
                        if(!registroActividads.isEmpty()){
                            isPresent = true;
                        }
                    }
                }
                item.put("diario", isPresent);
                actividades.add(item);
            }
            responsableDiarioDto.setDiarios(actividades);

            responsableDiarioDtos.add(responsableDiarioDto);
        }
        return responsableDiarioDtos;
    }



















    public PdfPCell headerDetail(String name) {
        PdfPCell cellHeaderDetail = new PdfPCell(new Phrase(new Chunk(name, new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD))));
        cellHeaderDetail.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellHeaderDetail.setBackgroundColor(new BaseColor(204, 204, 204));
        cellHeaderDetail.setBorder(Rectangle.BOX);
        cellHeaderDetail.setMinimumHeight(16f);
        return cellHeaderDetail;
    }

    public PdfPCell cellTableName(String name) {
        PdfPCell cellHeaderDetail = new PdfPCell(new Phrase(new Chunk(name, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL))));
        cellHeaderDetail.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeaderDetail.setMinimumHeight(14f);
        return cellHeaderDetail;
    }

    public PdfPCell cellTableValue(String name) {
        PdfPCell cellHeaderDetail = new PdfPCell(new Phrase(new Chunk(name, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL))));
        cellHeaderDetail.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellHeaderDetail.setMinimumHeight(14f);
        return cellHeaderDetail;
    }

    public PdfPCell detailBold(String name) {
        PdfPCell cellHeaderDetail = new PdfPCell(new Phrase(new Chunk(name, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD))));
        cellHeaderDetail.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeaderDetail.setMinimumHeight(17f);
        cellHeaderDetail.setBorder(Rectangle.NO_BORDER);
        return cellHeaderDetail;
    }

    public PdfPCell detailNormal(String name) {
        PdfPCell cellHeaderDetail = new PdfPCell(new Phrase(new Chunk(name, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL))));
        cellHeaderDetail.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeaderDetail.setMinimumHeight(17f);
        cellHeaderDetail.setBorder(Rectangle.NO_BORDER);
        return cellHeaderDetail;
    }
}
