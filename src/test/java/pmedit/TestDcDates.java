package pmedit;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaDublinCore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.junit.Test;

/**
 * @author zaro
 * Test for some bugs in pdfbox 2.0.X
 *
 */
public class TestDcDates {
	
	
	@Test
	public void test() throws Exception {
		File temp = File.createTempFile("test-file", ".pdf");
        temp.deleteOnExit();
        Calendar cal = Calendar.getInstance();
        
        // Create empty document
		try (PDDocument doc = new PDDocument()) {
			// a valid PDF document requires at least one page
			PDPage blankPage = new PDPage();
			doc.addPage(blankPage);
			XMPMetadata xmpNew = new XMPMetadata();
			XMPSchemaDublinCore dcS = xmpNew.addDublinCoreSchema();

			dcS.addDate(cal);

			PDDocumentCatalog catalog = doc.getDocumentCatalog();
			PDMetadata metadataStream = new PDMetadata(doc);

			metadataStream.importXMPMetadata(xmpNew.asByteArray());
			catalog.setMetadata(metadataStream);

			doc.save(temp);
		}
        
        // Read the DC dates field
		PDDocument document =  PDDocument.load(new FileInputStream(temp));
		PDDocumentCatalog catalog = document.getDocumentCatalog();
		PDMetadata meta = catalog.getMetadata();
		XMPMetadata xmp = XMPMetadata.load(meta.createInputStream());
		XMPSchemaDublinCore dcS = xmp.getDublinCoreSchema();

		List<Calendar> actual = dcS.getDates();
		
		assertEquals(1, actual.size());
		assertEquals(cal.getTimeInMillis()/1000, actual.get(0).getTimeInMillis()/1000);

	}
	
	@Test
	public void testDateFormat() throws IOException {
		String xmp = """
				<?xpacket begin="ï»¿" id="W5M0MpCehiHzreSzNTczkc9d"?>
				<x:xmpmeta xmlns:x="adobe:ns:meta/" x:xmptk="3.1-701">
				<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
				<rdf:Description rdf:about="uuid:0cd65b51-c9b8-4f78-bbb6-28c4b83ff97b"
				xmlns:pdf="http://ns.adobe.com/pdf/1.3/">
				<pdf:Producer>Acrobat Distiller 9.4.5 (Windows)</pdf:Producer>
				</rdf:Description>
				<rdf:Description rdf:about="uuid:0cd65b51-c9b8-4f78-bbb6-28c4b83ff97b"
				xmlns:xap="http://ns.adobe.com/xap/1.0/">
				<xap:CreatorTool>3B2 Total Publishing System 8.07e/W Unicode </xap:CreatorTool>
				<xap:ModifyDate>2011-11-22T20:24:41+08:00</xap:ModifyDate>
				<xap:CreateDate>2011-11-20T10:09Z</xap:CreateDate>
				<xap:MetadataDate>2011-11-22T20:24:41+08:00</xap:MetadataDate>
				</rdf:Description>
				<rdf:Description rdf:about="uuid:0cd65b51-c9b8-4f78-bbb6-28c4b83ff97b"
				xmlns:xapMM="http://ns.adobe.com/xap/1.0/mm/">
				<xapMM:DocumentID>uuid:bdfff38a-a251-43cd-baed-42a7db3ec2f3</xapMM:DocumentID>
				<xapMM:InstanceID>uuid:23ec6b59-5bb1-40ba-8e50-5e829b6be2e9</xapMM:InstanceID>
				</rdf:Description>
				<rdf:Description rdf:about="uuid:0cd65b51-c9b8-4f78-bbb6-28c4b83ff97b"
				xmlns:dc="http://purl.org/dc/elements/1.1/">
				<dc:format>application/pdf</dc:format>
				<dc:title>
				<rdf:Alt>
				<rdf:li xml:lang="x-default"/>
				</rdf:Alt>
				</dc:title>
				</rdf:Description>
				</rdf:RDF>
				</x:xmpmeta>
				<?xpacket end="w"?>""";

		XMPMetadata.load(new ByteArrayInputStream(xmp.getBytes()));

	}

}
