
package modelo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.SortUtil.CriterioProducto; // Importar el Criterio para ordenar

public class ReportePDFGenerator {

    private static final Logger logger = Logger.getLogger(ReportePDFGenerator.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private static final Font FONT_TITULO = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
    private static final Font FONT_SUBTITULO = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font FONT_HEADER_TABLA = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
    private static final Font FONT_CELDA = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);

     
    public static String generarReporte(Object[] data, String tipoReporte, String tituloPDF, String rutaBaseDirectorios) {
        if (data == null || data.length == 0) {
            logger.log(Level.WARNING, "No hay datos para generar el reporte: {0}", tituloPDF);
            return null;
        }
        
        String nombreArchivo = String.format("%s_%s.pdf", 
                                             tipoReporte.replace("_", "-"), 
                                             LocalDateTime.now().format(FORMATTER));
        String rutaCompleta = rutaBaseDirectorios + File.separator + nombreArchivo;

        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
            document.open();

            // 1. Título y Fecha
            document.add(crearTitulo(tituloPDF));
            
            // 2. Contenido de la Tabla
            switch (tipoReporte) {
                case "VENTAS_MAS":
                case "VENTAS_MENOS":
                    document.add(generarTablaProductosVendidos(data));
                    break;
                case "INVENTARIO":
                    document.add(generarTablaInventario(data));
                    break;
                default:
                    document.add(new Paragraph("Tipo de reporte desconocido.", FONT_SUBTITULO));
                    break;
            }

            document.close();
            return rutaCompleta;

        } catch (DocumentException | IOException e) {
            logger.log(Level.SEVERE, "Error al generar el PDF para: " + tituloPDF, e);
            return null;
        }
    }

    private static Paragraph crearTitulo(String titulo) {
        Paragraph p = new Paragraph(titulo, FONT_TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(10);
        return p;
    }

    /**
     * Genera la tabla para los reportes de productos más y menos vendidos.
     */
    private static PdfPTable generarTablaProductosVendidos(Object[] data) throws DocumentException {
        PdfPTable table = new PdfPTable(5); // 5 Columnas
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        float[] columnWidths = {2f, 4f, 2f, 2f, 2f};
        table.setWidths(columnWidths);

        // Encabezados de la Tabla
        String[] headers = {"Código", "Nombre", "Categoría", "Cantidad Vendida", "Ingresos Generados"};
        BaseColor headerColor = new BaseColor(0, 102, 204); // Azul

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FONT_HEADER_TABLA));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(headerColor);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Celdas de Datos
        for (Object obj : data) {
            ReporteVentasProducto rvp = (ReporteVentasProducto) obj;
            
            // Código
            PdfPCell c1 = new PdfPCell(new Phrase(rvp.getCodigoProducto(), FONT_CELDA));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            // Nombre
            PdfPCell c2 = new PdfPCell(new Phrase(rvp.getNombre(), FONT_CELDA));
            c2.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c2);

            // Categoría
            PdfPCell c3 = new PdfPCell(new Phrase(rvp.getCategoria(), FONT_CELDA));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c3);

            // Cantidad Vendida
            PdfPCell c4 = new PdfPCell(new Phrase(String.valueOf(rvp.getCantidadVendida()), FONT_CELDA));
            c4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c4);

            // Ingresos Generados
            PdfPCell c5 = new PdfPCell(new Phrase(String.format("Q %.2f", rvp.getIngresosGenerados()), FONT_CELDA));
            c5.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c5);
        }
        return table;
    }
    

    private static PdfPTable generarTablaInventario(Object[] data) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        float[] columnWidths = {1.5f, 3f, 2f, 1.5f, 2f, 2.5f};
        table.setWidths(columnWidths);

        String[] headers = {"Código", "Nombre", "Categoría", "Stock", "Estado", "Sugerencia"};
        BaseColor headerColor = new BaseColor(255, 102, 0); // Naranja

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FONT_HEADER_TABLA));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(headerColor);
            cell.setPadding(5);
            table.addCell(cell);
        }

        for (Object obj : data) {
            ReporteInventario ri = (ReporteInventario) obj;
            
            // Código
            PdfPCell c1 = new PdfPCell(new Phrase(ri.getCodigo(), FONT_CELDA));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            // Nombre
            PdfPCell c2 = new PdfPCell(new Phrase(ri.getNombre(), FONT_CELDA));
            c2.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c2);

            // Categoría
            PdfPCell c3 = new PdfPCell(new Phrase(ri.getCategoria(), FONT_CELDA));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c3);

            // Stock
            PdfPCell c4 = new PdfPCell(new Phrase(String.valueOf(ri.getStock()), FONT_CELDA));
            c4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c4);
            
            // Estado (Resaltado)
            BaseColor estadoColor;
            if ("Crítico".equals(ri.getEstado())) {
                estadoColor = new BaseColor(255, 200, 200); // Rojo claro
            } else if ("Bajo".equals(ri.getEstado())) {
                estadoColor = new BaseColor(255, 255, 180); // Amarillo claro
            } else {
                estadoColor = BaseColor.WHITE;
            }
            PdfPCell c5 = new PdfPCell(new Phrase(ri.getEstado(), FONT_CELDA));
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            c5.setBackgroundColor(estadoColor);
            table.addCell(c5);

            // Sugerencia
            PdfPCell c6 = new PdfPCell(new Phrase(ri.getSugerencia(), FONT_CELDA));
            c6.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c6);
        }
        return table;
    }
}
