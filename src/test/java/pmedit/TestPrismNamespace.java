package pmedit;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.jempbox.xmp.XMPMetadata;
import org.junit.Test;

public class TestPrismNamespace {


	@Test
	public void testPrism() throws IOException {
		String xmp = """
				<?xpacket begin="ï»¿" id="W5M0MpCehiHzreSzNTczkc9d"?><x:xmpmeta xmlns:x="adobe:ns:meta/" x:xmptk="Adobe XMP Core 5.4-c005 78.147326, 2012/08/23-13:03:03        ">\r
				   <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">\r
				      <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:pdf="http://ns.adobe.com/pdf/1.3/" xmlns:pdfx="http://ns.adobe.com/pdfx/1.3/" xmlns:prism="http://prismstandard.org/namespaces/basic/2.0/" xmlns:xmp="http://ns.adobe.com/xap/1.0/" xmlns:xmpMM="http://ns.adobe.com/xap/1.0/mm/" xmlns:xmpRights="http://ns.adobe.com/xap/1.0/rights/" rdf:about="">\r
				         <dc:format>application/pdf</dc:format>\r
				         <dc:identifier>doi:10.1016/j.survophthal.2012.08.008</dc:identifier>\r
				         <dc:title>\r
				            <rdf:Alt>\r
				               <rdf:li xml:lang="x-default">Serpiginous Choroiditis and Infectious Multifocal Serpiginoid Choroiditis</rdf:li>\r
				            </rdf:Alt>\r
				         </dc:title>\r
				         <dc:creator>\r
				            <rdf:Seq>\r
				               \r
				               \r
				            <rdf:li>Hossein Nazari Khanamiri MD</rdf:li>\r
				<rdf:li>Narsing A. Rao MD</rdf:li>\r
				</rdf:Seq>\r
				         </dc:creator>\r
				         <dc:subject>\r
				            <rdf:Bag>\r
				               \r
				               \r
				               \r
				               \r
				               \r
				            <rdf:li>serpiginous choroiditis</rdf:li>\r
				<rdf:li>serpiginous-like choroiditis</rdf:li>\r
				<rdf:li>multifocal serpiginoid choroiditis</rdf:li>\r
				<rdf:li>tuberculosis</rdf:li>\r
				<rdf:li>herpes virus</rdf:li>\r
				</rdf:Bag>\r
				         </dc:subject>\r
				         <dc:description>\r
				            <rdf:Alt>\r
				               <rdf:li xml:lang="x-default">Survey of Ophthalmology, 58 (2013) 203-232. doi:10.1016/j.survophthal.2012.08.008</rdf:li>\r
				            </rdf:Alt>\r
				         </dc:description>\r
				         <dc:publisher>\r
				            <rdf:Bag>\r
				               \r
				            <rdf:li>Elsevier Inc</rdf:li>\r
				</rdf:Bag>\r
				         </dc:publisher>\r
				         <prism:aggregationType>journal</prism:aggregationType>\r
				         <prism:publicationName>Survey of Ophthalmology</prism:publicationName>\r
				         <prism:copyright>Copyright Â©Â 2013 by Elsevier Inc.All rights reserved</prism:copyright>\r
				         <prism:issn>0039-6257</prism:issn>\r
				         <prism:volume>58</prism:volume>\r
				         <prism:number>3</prism:number>\r
				         <prism:coverDisplayDate>May-June 2013</prism:coverDisplayDate>\r
				         <prism:coverDate>2013-05-06</prism:coverDate>\r
				         <prism:pageRange>203-232</prism:pageRange>\r
				         <prism:startingPage>203</prism:startingPage>\r
				         <prism:endingPage>232</prism:endingPage>\r
				         <prism:doi>10.1016/j.survophthal.2012.08.008</prism:doi>\r
				         <prism:url>http://dx.doi.org/10.1016/j.survophthal.2012.08.008</prism:url>\r
				         <pdfx:ElsevierWebPDFSpecifications>6.3</pdfx:ElsevierWebPDFSpecifications>\r
				         <pdfx:doi>10.1016/j.survophthal.2012.08.008</pdfx:doi>\r
				         <pdfx:robots>noindex</pdfx:robots>\r
				         <xmp:CreatorTool>Elsevier</xmp:CreatorTool>\r
				         \r
				         \r
				         \r
				         <xmpRights:Marked>True</xmpRights:Marked>\r
				         <pdf:Producer>Acrobat Distiller 8.1.0 (Windows)</pdf:Producer>\r
				         <xmpMM:DocumentID>uuid:2fe3af88-bbfd-42a1-a58f-fa1902db88e0</xmpMM:DocumentID>\r
				         <xmpMM:InstanceID>uuid:0f9c640c-68b5-4937-9716-9ef527630e2c</xmpMM:InstanceID>\r
				      <xmp:CreateDate>2013-04-12T03:16:31+03:00</xmp:CreateDate>\r
				<xmp:ModifyDate>2016-07-22T01:53:00+03:00</xmp:ModifyDate>\r
				<xmp:MetadataDate>2014-05-14T01:22:38+03:00</xmp:MetadataDate>\r
				<xmp:Rating>0</xmp:Rating>\r
				<xmp:Title>TITLE</xmp:Title>\r
				</rdf:Description>\r
				   </rdf:RDF>\r
				</x:xmpmeta><?xpacket end="w"?>\r
				""";

		XMPMetadata.load(new ByteArrayInputStream(xmp.getBytes()));

	}

}
