package de.schneefisch.fruas.transactions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.FiCustomerDAO;
import de.schneefisch.fruas.database.LocationDAO;
import de.schneefisch.fruas.database.OfferPositionDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.OfferPosition;
import de.schneefisch.fruas.model.Product;

public class OfferPDFCreator {

	private Offer offer;
	private Customer customer;
	private FiCustomer fiCustomer;
	private Location location;
	private List<OfferPosition> offerPositions;
	
	
	public OfferPDFCreator(){};

	public OfferPDFCreator(Offer offer) {
		this.offer = offer;
		CustomerDAO cDAO = new CustomerDAO();
		FiCustomerDAO fDAO = new FiCustomerDAO();
		LocationDAO lDAO = new LocationDAO();
		OfferPositionDAO opDAO = new OfferPositionDAO();
		try {
			customer = cDAO.searchCustomerById(offer.getCustomerId());
			fiCustomer = fDAO.selectFiCustomer(customer.getFiKuId());
			location = lDAO.findLocationByFiCustomerId(fiCustomer.getId());
			offerPositions = opDAO.searchOfferPositionsByOfferId(offer.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createPDF() {
		
		String filename = offer.getId() + ".pdf";
		
		try {
			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();
			doc.addPage(page);
			
			PDPageContentStream content = new PDPageContentStream(doc, page);
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 14);
			content.newLineAtOffset(70, 750);			
			content.showText("Schneefisch GmbH");
			content.endText();
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 13);
			content.newLineAtOffset(70, 725);	
			content.showText("Nibelungenplatz 1");
			content.endText();
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 14);
			content.newLineAtOffset(70, 700);	
			content.showText("D-60318 Frankfurt am Main");			
			content.endText();
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, 640);		
			content.showText(fiCustomer.getName());
			content.endText();
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, 620);	
			content.showText(customer.getSalutation() + " " + customer.getFirstName() + " " + customer.getLastName());
			content.endText();
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, 600);	
			content.showText(location.getStreet() + " " + location.getHouseNumber());
			content.endText();
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, 580);
			content.showText(location.getPostalCode() + " " + location.getCity());
			content.endText();
			
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(300, 540);
			content.showText("Angebot " + offer.getId() +" für KN " + offer.getCustomerId());
			content.endText();
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(75, 500);
			content.showText("POS");
			content.endText();
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(120, 500);
			content.showText("Bezeichnung");
			content.endText();
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(240, 500);
			content.showText("Menge");
			content.endText();
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(320, 500);
			content.showText("Einzelpreis");
			content.endText();
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(440, 500);
			content.showText("Gesamtpreis");
			content.endText();
			int count = 1;
			float yoffset = 460;
			for(OfferPosition op: offerPositions) {	
				ProductDAO pDAO = new ProductDAO();
				Product product = pDAO.searchProductById(op.getProductId());
				content.beginText();
				content.setFont(PDType1Font.HELVETICA , 12);
				content.newLineAtOffset(75, yoffset);
				content.showText(Integer.toString(count));
				content.endText();
				content.beginText();
				content.setFont(PDType1Font.HELVETICA , 12);
				content.newLineAtOffset(120, yoffset);
				content.showText(product.getName());
				content.endText();
				content.beginText();
				content.setFont(PDType1Font.HELVETICA , 12);
				content.newLineAtOffset(240, yoffset);
				content.showText(Integer.toString(op.getCount()));
				content.endText();
				content.beginText();
				content.setFont(PDType1Font.HELVETICA , 12);
				content.newLineAtOffset(320, yoffset);
				content.showText("EUR " + Float.toString(product.getPrice()));
				content.endText();
				content.beginText();
				content.setFont(PDType1Font.HELVETICA , 12);
				content.newLineAtOffset(440, yoffset);
				content.showText("EUR " + Float.toString(op.getCount()*product.getPrice()));
				content.endText();
				count++;
				yoffset-=40;
			}
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("Bedingungen:");
			content.endText();
			yoffset-=20;
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("- Alle Preise verstehen sich zzgl. ges. Ust.");
			content.endText();
			yoffset-=20;
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("- Zahlung erfolgt 30 Tage netto. Dieses Angebot ist freibleibend und gilt bis" + offer.getValidity());
			content.endText();
			yoffset-=40;
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("Mit freundlichen Grüßen");
			content.endText();
			yoffset-=20;
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("Schneefisch GmbH");
			content.endText();
			content.close();
			
			
			doc.save(filename);
			doc.close();
			System.out.println(filename + " erstellt in: " + System.getProperty("user.dir"));
		}  catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		
		
	}
	
	
	
	
	
	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

}
