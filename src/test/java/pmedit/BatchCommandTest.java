package pmedit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pmedit.MetadataInfoTest.PMTuple;
import pmedit.PDFMetadataEditBatch.ActionStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BatchCommandTest {

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	static int NUM_FILES = 5;
	@Test
	public void testClearAll() throws Exception {
		List<PMTuple> fileList = MetadataInfoTest.randomFiles(NUM_FILES);
		List<String> args = new ArrayList<>();
		args.add("clear");
		args.add("all");
		
		for(PMTuple t: fileList){
			args.add(t.file.getAbsolutePath());
		}
		
		CommandLine c = CommandLine.parse(args);
		PDFMetadataEditBatch batch =new PDFMetadataEditBatch(c.params);
		batch.runCommand(c.command, FileList.fileList(c.fileList), new ActionStatus(){
			@Override
			public void addStatus(String filename, String message) {
			}

			@Override
			public void addError(String filename, String error) {
				System.out.println(error);
				fail(error);
			}
			
		});
		MetadataInfo empty = new MetadataInfo();
		for(PMTuple t: fileList){
			MetadataInfo loaded = new MetadataInfo();
			loaded.loadFromPDF(t.file);
			//System.out.println(pdf.getAbsolutePath());
			assertTrue(empty.isEquivalent(loaded));
		}
	}

	@Test
	public void testClearNone() throws Exception {
		List<PMTuple> fileList = MetadataInfoTest.randomFiles(NUM_FILES);
		List<String> args = new ArrayList<>();
		args.add("clear");
		args.add("none");
		
		for(PMTuple t: fileList){
			args.add(t.file.getAbsolutePath());
		}
		
		CommandLine c = CommandLine.parse(args);
		PDFMetadataEditBatch batch =new PDFMetadataEditBatch(c.params);
		batch.runCommand(c.command, FileList.fileList(c.fileList), new ActionStatus(){
			@Override
			public void addStatus(String filename, String message) {
			}

			@Override
			public void addError(String filename, String error) {
				System.out.println(error);
				fail(error);
			}
			
		});
		for(PMTuple t: fileList){
			MetadataInfo loaded = new MetadataInfo();
			loaded.loadFromPDF(t.file);
			//System.out.println(pdf.getAbsolutePath());
			assertTrue(t.md.isEquivalent(loaded));
		}
	}

	@Test
	public void testFromCSV() throws Exception {
		List<PMTuple> fileList = MetadataInfoTest.randomFiles(NUM_FILES);
		ArrayList<String> csvLines = new ArrayList<>();
		csvLines.add("file.fullPath,doc.author,dc.title");
		for(PMTuple t: fileList){
			csvLines.add(t.file.getAbsolutePath() + ",AUTHOR-AUTHOR,\"TITLE,TITLE\"");
		}
		
		File csvFile = MetadataInfoTest.csvFile(csvLines);
		List<String> args = new ArrayList<>();
		args.add("fromcsv");
		
		
		args.add(csvFile.getAbsolutePath());
		
		CommandLine c = CommandLine.parse(args);
		PDFMetadataEditBatch batch =new PDFMetadataEditBatch(c.params);
		batch.runCommand(c.command, FileList.fileList(c.fileList), new ActionStatus(){
			@Override
			public void addStatus(String filename, String message) {
			}

			@Override
			public void addError(String filename, String error) {
				System.out.println(error);
				fail(error);
			}
			
		});
		for(PMTuple t: fileList){
			MetadataInfo loaded = new MetadataInfo();
			loaded.loadFromPDF(t.file);
			//System.out.println(pdf.getAbsolutePath());
			//assertTrue(t.md.isEquivalent(loaded));
			assertEquals(loaded.doc.author,"AUTHOR-AUTHOR");
			assertEquals(loaded.dc.title,"TITLE,TITLE");
		}
	}

	
	
}
