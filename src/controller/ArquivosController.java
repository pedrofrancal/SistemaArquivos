package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class ArquivosController implements IArquivosController{

	@Override
	public void verificaDirTemp() throws IOException {
		File dir = new File("C:\\TEMP");
		if(!dir.exists()) {
			dir.mkdir();
		}
	}

	@Override
	public boolean verificaRegistro(String arquivo, int codigo) throws IOException {
		File arq = new File("C:\\TEMP", arquivo);
		File dir = new File("C:\\TEMP");
		if(dir.exists() && dir.isDirectory()) {
			if(arq.exists()) {
				FileInputStream fluxo = new FileInputStream(arq);
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				String lido = buffer.readLine();
				while(lido != null) {
					if(lido.contains(String.valueOf(codigo))) {
						buffer.close();
						leitor.close();
						fluxo.close();
						return true;
					}
					lido = buffer.readLine();
				}
				buffer.close();
				leitor.close();
				fluxo.close();
			}else {
				FileWriter fileWriter = new FileWriter(arq);
	            PrintWriter print = new PrintWriter(fileWriter);
	            print.write("");
	            print.flush();
	            print.close();
	            fileWriter.close();
			}
		}else {
			throw new IOException("Arquivo inválido");
		}
		return false;
	}

	@Override
	public void imprimeCadastro(String arquivo, int codigo) throws IOException {
		File arq = new File("C:\\TEMP", arquivo);
		if(verificaRegistro(arquivo, codigo)) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			while(linha != null) {
				System.out.println(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		}else {
			throw new IOException("Arquivo inválido");
		}
	}

	@Override
	public void insereCadastro(String arquivo, int codigo, String nome, String email) throws IOException {
		File arq = new File("C:\\TEMP", arquivo);
		if(!verificaRegistro(arquivo, codigo)) {
			String conteudo = geraTxt(codigo, nome, email);
			FileWriter fileWriter = new FileWriter(arq, arq.exists());
			PrintWriter print = new PrintWriter(fileWriter);
			print.write(conteudo);
			print.flush();
			print.close();
			fileWriter.close();
		}else {
			JOptionPane.showMessageDialog(null, "Algo não esperado foi digitado");
		}
	}

	private String geraTxt(int codigo, String nome, String email) {
		StringBuffer buffer = new StringBuffer();
		String linha = "";
		
		linha = "Código:;" + codigo + "\n";
		linha = linha + "Nome:;" + nome + "\n";
		linha = linha + "Email:;" + email + "\n";
		buffer.append(linha +"\n");
		return buffer.toString();
	}

}
