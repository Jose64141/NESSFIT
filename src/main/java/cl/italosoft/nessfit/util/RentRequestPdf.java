package cl.italosoft.nessfit.util;

import java.awt.Color;
import java.text.SimpleDateFormat;
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
  
	private Util util;
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<RentRequest> rentRequestList = (List<RentRequest>) model.get("requests");

		Color c1 = new Color(160,251,14);    // a0fb0e
		Color c2 = new Color(32,198,122);    // #20c67a
		Color c3 = new Color(63,136,128);    // #3f8880
		Color c4 = new Color(157,192,157);   // #9dc09d
		Color c5 = new Color(254,246,205);   // #fef6cd 
		
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.black);
		Font columnTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.black);
		Font titlecell = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.black);
		
		document.setPageSize(PageSize.LETTER.rotate());
		document.setMargins(-20, -20, 30, 20);
		document.open();
		PdfPCell cell = null;		
		
		PdfPTable rentRequestTableTitle = new PdfPTable(1);
		
		cell = new PdfPCell(new Phrase("Listado de solicitudes de ".concat(Util.capitelizeEachWord(rentRequestList.get(0).getUser().getName() +" "+
				rentRequestList.get(0).getUser().getFirstLastName() +" "+ rentRequestList.get(0).getUser().getSecondLastName()) ), titleFont));
		cell.setBorder(0);
		cell.setBackgroundColor(c2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(30);
		
		rentRequestTableTitle.addCell(cell);
		rentRequestTableTitle.setSpacingAfter(30);
		
		PdfPTable table = new PdfPTable(7);
		table.setWidths(new float[] {1.1f,0.8f,2.4f, 1.5f, 1.7f, 2.6f, 1.3f});
		
		cell = new PdfPCell(new Phrase("Fecha" ,columnTitleFont));
		cell.setBackgroundColor(c3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("ID" ,columnTitleFont));
		cell.setBackgroundColor(c3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Nombre Recinto" ,columnTitleFont));
		cell.setBackgroundColor(c3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Costo Total" ,columnTitleFont));
		cell.setBackgroundColor(c3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("RUT Cliente" ,columnTitleFont));
		cell.setBackgroundColor(c3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Nombre Cliente" ,columnTitleFont));
		cell.setBackgroundColor(c3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Estado" ,columnTitleFont));
		cell.setBackgroundColor(c3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		table.addCell(cell);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		for(RentRequest rentRequest : rentRequestList) {
			cell = new PdfPCell(new Phrase(formatter.format(rentRequest.getRequestDate()), titlecell));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("#"+rentRequest.getId(), titlecell));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(rentRequest.getDeportiveCenter().getName().toUpperCase(), titlecell));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("$"+rentRequest.getTotalPrice(), titlecell));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(rentRequest.getUser().getRut(), titlecell));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(Util.capitelizeEachWord(rentRequest.getUser().getName() +" "+ rentRequest.getUser().getFirstLastName() +" "+ rentRequest.getUser().getSecondLastName()), titlecell));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(Util.capitelizeEachWord(rentRequest.getStatus()), titlecell));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			table.addCell(cell);
		
		}
			
		document.add(rentRequestTableTitle);
		document.add(table);
	}
}

    