package cl.italosoft.nessfit.util;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import cl.italosoft.nessfit.service.RentRequestService;



@Component("/cliente/visualize-rent-requests/pdf")
public class PDFRentRequest extends AbstractPdfView{

    @Autowired
    private RentRequestService rentRequestService;
    
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<RentRequest> listrequestuser = (List<RentRequest>) model.get("requests");
		PdfPTable tabla = new PdfPTable(6);
		
		listrequestuser.forEach(rentrequest ->{
			tabla.addCell(String.valueOf(rentrequest.getId()));
			tabla.addCell(rentrequest.getDeportiveCenter().getName());
			tabla.addCell(String.valueOf(rentrequest.getTotalPrice()));
			tabla.addCell(rentrequest.getUser().getRut());
			tabla.addCell(rentrequest.getUser().getName());
			tabla.addCell(rentrequest.getStatus());
			
		});
		
		document.add(tabla);
	
		/*
		
		@SuppressWarnings("unchecked")
		List<RentRequest> Listrentrequest = (List<RentRequest>) model.get("rentRequest");
			
		
		
		document.setPageSize(PageSize.LETTER.rotate());
		document.setMargins(-20, -20, 40, 20);
		document.open();
		
		PdfPTable tablerentrequesttitle = new PdfPTable(1);
		PdfPCell celda = null;
		
		Font fonttitle = FontFactory.getFont("Arial",16, Color.black);
		
		celda = new PdfPCell(new Phrase("LISTADO DE SOLICITUDES CLIENTE", fonttitle));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(40,190,138));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(30);
		
		tablerentrequesttitle.addCell(celda);
		tablerentrequesttitle.setSpacingAfter(30);
		
		PdfPTable tablerentrequest = new PdfPTable(5);
	
		
		for (RentRequest rentrequest : Listrentrequest) {
			
			celda = new PdfPCell(new Phrase(rentrequest.getDates().toString()));
			celda.setPadding(5);
			tablerentrequest.addCell(celda);
			
			celda = new PdfPCell(new Phrase(rentrequest.getId()));
			celda.setPadding(5);
			tablerentrequest.addCell(celda);
			
			celda = new PdfPCell(new Phrase(rentrequest.getDeportiveCenter().getName()));
			celda.setPadding(5);
			tablerentrequest.addCell(celda);
			
			celda = new PdfPCell(new Phrase(rentrequest.getTotalPrice()));
			celda.setPadding(5);
			tablerentrequest.addCell(celda);
			
			celda = new PdfPCell(new Phrase(rentrequest.getStatus()));
			celda.setPadding(5);
			tablerentrequest.addCell(celda);
			
		}
		
/*
		Listrentrequest.forEach(RentRequest ->{
			
			int id = RentRequest.getId();
			String id2 = Integer.toString(id);
			int totalprice = RentRequest.getId();
			String totalprice2 = Integer.toString(totalprice);
			
			
			tablerentrequest.addCell(RentRequest.getDates().toString());			
			tablerentrequest.addCell(id2);
			tablerentrequest.addCell(RentRequest.getDeportiveCenter().getName());
			tablerentrequest.addCell(totalprice2);
			tablerentrequest.addCell(RentRequest.getStatus());

		});
		
    	document.add(tablerentrequesttitle);
		document.add(tablerentrequest);
		*/
	}
}

    