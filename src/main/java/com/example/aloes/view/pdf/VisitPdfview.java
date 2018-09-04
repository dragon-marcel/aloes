package com.example.aloes.view.pdf;

import com.example.aloes.entity.ItemVisit;
import com.example.aloes.entity.Visit;
import com.example.aloes.service.VisitService;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;

@Component(value = "visit/detailsVisit")
public class VisitPdfview extends AbstractPdfView{
@Autowired
private VisitService visitService;
    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) throws Exception {

        Visit visit =(Visit) map.get("Visit");

        PdfPTable table = new PdfPTable(1);
        table.setSpacingAfter(20);
        PdfPCell cell = new PdfPCell(new Phrase("Details client:"));
        cell.setBackgroundColor(new Color(51,134,70));
        cell.setPadding(8f);
        table.addCell(cell);
        table.addCell(visit.getClient().getName() + ' ' + visit.getClient().getSurname());
        table.addCell(visit.getClient().getNumber());
        table.addCell(visit.getClient().getEmail());

        PdfPTable table1 = new PdfPTable(1);
        table1.setSpacingAfter(20);
        PdfPCell cell1 = new PdfPCell(new Phrase("Detail visit:"));
        cell1.setBackgroundColor(new Color(51,134,70));
        cell1.setPadding(8f);
        table1.addCell(cell1);
        table1.addCell("Number: "+String.valueOf(visit.getId()));
        table1.addCell("Description: " +visit.getDescription());
        table1.addCell("Data created: " +visit.getCreateDate());
        table1.addCell("Data visit: " +visit.getVisitDate());
        table1.addCell("Time visit: " +visit.getVisitTime());

        PdfPTable table2 = new PdfPTable(4);
        table2.addCell("Massage");
        table2.addCell("Price");
        table2.addCell("Quantity");
        table2.addCell("Sum Price");

        for(ItemVisit itemVisit : visit.getItems()){
            table2.addCell(itemVisit.getMassage().getName());
            table2.addCell(itemVisit.getMassage().getPrice().toString());
            table2.addCell(itemVisit.getQuantity().toString());
            table2.addCell(itemVisit.countPrice().toString());
        }

        PdfPCell cell2 = new PdfPCell(new Phrase("Total:"));
        cell2.setColspan(3);
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table2.addCell(cell2);
        table2.addCell(visit.getTotalPrice().toString());
        document.add(table);
        document.add(table1);
        document.add(table2);
    }
}
