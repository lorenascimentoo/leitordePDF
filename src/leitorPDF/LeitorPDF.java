package leitorPDF;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class LeitorPDF {
	public static void main(String[] args) throws IOException{
		File file = new File("C:\\Users\\loren\\eclipse-workspace\\apache_lucene\\dados-lucene\\frmVisualizarBula.pdf");
		
		try (PDDocument document = PDDocument.load(file)){
			if(!document.isEncrypted()) {
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				
				//Cria o leitor e faz a leitura do TEXTO para uma String;
				PDFTextStripper tStripper = new PDFTextStripper();
				String pdfFileInText = tStripper.getText(document);
				
				
				
				List<String> dados = getBulasInfo(pdfFileInText);
			   	
				String nomeComercial = dados.get(0);
				String principioAtivo = dados.get(1);
				String fabricante = dados.get(2);
								
				List<String> texto = getIndicacoes(pdfFileInText);
				String indicacoes = texto.get(0);
				String contraIndicacoes = texto.get(1);
				
				System.out.println("NOME COMERCIAL " +nomeComercial);
				System.out.println("PRINCIPIO ATIVO " +principioAtivo);
				System.out.println("FABRICANTE " +fabricante);
				System.out.println(indicacoes);
				System.out.println(contraIndicacoes);
				System.out.println(texto);
			}
		}
	}
	
	public static List<String> getBulasInfo(String pdfFileInText){
		//Faz a varredura linha a linha do texto extraído 
		Scanner scn = null;					
		scn = new Scanner(pdfFileInText);
		List<String> line = new ArrayList<String>();
			while (scn.hasNextLine()){
				String analisar = scn.nextLine().trim();
				if(analisar.length()>0 ) {
					line.add(analisar);
				}
			}
		
		
		List<String> dados = new ArrayList<>();
		dados.add(line.get(0));
		dados.add(line.get(1));
		dados.add(line.get(2));
		
		scn.close();
		return dados;	
	}
	
	public static List<String> getIndicacoes(String pdfFileInText) {
		
		String p = pdfFileInText.replaceAll("\r\n", "");
		p = p.replaceAll("  ", "\n");
		//Leitura linha a linha que gera o arquivo sem os espaços em branco
		Scanner scn = null;					
		scn = new Scanner(p);
		
		List<String> textoExtraido = new ArrayList<String>();
		while (scn.hasNextLine()){
			//retira as linhas em branco
			String analisar = scn.nextLine().trim();
			
			//não permite linhas em branco no documento
			if(analisar.length()>0) {
				textoExtraido.add(analisar);
			}
		}
		scn.close();
		
		List<String> inf = new ArrayList<String>();
		
		for(String line: textoExtraido) {
			if(line.contains("INDICA")) {
				inf.add(line);
			} else if(line.contains("REAÇ")){
				inf.add(line);
			}
		}
	
		
		return inf;
	}
	
	
	
	
	

	
	
	
}

