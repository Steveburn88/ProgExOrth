package de.schneefisch.fruas.transactions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.DeliveryNotePositionDAO;
import de.schneefisch.fruas.database.FiCustomerDAO;
import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.database.LocationDAO;
import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.DeliveryNote;
import de.schneefisch.fruas.model.DeliveryNotePosition;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.License;
import de.schneefisch.fruas.model.Location;
import de.schneefisch.fruas.model.Maintenance;
import de.schneefisch.fruas.model.Product;

public class DeliveryNotePDFCreator {
	
	private DeliveryNote deliveryNote;
	private Customer customer;
	private FiCustomer fiCustomer;
	private Location location;
	private List<DeliveryNotePosition> deliveryNotePositions;

	public DeliveryNotePDFCreator(DeliveryNote dn) {
		this.deliveryNote = dn;
		
		DeliveryNotePositionDAO dnpDAO = new DeliveryNotePositionDAO();
		LicenseDAO lDAO = new LicenseDAO();
		
		CustomerDAO cDAO = new CustomerDAO();
		FiCustomerDAO fDAO = new FiCustomerDAO();
		LocationDAO locDAO = new LocationDAO();
		
		
		try {
			this.deliveryNotePositions  = dnpDAO.selectAllPositionsForDeliveryNote(deliveryNote.getId());
			License license = lDAO.selectLicenseById(deliveryNotePositions.get(0).getLicenseId());
			
			this.customer = cDAO.searchCustomerById(license.getCustomerId());
			this.fiCustomer = fDAO.selectFiCustomer(customer.getFiKuId());
			this.location = locDAO.findLocationByFiCustomerId(fiCustomer.getId());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
public void createPDF() {
		
		String filename = "Lieferschein-" + deliveryNote.getId() + ".pdf";
		
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
			content.showText("Lieferschein " + deliveryNote.getId() +" fuer KN " + fiCustomer.getId());
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
			
			
			int count = 1;
			float yoffset = 460;
			for(DeliveryNotePosition dnp: deliveryNotePositions) {	
				LicenseDAO licDAO = new LicenseDAO();
				License license = licDAO.selectLicenseById(dnp.getLicenseId());
				ProductDAO pDAO = new ProductDAO();
				Product product = pDAO.searchProductById(license.getProductId());
				
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
				content.newLineAtOffset(120, yoffset-20);
				content.showText("Lizenz: " + license.getId() + " von " + license.getSoldDate() + " bis " + license.getEndDate()+ ".");
				content.endText();
				if(license.getMaintenanceId() == 0 ) {
					content.beginText();
					content.setFont(PDType1Font.HELVETICA , 12);
					content.newLineAtOffset(120, yoffset-40);
					content.showText("Ohne Maintenance-Vertrag.");
					content.endText();
				} else {
					MaintenanceDAO mDAO = new MaintenanceDAO();
					Maintenance maintenance = mDAO.searchMaintenanceById(license.getMaintenanceId());
					content.beginText();
					content.setFont(PDType1Font.HELVETICA , 12);
					content.newLineAtOffset(120, yoffset-60);
					content.showText("Mit " + maintenance.getInfo() + " als Maintenance-Vertrag.");
					content.endText();
				}			
				count++;
				yoffset-=100;
			}
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("Rechnungslegung erfolgt separat.");
			content.endText();
			yoffset-=20;
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("Das Produkt bleibt bis zur vollständigen Bezahlung unser Eigentum.");
			content.endText();
			yoffset-=20;			
			
			
			content.beginText();
			content.setFont(PDType1Font.HELVETICA , 12);
			content.newLineAtOffset(70, yoffset);
			content.showText("Mit freundlichen Gruessen");
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
	
	
}
