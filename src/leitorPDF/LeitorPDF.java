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
		File file = new File("C:\\Users\\loren\\eclipse-workspace\\apache_lucene\\dados-lucene\\AAS_Infantil.pdf");
		
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
				String reacoesAdversas= texto.get(2);
				
				System.out.println("NOME COMERCIAL: " +nomeComercial);
				System.out.println("PRINCIPIO ATIVO: " +principioAtivo);
				System.out.println("FABRICANTE: " +fabricante);
				System.out.println(indicacoes);
				System.out.println(contraIndicacoes);
				System.out.println(reacoesAdversas);
				
				System.out.println("Funcionou!!!");
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
		p = p.replaceAll("  ", "\n").replace("?", ":");
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
		List<String> indicacao = new ArrayList<String>();
		List<String> contraIndicacao = new ArrayList<String>();
		List<String> reacaoAdversa = new ArrayList<String>();
		for(int i=0;i<textoExtraido.size();i++) {
			String line = textoExtraido.get(i);
			if(line.contains("PARA")) {
				int j = i;
				while(!textoExtraido.get(j).contains("NÃO")) {
					indicacao.add(textoExtraido.get(j));
					j++;
				}
			} else if(line.contains("NÃO")){
				int j = i;
				while(!textoExtraido.get(j).contains("SABER")) {
					contraIndicacao.add(textoExtraido.get(j));
					j++;
					}
				
			}else if(line.contains("MALES")){
				int j = i;
				while(!textoExtraido.get(j).contains("QUANTIDADE")) {
					reacaoAdversa.add(textoExtraido.get(j));
					j++;
					}
			}

		}
		
		String dadoInd = getString(indicacao); 
		String dadoContraInd = getString(contraIndicacao);
		String dadoReacAdv = getString(reacaoAdversa);
		
		
		inf.add(dadoInd);
		inf.add(dadoContraInd);
		inf.add(dadoReacAdv);
		
		
		return inf;
	}
	
	public static String getString(List<String> lista) {
		String dado = lista.toString();
		dado = dado.replaceAll("\\d.", "");
		return dado;
	}
	
	
	

	
	
	
}

