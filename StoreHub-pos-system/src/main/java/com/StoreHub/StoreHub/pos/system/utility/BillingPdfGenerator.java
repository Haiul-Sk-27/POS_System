package com.StoreHub.StoreHub.pos.system.utility;

import com.StoreHub.StoreHub.pos.system.payload.dto.BillingDto;
import com.StoreHub.StoreHub.pos.system.payload.dto.OrderItemDto;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;

public class BillingPdfGenerator {

    public static byte[] generate(BillingDto bill, String password) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        WriterProperties props = new WriterProperties()
                .setStandardEncryption(
                        password.getBytes(),
                        password.getBytes(),
                        EncryptionConstants.ALLOW_PRINTING,
                        EncryptionConstants.ENCRYPTION_AES_128
                );

        PdfWriter writer = new PdfWriter(baos, props);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        doc.add(new Paragraph("STOREHUB INVOICE").setBold());
        doc.add(new Paragraph("Order ID: " + bill.getOrderId()));
        doc.add(new Paragraph("Customer: " + bill.getCustomerName()));
        doc.add(new Paragraph("Date: " + bill.getBilledAt()));
        doc.add(new Paragraph("\n"));

        Table table = new Table(4);
        table.addCell("Item");
        table.addCell("Qty");
        table.addCell("Price");
        table.addCell("Total");

        for (OrderItemDto item : bill.getItems()) {
            table.addCell(item.getProduct().getName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getPrice()));
            table.addCell(String.valueOf(item.getPrice() * item.getQuantity()));
        }

        doc.add(table);

        doc.add(new Paragraph("\nTotal Payable: ₹" + bill.getTotalPayable()).setBold());

        doc.close();
        return baos.toByteArray();
    }
}