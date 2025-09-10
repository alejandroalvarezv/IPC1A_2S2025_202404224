package proyecto1;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Reportes {
    
    public static void generarReporteStockPDF(Producto[] productos, int totalProductos){
        try{
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
            String nombreArchivo = fechaHora + "_Stock.pdf";
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();
            
            document.add(new Paragraph("Reporte de Stock", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Fecha de generación: " + fechaHora.replace("_", ":"), FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("\n"));
            
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.addCell("Código");
            tabla.addCell("Nombre");
            tabla.addCell("Categoría");
            tabla.addCell("Precio (Q)");
            tabla.addCell("Cantidad");
            
            for (int i = 0; i < totalProductos; i++) {
                Producto p = productos[i];
                tabla.addCell(p.getCodigo());
                tabla.addCell(p.getNombre());
                tabla.addCell(p.getCategoria());
                tabla.addCell(String.format("%.2f", p.getPrecio()));
                tabla.addCell(String.valueOf(p.getCantidad()));
        }
            document.add(tabla);
            document.close();
            
            System.out.println("Reporte de stock generado: " + nombreArchivo);
    }catch (Exception e){
            System.out.println("Error al generar reporte de stock: " + e.getMessage());
    }   
}
    

    public static void generarReporteVentasPDF() {
        try {
            File archivoVentas = new File("ventas.txt");
            if (!archivoVentas.exists()) {
                System.out.println("No se encontraron ventas registradas.");
                return;
            }

            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
            String nombreArchivo = fechaHora + "_Venta.pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            document.add(new Paragraph("Reporte de Ventas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Fecha de generación: " + fechaHora.replace("_", ":"), FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(new Paragraph("\n"));

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.addCell("Código");
            tabla.addCell("Cantidad");
            tabla.addCell("Fecha y Hora");
            tabla.addCell("Total (Q)");

            Scanner lector = new Scanner(archivoVentas);
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] partes = linea.split("\\|");

                if (partes.length == 4) {
                    tabla.addCell(partes[0].split(":")[1].trim());
                    tabla.addCell(partes[1].split(":")[1].trim());
                    tabla.addCell(partes[2].split(":")[1].trim());
                    tabla.addCell(partes[3].split(":")[1].trim());
                }
            }
            lector.close();

            document.add(tabla);
            document.close();

            System.out.println("Reporte de ventas generado: " + nombreArchivo);
        } catch (Exception e) {
            System.out.println("Error al generar reporte de ventas: " + e.getMessage());
        }
    }
}