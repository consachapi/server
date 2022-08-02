package pe.regioncusco.llankasun.server.commons;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        Date fechaHoy = new Date();
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
        String fechaActual = fechaFormat.format(fechaHoy);
        String HoraActual = horaFormat.format(fechaHoy);

        try {
            Image logoPeru = Image.getInstance(HeaderFooterPageEvent.class.getResource("/images/peru.png"));
            logoPeru.setAlignment(Element.ALIGN_RIGHT);
            logoPeru.setAbsolutePosition(70, 760);
            logoPeru.scalePercent(28f, 28f);
            writer.getDirectContent().addImage(logoPeru, true);

            Image logoGore = Image.getInstance(HeaderFooterPageEvent.class.getResource("/images/gore.png"));
            logoGore.setAbsolutePosition(440, 770);
            logoGore.scalePercent(4.1f, 4.1f);
            writer.getDirectContent().addImage(logoGore, true);

            Phrase entidad = new Phrase("GOBIERNO REGIONAL DE CUSCO", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            Phrase gerencia = new Phrase("GERENCIA REGIONAL DE GESTION DE PROYECTOS", new Font(Font.FontFamily.HELVETICA, 10));
            Paragraph preface = new Paragraph(gerencia);
            preface.setAlignment(Element.ALIGN_CENTER);

            Phrase subGerencia = new Phrase("SUB GERENCIA DE OBRAS", new Font(Font.FontFamily.HELVETICA, 8));
            Phrase nombreAnio = new Phrase("\"AÑO DEL FORTALECIMIENTO DE LA SOBERANIA NACIONAL\"", new Font(Font.FontFamily.HELVETICA, 7));
            Phrase fecha = new Phrase("Fecha: " + fechaActual + " " + HoraActual, new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL));
            Phrase usuario = new Phrase("Usuario: 42520026", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL));
            Phrase line = new Phrase("____________________________________________________________", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL));

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, entidad, 180, 798, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, preface, 145, 786, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, subGerencia, 215, 776, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, nombreAnio, 162, 767, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, fecha, 146, 750, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, usuario, 477, 750, 0);
            //ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, line, 72, 749, 0);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            Image logoIntegridad = Image.getInstance(HeaderFooterPageEvent.class.getResource("/images/slogantrans.png"));
            logoIntegridad.setAlignment(Element.ALIGN_RIGHT);
            logoIntegridad.setAbsolutePosition(70, 5);
            logoIntegridad.scalePercent(7.5f, 7.5f);
            writer.getDirectContent().addImage(logoIntegridad, true);

            Image logoBarra = Image.getInstance(HeaderFooterPageEvent.class.getResource("/images/barra.png"));
            logoBarra.setAlignment(Element.ALIGN_RIGHT);
            logoBarra.setAbsolutePosition(300, 5);
            logoBarra.scalePercent(8f, 8f);
            writer.getDirectContent().addImage(logoBarra, true);

            Phrase pagina = new Phrase("Pág. " + document.getPageNumber(), new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC));

            Phrase direccion = new Phrase(ParamsManager.ENTIDAD_DIRECCION, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
            Phrase url = new Phrase(ParamsManager.ENTIDAD_URL, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, pagina, 525, 30, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, direccion, 460, 20, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, url, 465, 10, 0);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
}
