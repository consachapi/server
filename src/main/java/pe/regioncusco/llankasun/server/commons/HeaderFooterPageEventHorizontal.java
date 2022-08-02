package pe.regioncusco.llankasun.server.commons;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import pe.regioncusco.llankasun.server.config.AccessTokenImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderFooterPageEventHorizontal extends PdfPageEventHelper {
    private String user;

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
            logoPeru.setAbsolutePosition(82, 515);
            logoPeru.scalePercent(28f, 28f);
            writer.getDirectContent().addImage(logoPeru, true);

            Image logoGore = Image.getInstance(HeaderFooterPageEvent.class.getResource("/images/gore.png"));
            logoGore.setAbsolutePosition(645, 515);
            logoGore.scalePercent(4.1f, 4.1f);
            writer.getDirectContent().addImage(logoGore, true);

            Phrase entidad = new Phrase("GOBIERNO REGIONAL DE CUSCO", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            Phrase gerencia = new Phrase("GERENCIA REGIONAL DE GESTION DE PROYECTOS", new Font(Font.FontFamily.HELVETICA, 10));
            Paragraph preface = new Paragraph(gerencia);
            preface.setAlignment(Element.ALIGN_CENTER);

            Phrase subGerencia = new Phrase("SUB GERENCIA DE OBRAS", new Font(Font.FontFamily.HELVETICA, 8));
            Phrase nombreAnio = new Phrase("\"AÑO DEL FORTALECIMIENTO DE LA SOBERANIA NACIONAL\"", new Font(Font.FontFamily.HELVETICA, 7));
            Phrase fecha = new Phrase("Fecha: " + fechaActual + " " + HoraActual, new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL));
            Phrase usuario = new Phrase("Usuario: " + user, new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL));

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, entidad, 320, 548, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, preface, 280, 534, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, subGerencia, 355, 522, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, nombreAnio, 305, 510, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, fecha, 171, 502, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, usuario, 700, 502, 0);

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
            logoBarra.setAbsolutePosition(405, 5);
            logoBarra.scalePercent(8f, 8f);
            writer.getDirectContent().addImage(logoBarra, true);

            Phrase pagina = new Phrase("Pág. " + document.getPageNumber(), new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC));

            Phrase direccion = new Phrase(ParamsManager.ENTIDAD_DIRECCION, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
            Phrase url = new Phrase(ParamsManager.ENTIDAD_URL, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, pagina, 740, 30, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, direccion, 675, 20, 0);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, url, 678, 10, 0);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public void setUser(String user) {
        this.user = user;
    }
}
