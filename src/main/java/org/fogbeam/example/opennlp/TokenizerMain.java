
package org.fogbeam.example.opennlp;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;


public class TokenizerMain {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Uso: java TokenizerMain <output_file> <input_file1> <input_file2> ...");
			return;
		}

		// Archivo de salida
		String outputFilePath = args[0];

		// Archivos de entrada
		List<String> inputFiles = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			inputFiles.add(args[i]);
		}

		// Modelo de tokenización
		try (InputStream modelIn = new FileInputStream("models/en-token.model");
			 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8))) {

			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);

			// Procesar cada archivo
			for (String inputFile : inputFiles) {
				System.out.println("Procesando archivo: " + inputFile);

				try (BufferedReader reader = new BufferedReader(new FileReader(inputFile, StandardCharsets.UTF_8))) {
					String line;
					while ((line = reader.readLine()) != null) {
						// Tokenizar línea
						String[] tokens = tokenizer.tokenize(line);

						// Escribir cada token en una nueva línea
						for (String token : tokens) {
							writer.write(token);
							writer.newLine();
						}
					}
				} catch (IOException e) {
					System.err.println("Error leyendo archivo: " + inputFile);
					e.printStackTrace();
				}
			}

			System.out.println("Procesamiento completo. Tokens guardados en: " + outputFilePath);

		} catch (IOException e) {
			System.err.println("Error al cargar el modelo o escribir el archivo de salida.");
			e.printStackTrace();
		}
	}
}