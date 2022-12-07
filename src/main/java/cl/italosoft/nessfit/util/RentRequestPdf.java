package cl.italosoft.nessfit.util;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import cl.italosoft.nessfit.model.RentRequest;

@Component("/cliente/visualize-rent-requests/pdf")
public class RentRequestPdf extends AbstractPdfView{
  
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<RentRequest> rentRequestList = (List<RentRequest>) model.get("requests");

		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.black);
		Font columnTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.blue);
		
		document.setPageSize(PageSize.LETTER.rotate());
		document.setMargins(-20, -20, 30, 20);
		document.open();
		PdfPCell cell = null;		
		
		PdfPTable rentRequestTableTitle = new PdfPTable(1);
		
		cell = new PdfPCell(new Phrase("Listado de solicitudes de ".concat(rentRequestList.get(0).getUser().getName() +" "+
				rentRequestList.get(0).getUser().getFirstLastName() +" "+ rentRequestList.get(0).getUser().getSecondLastName() ), titleFont));
		cell.setBorder(0);
		cell.setBackgroundColor(new Color(40,190,138));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(30);
		
		rentRequestTableTitle.addCell(cell);
		rentRequestTableTitle.setSpacingAfter(30);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidths(new float[] {0.8f,2.8f, 2.0f, 1.5f, 3.0f, 1.5f});
		cell = new PdfPCell(new Phrase("ID" ,columnTitleFont));
		cell.setBackgroundColor(Color.lightGray);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Centro Deportivo" ,columnTitleFont));
		cell.setBackgroundColor(Color.lightGray);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Monto Total" ,columnTitleFont));
		cell.setBackgroundColor(Color.lightGray);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Rut" ,columnTitleFont));
		cell.setBackgroundColor(Color.lightGray);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Nombre Cliente" ,columnTitleFont));
		cell.setBackgroundColor(Color.lightGray);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Estado" ,columnTitleFont));
		cell.setBackgroundColor(Color.lightGray);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		rentRequestList.forEach(rentRequest ->{
			table.addCell(String.valueOf(rentRequest.getId()));
			table.addCell(rentRequest.getDeportiveCenter().getName());
			table.addCell(String.valueOf(rentRequest.getTotalPrice()));
			table.addCell(rentRequest.getUser().getRut());
			table.addCell(rentRequest.getUser().getName() +" "+ rentRequest.getUser().getFirstLastName() +" "+ rentRequest.getUser().getSecondLastName());
			table.addCell(rentRequest.getStatus());
			
		});
		document.add(rentRequestTableTitle);
		document.add(table);
	}
}

    